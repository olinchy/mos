package com.zte.domain.transaction;

import com.zte.mos.exception.MOSException;
import com.zte.mos.util.Singleton;
import com.zte.mos.util.basic.Logger;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by ccy on 7/16/16.
 */

class TransactionHolder
{
    private static final long TIMEOUT = 5 * 60 * 1000;

    TransactionHolder(Transactional transactional, String targetId, int transId)
    {
        this.transactional = transactional;
        this.targetId  = targetId;
        this.transId = transId;
        this.startTime = System.currentTimeMillis();
    }


    String getTargetId()
    {
        return this.targetId;
    }

    int getTransId()
    {
        return this.transId;
    }

    public boolean isTimeOut()
    {
        return (System.currentTimeMillis() - startTime) >= TIMEOUT;
    }

    public void onTimeOut()
    {
        try
        {
            transactional.onTimeOut(new TransactionContext(this.targetId, this.transId));
        }
        catch (MOSException e)
        {
            logger(this.getClass()).error(" ne transaction time out, target Id " + targetId + " transId " +  transId);
        }
    }

    private final Transactional transactional;
    private final String targetId;
    private final int transId;
    private final long startTime;

}

public class TransactionMonitorService
{
    private static Logger logger = logger(TransactionMonitorService.class);

    private ConcurrentLinkedQueue<TransactionHolder> queue = new ConcurrentLinkedQueue<TransactionHolder>();
    private ConcurrentHashMap<String, TransactionHolder> map = new ConcurrentHashMap<String, TransactionHolder>();

    private TransactionMonitorService()
    {

    }

    public static void startTransactionMonitorService() throws MOSException
    {
        Singleton.getInstance(TransactionMonitorService.class).start();
    }

    public static void startMonitorTransaction(Transactional transactional, String targetId, int transId) throws MOSException
    {
        Singleton.getInstance(TransactionMonitorService.class).add(transactional, targetId, transId);
    }

    public static void endMonitorTransaction(Transactional transactional, String targetId, int transId) throws MOSException
    {
        Singleton.getInstance(TransactionMonitorService.class).remove(transactional, targetId, transId);
    }


    private synchronized void add(Transactional transactional, String targetId, int transId)
    {
        logger.debug(" start add ne transaction, target id " + targetId + " transId " + transId);
        String key = toKey(targetId, transId);
        if (!map.containsKey(key))
        {
            TransactionHolder transactionHolder = new TransactionHolder(transactional, targetId, transId);
            queue.add(transactionHolder);
            map.put(key, transactionHolder);
        }
        else
        {
            logger.debug(" discard duplicated ne transaction, target id " + targetId + " transId " + transId);
        }

    }

    private void start()
    {
        new TransactionChecker().start();
    }

    private String toKey(String targeId, int transId)
    {
        return targeId + "_" + transId;
    }

    private synchronized void remove(Transactional transactional, String targetId, int transId)
    {
        logger.debug(" start remove ne transaction, target id " + targetId + " transId " + transId);
        TransactionHolder transactionHolder = map.remove(toKey(targetId, transId));
        if (transactionHolder != null)
        {
            queue.remove(transactionHolder);
        }
        else
        {
            logger.debug(" failed no ne transaction, target id " + targetId + " transId " + transId);
        }
    }

    private void check()
    {
        int len = queue.size();
        int i = 0;
        while (i < len && !queue.isEmpty())
        {
            TransactionHolder transactionHolder = queue.poll();
            i++;
            if (transactionHolder.isTimeOut())
            {
                transactionHolder.onTimeOut();
                map.remove(toKey(transactionHolder.getTargetId(), transactionHolder.getTransId()));
            }
            else
            {
                queue.add(transactionHolder);
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
                    Thread.sleep(5000);
                }
                catch (Throwable e)
                {
                    e.printStackTrace();
                }
            }
        }
    }


}
