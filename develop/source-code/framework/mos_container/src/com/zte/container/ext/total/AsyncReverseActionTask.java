package com.zte.container.ext.total;

import com.zte.mos.container.NeDomain;
import com.zte.mos.domain.ConnectionState;
import com.zte.mos.domain.ImagedSystem;
import com.zte.mos.exception.MOSException;
import com.zte.mos.imaged.ImagedMos;
import com.zte.mos.msg.framework.except.MsgFrameException;
import com.zte.mos.msg.framework.except.NotConnectException;
import com.zte.mos.msg.framework.operation.ReverseSynResponse;
import com.zte.mos.task.SchedulingTask;
import com.zte.mos.type.Pair;
import com.zte.mos.util.basic.Logger;
import com.zte.mos.util.msg.MoEvent;
import com.zte.smartlink.deliver.SmartLinkMsgService;

import static com.zte.container.ext.total.AsyncReverseCheckProcessor.addAsyncReverseChecker;
import static com.zte.container.ext.total.AsyncReverseCheckProcessor.isAsyncReverseActionPending;
import static com.zte.container.ext.util.PairComparator.isEqual;
import static com.zte.mos.util.basic.Logger.logger;


public class AsyncReverseActionTask extends SchedulingTask
{
    private static final long TIMEOUT = 30;

    private static Logger log = logger(AsyncReverseActionTask.class);
    private final long startTime;
    private final NeDomain ne;
    private ImagedSystem sys;

    public Pair<String, Object>[] getParas()
    {
        return paras;
    }

    private final Pair<String, Object>[] paras;


    public AsyncReverseActionTask(NeDomain ne, ImagedSystem sys, Pair<String, Object>[] paras)
    {
        this.ne = ne;
        this.sys = sys;
        this.paras = paras;
        this.startTime = System.currentTimeMillis();
    }

    public String getTargetId()
    {
        return ne.getID();
    }

    @Override
    public void run()
    {
        if (!isAsyncReverseActionPending(getTargetId()))
        {
            startReverseReq();
        }
        else
        {
            logger(this.getClass()).debug(getTargetId() + " is in syncing ");
            sendEvent(ReverseSyncResult.syncConflict);
        }
    }

    void sendEvent(ReverseSyncResult result)
    {
        try
        {
            MoEvent event = new MoEvent(getTargetId(), "");
            event.put("dn", getTargetId());
            event.put("reverseSyncResult", result.getErrorCode());
            if (paras != null)
            {
                for(Pair<String, Object> para : paras)
                {
                    event.put(para.first(), para.second());
                }
            }

            SmartLinkMsgService.send("BUNDLE", event);
        }
        catch (MOSException e)
        {
            e.printStackTrace();
        }
    }


    private void startReverseReq()
    {
        try
        {
            ReverseSynResponse response = SyncTool.sendReverseRequest(getTargetId());
            if (!response.isSuccess())
            {
                log.info(getTargetId() + " send to remote fail:" + response.getResult());
                failed(ReverseSyncResult.failed);
            }
            else
            {
                addAsyncReverseChecker(this);
            }
        }
        catch (NotConnectException e)
        {
            if(ne.getConnectionState() == ConnectionState.ONLINE)
            {
                log.debug(getTargetId() + " rpc is not ready, wait ....");
                sendEvent(ReverseSyncResult.failed);
            }
            else
            {
                failed(ReverseSyncResult.failed);
            }
        }
        catch (MsgFrameException e)
        {
            log.warn(getTargetId(), e);
            failed(ReverseSyncResult.failed);
        }
    }

    public boolean isTimeOut()
    {
        return (System.currentTimeMillis() - this.startTime) / 1000 > TIMEOUT;
    }

    public void onTimeOut()
    {
        logger(AsyncReverseActionTask.class).info("asyn reverse task timeout, targetId " + getTargetId());
        failed(ReverseSyncResult.timeout);
    }

    private void failed(ReverseSyncResult result)
    {
        ImagedMos mos = (ImagedMos)sys;
        mos.reverseFailed();
        sendEvent(result);
    }

    @Override
    protected String identity()
    {
        return getTargetId();
    }


    @Override
    public boolean equals(Object o)
    {
        if (!super.equals(o))
        {
            return false;
        }
        return isEqual(this.paras, ((AsyncReverseActionTask) o).paras);
    }

}


