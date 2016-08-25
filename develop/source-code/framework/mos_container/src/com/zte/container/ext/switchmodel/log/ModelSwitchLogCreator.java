package com.zte.container.ext.switchmodel.log;

import com.zte.mos.exception.MOSException;
import com.zte.mos.message.Mo;
import com.zte.mos.persistent.Log;
import com.zte.mos.service.MOS;
import com.zte.mos.util.structure.tree.MoTree;
import edu.emory.mathcs.backport.java.util.Arrays;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static com.zte.container.ext.switchmodel.log.ModelSwitchLogCreatorHelper.createFaileMoLogsRecursively;
import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by ccy on 5/23/16.
 */

class ModelSwitchLogCreatorHelper
{
    static List<Log<Mo>> createFaileMoLogsRecursively(Mo failedMo, MoTree tree)
    {
        Queue<Mo> queue = new LinkedList<Mo>();
        queue.add(failedMo);
        List<Log<Mo>> list = new ArrayList<Log<Mo>>();
        while(!queue.isEmpty())
        {
            Mo temp = queue.poll();
            list.add(new Log<Mo>(temp, null, Log.LogType.Delete));
            addChildren(temp, tree, queue);
        }
        return list;
    }

    private static void addChildren(Mo mo,  MoTree tree, Queue<Mo> queue)
    {
        queue.addAll(tree.getChildren(mo));
    }
}


public enum ModelSwitchLogCreator implements LogCreator<Mo>
{
    failed
            {
                @Override
                public List<Log<Mo>> createLogs(Context ctx)
                {
                    MergeFailedContext context = (MergeFailedContext)ctx;
                    return createFaileMoLogsRecursively(context.failedMo(), context.moTree());
                }
            },
    leaveby
            {
                @Override
                public List<Log<Mo>> createLogs(Context ctx)
                {
                    MergeLeaveByContext context = (MergeLeaveByContext)ctx;
                    Queue<Mo> queue = context.leaveByQueue();
                    List<Log<Mo>> list = new ArrayList<Log<Mo>>();
                    while(!queue.isEmpty())
                    {
                        Mo mo = queue.poll();
                        list.addAll(createFaileMoLogsRecursively(mo, context.moTree()));
                    }
                    return list;
                }
            },
    success
            {
                @Override
                public List<Log<Mo>> createLogs(Context ctx)
                {
                    MergeSuccessfulContext context = (MergeSuccessfulContext)ctx;
                    List<Log<Mo>> list = new ArrayList<Log<Mo>>();
                    Mo origin = context.originMo();
                    List<MoOperateLog> logs = context.moOperateLogs();

                    if (dnChanged(origin, logs))
                    {
                        return Arrays.asList(new Log[]{new Log(context.originMo(), null, Log.LogType.Delete)});
                    }

                    for(MoOperateLog log : logs)
                    {
                        if (!log.getMo().getDn().toString().equalsIgnoreCase(origin.getDn().toString()))
                        {
                            continue;
                        }
                        try
                        {
                            list.add(new Log<Mo>(origin, context.rawMos().getMO(origin.getDn().toString(),
                                                                                MOS.SWITCH_MODEL_TRANS_ID).toMoClass(), Log.LogType.Update));
                        }
                        catch (MOSException e)
                        {
                            logger(ModelSwitchLogCreator.class).error(" fail to create logs ", e);
                        }
                    }
                    return list;
                }

                private boolean dnChanged(Mo origin, List<MoOperateLog> moOperateLogs)
                {
                    boolean result = true;
                    for(MoOperateLog log : moOperateLogs)
                    {
                        if (log.getMo().getDn().toString().equalsIgnoreCase(origin.getDn().toString()))
                        {
                            result = false;
                            break;
                        }
                    }
                    return result;
                }
            };

}
