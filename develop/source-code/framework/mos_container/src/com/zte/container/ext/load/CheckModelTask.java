package com.zte.container.ext.load;

import com.zte.mos.container.NeDomain;
import com.zte.mos.container.RealBundleDomain;
import com.zte.mos.exception.MOSException;
import com.zte.mos.service.BundleService;
import com.zte.mos.task.SchedulingTask;
import com.zte.mos.util.basic.Logger;
import com.zte.mos.util.msg.MoEvent;
import com.zte.smartlink.deliver.SmartLinkMsgService;

import static com.zte.mos.util.basic.Logger.logger;


public class CheckModelTask extends SchedulingTask
{
    private static final String CHECK_MODEL_COMPLETE = "/Ems/Ne/Product/LoadModelComplete";

    enum CHECK_MODEL_RESULT
    {
        success, failed
    }

    private static final Logger log = logger(CheckModelTask.class);
    private NeDomain ne;
    private String model;
    private String version;
    private String neId;
    private final BundleService localService;


    public CheckModelTask(String model, String version, String neId, BundleService localService)
    {
        this.model = model.toLowerCase();
        this.version = version.toLowerCase();
        this.neId = neId;
        this.ne = RealBundleDomain.getInstance().getChild(neId);
        this.localService = localService;
    }

    @Override
    public void run()
    {
        if (ne == null)
        {
            log.warn(neId + " not exist in checkModelTask");
            return;
        }
        try
        {
            ne.updateModel(this.model, this.version, localService);
            sendEvent(toEvent(this.ne.getID(), CHECK_MODEL_RESULT.success, null));
        }
        catch (Throwable e)
        {
            log.error(ne.getID() + " fail to run check model task ", e);
            sendEvent(toEvent(this.ne.getID(), CHECK_MODEL_RESULT.failed, e));
        }

    }

    private void sendEvent(MoEvent event)
    {
        try
        {
            logger(CheckModelTask.class).debug("notify check model result: " + event);
            SmartLinkMsgService.send("BUNDLE", event);
        }
        catch (MOSException e)
        {
            e.printStackTrace();
        }
    }

    private MoEvent toEvent(String targetId, CHECK_MODEL_RESULT result, Throwable e)
    {
        MoEvent event = new MoEvent(CHECK_MODEL_COMPLETE, "");
        event.put("targetId", targetId);
        event.put("result", result.name());
        event.put("ex", e);
        return event;
    }

    @Override
    protected String identity()
    {
        return ne.getID();
    }
}
