package com.zte.container.ext.diff;

import com.zte.container.ext.total.ReverseTaskFactory;
import com.zte.mos.container.NeDomain;
import com.zte.mos.domain.DataState;
import com.zte.mos.domain.ImagedSystem;
import com.zte.mos.domain.ModelMETA;
import com.zte.mos.msg.framework.CommServiceFactory;
import com.zte.mos.msg.framework.ISouthService;
import com.zte.mos.msg.framework.except.MsgFrameException;
import com.zte.mos.msg.framework.operation.PingResponse;
import com.zte.mos.task.SchedulingTask;
import com.zte.mos.task.TaskScheduler;
import com.zte.mos.util.basic.Logger;

import static com.zte.mos.domain.DataState.init;
import static com.zte.mos.domain.DataState.syncing;
import static com.zte.mos.util.basic.Logger.logger;

public class DiffPollingTask extends SchedulingTask
{
    private static Logger log = logger(DiffPollingTask.class);
    private final NeDomain ne;

    public DiffPollingTask(NeDomain ne)
    {
        this.ne = ne;
    }

    private boolean isPollable(ModelMETA model)
    {
        if (model.mosHead.version() > 1.1f)
        {
            return true;
        }
        return false;
    }

    @Override
    public void run()
    {
        ImagedSystem sys = ne.getSystem();
        if (sys == null){
            return;
        }
        DataState dataState = sys.getDataState();
        if (dataState == init )
        {
            if (isSupported(sys))
            {
                Runnable task = ReverseTaskFactory.createSystemTask(ne.getID(), sys);
                TaskScheduler.addTask(task);
            }
        }
        else
        {// synced, unsynced, syncing
            boolean isPollable = isPollable(sys.getModelMETA());
            if (isPollable && dataState != syncing)
            {
                poll();
            }
        }
    }

    private boolean isSupported(ImagedSystem sys){
        return true;
//        ModelHead head = sys.getModelMETA().modelHead;
//        return head.type.equals("nr8250") && head.version.equals("v242");
    }

    private void poll()
    {
        try
        {
            ISouthService sv = CommServiceFactory.getService();
            PingResponse response = sv.ping(ne.getID());
            DiffPollingFutureProcessor.add(response);
        }
        catch (MsgFrameException e)
        {
            //log.warn(ne.getID(), e);
            log.info(ne.getID() + " ping exception");
        }
    }

    @Override
    protected String identity()
    {
        return this.ne.getID();
    }
}
