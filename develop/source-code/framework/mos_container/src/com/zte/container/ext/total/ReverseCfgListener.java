package com.zte.container.ext.total;

import com.fasterxml.jackson.databind.JsonNode;
import com.zte.mos.domain.TargetAddress;
import com.zte.mos.msg.framework.svr.ReverseListener;
import com.zte.mos.task.TaskScheduler;
import com.zte.mos.type.Pair;
import com.zte.mos.util.basic.Logger;

import static com.zte.container.ext.total.AsyncReverseCheckProcessor.removeAsyncReverseChecker;
import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by luoqingkai on 3/7/16.
 */
public class ReverseCfgListener implements ReverseListener
{
    private static Logger log = logger(ReverseCfgListener.class);

    @Override
    public void process(TargetAddress target, JsonNode moList, int sn, boolean isContinue)
    {
        if (!allMos(target))
        {
            log.info(" drop v1.1 unexpected reverse sync task " + target.getTargetID() + " modelName " + target.getModelHead().modelName() + " modelVersion " + target.getModelHead().modelVersion());
            return;
        }
        AsyncReverseActionTask task = removeAsyncReverseChecker(target.getTargetID());
        Pair<String, Object>[] paras = (task != null) ? task.getParas() : null;
        TaskScheduler.addTask(new V11TotalSyncTask(target, moList, sn, paras));
        if (task == null)
        {
            log.info("v1.1 unexpected reverse sync task " + target.getTargetID() + " modelName " + target.getModelHead().modelName() + " modelVersion " + target.getModelHead().modelVersion());
        }
        log.info("v1.1 reverse sync task added:" + target.getTargetID());
    }

    /**
     * @param target
     * @return true if ne mos support all mo models, otherwise false.
     * mos 1.1f only nr8960 support all mo models, nr8250 nr8120 etc only support tdm and rpm mo models.
     */
    private boolean allMos(TargetAddress target)
    {
        String modelName = target.getModelHead().modelName();
        String msgVersion = target.getModelHead().msgVersion();
        int result = msgVersion.compareTo("1.1f");
        if (result < 0)
        {
            return false;
        }
        else if (result == 0)
        {
            return modelName.toLowerCase().contains("nr8960");
        }
        else
        {
            return true;
        }
    }


}
