package com.zte.smartlink;

import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoCmds;
import com.zte.mos.message.Result;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by olinchy on 16-6-21.
 */
public class Counter
{
    private static ConcurrentLinkedQueue<MsgHandlingLog> queue = new ConcurrentLinkedQueue<MsgHandlingLog>();
    private static HashMap<String, HashMap<MoCmds, CounterFactor>> map = new HashMap<String, HashMap<MoCmds, CounterFactor>>();
    private static TimerTask msgInSum = new TimerTask()
    {
        @Override
        public void run()
        {
            logger(Counter.class).info("debugging in massive<Message>, msg in sum: " + map.toString());
        }
    };
    private static TimerTask heapOutput = new TimerTask()
    {
        @Override
        public void run()
        {
            Runtime runtime = Runtime.getRuntime();
            long mb = 1048567;
            MemoryLog log = new MemoryLog(
                    (runtime.totalMemory() - runtime.freeMemory()) / mb, runtime.totalMemory() / mb);
            LoggerToRemote.log(log);
        }
    };
    private static Timer timer = new Timer();
    private static Thread statistics = new Thread()
    {
        @Override
        public void run()
        {
            while (true)
            {
                try
                {
                    sleep(1);
                }
                catch (InterruptedException ignored)
                {
                }
                if (queue.isEmpty())
                    continue;

                push(queue.poll(), map);
            }
        }

        private void push(MsgHandlingLog log, HashMap<String, HashMap<MoCmds, CounterFactor>> map)
        {
            HashMap<MoCmds, CounterFactor> mapFactor = map.get(log.name);
            if (mapFactor == null)
            {
                map.put(log.name, mapFactor = new HashMap<MoCmds, CounterFactor>());
            }

            CounterFactor factor = mapFactor.get(log.msg.getCmd());
            if (factor == null)
                mapFactor.put(log.msg.getCmd(), factor = new CounterFactor());
            factor.count++;
            factor.cost += log.timeCost;
        }
    };
    private static ThreadGroup rmiThreadGroup = null;
    private static final TimerTask rmiThreadsCount = new TimerTask()
    {
        @Override
        public void run()
        {
            if (rmiThreadGroup == null)
                findThreadGroup("RMI");
            if (rmiThreadGroup != null)
            {
                RMILog log = new RMILog(rmiThreadGroup.activeCount());
                LoggerToRemote.log(log);
            }
        }

        private void findThreadGroup(String namePrefix)
        {
            ThreadGroup group = Thread.currentThread().getThreadGroup();
            while (group != null)
            {
                if (group.getName().startsWith("RMI"))
                {
                    rmiThreadGroup = group;
                    break;
                }
                ThreadGroup[] groups = new ThreadGroup[group.activeGroupCount()];
                group.enumerate(groups);
                for (ThreadGroup threadGroup : groups)
                {
                    if (threadGroup.getName().startsWith(namePrefix))
                    {
                        rmiThreadGroup = threadGroup;
                        break;
                    }
                }
                if (rmiThreadGroup != null)
                    break;
                group = group.getParent();
            }
        }
    };

    static
    {
        statistics.start();
        timer.schedule(msgInSum, 25000, 60000);
        timer.schedule(heapOutput, 3000, 5000);
        timer.schedule(rmiThreadsCount, 2000, 5000);
    }

    public static Result count(Executing executing, String name) throws MOSException, RemoteException
    {
        Date date = new Date();

        Result result = executing.execute();

        MsgHandlingLog msgHandlingLog;
        queue.add(
                msgHandlingLog = new MsgHandlingLog(executing.getMsg(), new Date().getTime() - date.getTime(), name));

        LoggerToRemote.log(msgHandlingLog);

        return result;
    }
}
