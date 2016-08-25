package com.zte.container.ext.total;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by ccy on 3/31/16.
 */
public class AsyncReverseCheckProcessor
{
    private static AsyncReverseCheckProcessor instance = new AsyncReverseCheckProcessor();

    private static ConcurrentLinkedQueue<AsyncReverseActionTask> queue = new ConcurrentLinkedQueue<AsyncReverseActionTask>();
    private static ConcurrentHashMap<String, AsyncReverseActionTask> map = new ConcurrentHashMap<String, AsyncReverseActionTask>();

    static void addAsyncReverseChecker(AsyncReverseActionTask task)
    {
        logger(AsyncReverseCheckProcessor.class).info("start add async reverse action task , target Id " + task.getTargetId());
        queue.add(task);
        map.put(task.getTargetId(), task);
    }

    static AsyncReverseActionTask removeAsyncReverseChecker(String targetId)
    {
        logger(AsyncReverseCheckProcessor.class).info("start remove async reverse action task , target Id " + targetId);
        AsyncReverseActionTask task = map.remove(targetId);
        if (task != null)
        {
            queue.remove(task);
        }
        return task;
    }

    static boolean isAsyncReverseActionPending(String targetId)
    {
        return map.containsKey(targetId);
    }

    private AsyncReverseCheckProcessor()
    {
        new AsyncReverseCheckThread().start();
    }



    private void check()
    {
        int len = queue.size();
        int i = 0;
        while (i < len && !queue.isEmpty())
        {
            AsyncReverseActionTask task = queue.poll();
            i++;
            if (task.isTimeOut())
            {
                task.onTimeOut();
                map.remove(task.getTargetId());
            }
            else
            {
                queue.add(task);
            }
        }

    }


    private class AsyncReverseCheckThread extends Thread
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


