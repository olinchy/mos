package com.zte.smartlink.deliver;

import com.zte.mos.util.basic.Logger;
import com.zte.mos.util.msg.IndicateMsg;
import com.zte.smartlink.Address;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by olinchy on 15-5-18.
 */
public class IndicationSender
{
    static HashMap<Address, SendingTask> map = new HashMap<Address, SendingTask>();
    private static HashMap<Address, BatchSendingTask> mapBatchTask = new HashMap<Address, BatchSendingTask>();

    private static void addJob(Address address, IndicateMsg indicateMsg)
    {
        SendingTask task = map.get(address);
        if (task == null)
        {
            task = new SendingTask(address);
            map.put(address, task);
        }
        task.put(indicateMsg);
    }

    public static void addJob(HashMap<Address, ArrayList<IndicateMsg>> jobList)
    {
        for (Address address : jobList.keySet())
        {
            for (IndicateMsg msg : jobList.get(address))
            {
                addJob(address, msg);
            }

            addPackedJob(address, jobList.get(address));
        }
    }

    private static void addPackedJob(Address address, ArrayList<IndicateMsg> indicateMsgs)
    {
        BatchSendingTask task = mapBatchTask.get(address);
        if (task == null)
        {
            task = new BatchSendingTask(address);
            mapBatchTask.put(address, task);
        }
        task.put(indicateMsgs);
    }

    private static class SendingTask extends Timer
    {
        static Logger logger = Logger.logger(SendingTask.class);
        private LinkedBlockingQueue<IndicateMsg> msgList = new LinkedBlockingQueue<IndicateMsg>();

        public SendingTask(final Address address)
        {
            this.schedule(getTimerTask(address), 20, 10);
        }

        protected TimerTask getTimerTask(final Address address)
        {
            return new TimerTask()
            {
                @Override
                public void run()
                {
                    IndicateMsg msg = msgList.poll();
                    if (msg != null)
                    {
                        try
                        {
                            logger.debug("ind " + msg.toString() + " to " + address.toString());
                            address.on(msg);
                        }
                        catch (Throwable throwable)
                        {
                            logger.error(
                                    "send " + msg.toString() + " to " + address.toString() + " caught exception!",
                                    throwable);
                        }
                    }
                }
            };
        }

        void put(IndicateMsg msg)
        {
            try
            {
                msgList.put(msg);
            }
            catch (InterruptedException e)
            {
            }
        }
    }

    private static class BatchSendingTask extends SendingTask
    {
        public BatchSendingTask(Address address)
        {
            super(address);
        }

        @Override
        protected TimerTask getTimerTask(final Address address)
        {
            return new TimerTask()
            {
                @Override
                public void run()
                {
                    ArrayList<IndicateMsg> msg = msgList.poll();
                    if (msg != null)
                    {
                        try
                        {
                            logger.debug("ind " + msg.toString() + " to " + address.toString() + " in package");
                            address.on(msg);
                        }
                        catch (Throwable throwable)
                        {
                            logger.error(
                                    "send " + msg.toString() + " to " + address.toString() + "in package caught exception!",
                                    throwable);
                        }
                    }
                }
            };
        }

        private LinkedBlockingQueue<ArrayList<IndicateMsg>> msgList = new LinkedBlockingQueue<ArrayList<IndicateMsg>>();

        public void put(ArrayList<IndicateMsg> indicateMsgs)
        {
            try
            {
                msgList.put(indicateMsgs);
            }
            catch (InterruptedException e)
            {
            }
        }
    }
}
