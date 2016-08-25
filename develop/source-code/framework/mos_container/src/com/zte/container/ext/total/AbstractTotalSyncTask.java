package com.zte.container.ext.total;

import com.zte.mos.domain.ImagedSystem;
import com.zte.mos.exception.LockedByTransException;
import com.zte.mos.exception.MOSException;
import com.zte.mos.msg.framework.except.MsgFrameException;
import com.zte.mos.task.SchedulingTask;
import com.zte.mos.type.Pair;
import com.zte.mos.util.basic.Logger;
import com.zte.mos.util.msg.MoEvent;
import com.zte.smartlink.deliver.SmartLinkMsgService;

import static com.zte.container.ext.total.ReverseSyncResult.failed;
import static com.zte.container.ext.total.ReverseSyncResult.syncConflict;
import static com.zte.container.ext.total.ReverseSyncResult.success;

import static com.zte.mos.util.basic.Logger.logger;

abstract class AbstractTotalSyncTask extends SchedulingTask
{
    private final String targetId ;
    private Pair<String, Object>[] paras;


    public AbstractTotalSyncTask(String targetId, Pair<String, Object>[] paras)
    {
        this.targetId = targetId;
        this.paras = paras;
    }


    protected ReverseSyncResult doSync(ImagedSystem sys, Logger log)
    {
        try
        {
            SyncTool.sync(sys, targetId);
            return success;
        }
        catch (SyncFailedException e)
        {
            log.error(e.getMessage() + ": " + targetId, e);
            return new ReverseSyncResult(e.getCode());
        }
        catch (LockedByTransException e)
        {
            log.error(e.getMessage() + ": " + targetId, e);
            return syncConflict;
        }
        catch (MsgFrameException e)
        {
            log.error("msg or parse fail:" + targetId, e);
            return failed;
        }
        catch (MOSException e)
        {
            log.error("reverse save fail:" + targetId, e);
            return failed;
        }
        catch (Exception e)
        {
            log.error("unknown error:" + targetId, e);
            return failed;
        }
    }

    protected String getTargetId() {
        return targetId;
    }

    protected MoEvent toEvent(ReverseSyncResult result) throws MOSException
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
        return event;
    }

    void sendEvent(ReverseSyncResult result)
    {
        try
        {
            logger(AbstractTotalSyncTask.class).debug("notify reverse sync result: " + result.getErrorCode() + " target id: " + this.targetId);
            MoEvent event = toEvent(result);
            SmartLinkMsgService.send("BUNDLE", event);
        }
        catch (MOSException e)
        {
            logger(AbstractTotalSyncTask.class).error(this.targetId + " fail to send event ", e);
        }
    }

    @Override
    protected String identity()
    {
        return this.getTargetId();
    }


}
