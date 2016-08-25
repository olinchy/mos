package com.zte.mos.task;

import com.zte.container.ext.diff.DiffPollingTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by luoqingkai on 15-11-2.
 */

class TaskImpl implements Runnable
{
    TaskImpl(Runnable t)
    {
        this.t = t;
    }

    private final Runnable t;

    @Override
    public void run()
    {
        try
        {
            log("start run scheduled task");
            t.run();
            log("finish run scheduled task");
        }
        catch (Throwable e)
        {
            logger(TaskImpl.class).error("fail to run scheduled task [" + t.getClass() + "]", e);
        }
    }

    private void log(String desc)
    {
        if (t instanceof SchedulingTask)
        {
            if (!(t instanceof DiffPollingTask))
            {
                logger(TaskScheduler.class).debug(desc + " [" + t.toString() + "]");
            }
        }
    }
}

public class TaskScheduler
{
    private static LinkedBlockingQueue<Runnable> pendingTasks = new LinkedBlockingQueue<Runnable>();
    private static ExecutorService executor = new ThreadPoolExecutor(20, 20, 2, TimeUnit.SECONDS, pendingTasks);

    public static void addTask(Runnable t)
    {
        if (t instanceof SchedulingTask)
        {
            if (isPending(t))
            {
                logger(TaskScheduler.class).debug("discard duplicated task [" + t.toString() + "]");
                return;
            }

            if (!(t instanceof DiffPollingTask))
            {
                logger(TaskScheduler.class).debug("start add scheduled task [" + t.toString() + "]");
            }
            executor.execute(new TaskImpl(t));
            return;
        }
        logger(TaskScheduler.class).error("discard invalid task [" + t.toString() + "]");
    }

    private static boolean isPending(Runnable t)
    {
        return pendingTasks.contains(t);
    }

}
