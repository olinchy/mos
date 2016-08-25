package com.zte.container.ext.switchmodel.tm;

import com.zte.container.ext.switchmodel.ModelSwitchContext;
import com.zte.mos.exception.ExistedModelSwitchTransaction;
import com.zte.container.ext.switchmodel.tm.local.LocalModelSwitchTransaction;
import com.zte.container.ext.switchmodel.tm.remote.RemoteModelSwitchTransaction;
import com.zte.mos.container.BundleObject;
import com.zte.mos.util.msg.action.ModelSwitchReq;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by ccy on 5/21/16.
 */
public class TransactionManager
{
    private static TransactionManager instance = new TransactionManager();

    private static ConcurrentLinkedQueue<ModelSwitchTransaction> queue = new ConcurrentLinkedQueue<ModelSwitchTransaction>();
    private static ConcurrentHashMap<String, ModelSwitchTransaction> map = new ConcurrentHashMap<String, ModelSwitchTransaction>();

    public static synchronized LocalModelSwitchTransaction createLocalModelTransaction(ModelSwitchContext context) throws ExistedModelSwitchTransaction
    {
        logger(TransactionManager.class).info("start create local model switch transaction , target Id " + context.getID());
        String key = toKey(context.getID(), TransactionType.local);
        if (map.containsKey(key))
        {
            throw new ExistedModelSwitchTransaction(key);
        }

        LocalModelSwitchTransaction trans = new LocalModelSwitchTransaction(context);
        queue.add(trans);
        map.put(key, trans);
        return trans;
    }

    public static synchronized ModelSwitchTransaction createRemoteModelTransaction(ModelSwitchReq msg, BundleObject bundleObject) throws ExistedModelSwitchTransaction
    {
        logger(TransactionManager.class).info("start create remote model switch transaction ");
        ModelSwitchTransaction remoteTrans = new RemoteModelSwitchTransaction(msg, bundleObject);
        String key = toKey(remoteTrans.getTargetId(), TransactionType.remote);
        if (map.containsKey(key))
        {
            throw new ExistedModelSwitchTransaction(key);
        }

        queue.add(remoteTrans);
        map.put(key, remoteTrans);
        return remoteTrans;
    }


    private static String toKey(String targetId, TransactionType type)
    {
        return targetId + "_" + type.name();
    }


    public static synchronized ModelSwitchTransaction removeModelSwitchTransaction(String targetId, TransactionType type)
    {
        logger(TransactionManager.class).info("start remove model switch transaction, target Id " + targetId);
        ModelSwitchTransaction trans = map.remove(toKey(targetId, type));
        if (trans != null)
        {
            queue.remove(trans);
        }
        return trans;
    }


    private TransactionManager()
    {
        new TransactionChecker().start();
    }



    private void check()
    {
        int len = queue.size();
        int i = 0;
        while (i < len && !queue.isEmpty())
        {
            ModelSwitchTransaction transaction = queue.poll();
            i++;

            if (transaction.isTimeOut())
            {
                transaction.onTimeOut();
                map.remove(toKey(transaction.getTargetId(), transaction.transType()));
            }
            else
            {
                queue.add(transaction);
            }
        }

    }


    private class TransactionChecker extends Thread
    {
        public void run()
        {
            while (true)
            {
                try
                {
                    check();
                    Thread.sleep(1000);
                }
                catch (Throwable e)
                {
                    e.printStackTrace();
                }
            }
        }
    }


}
