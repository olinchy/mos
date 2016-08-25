package com.zte.mos.logging_service.samplers.msg;

import com.zte.mos.logging_service.Log;
import com.zte.mos.massive.monitor.Sampler;
import com.zte.smartlink.MsgHandlingLog;

/**
 * Created by olinchy on 16-7-21.
 */
public abstract class AverageCostSampler implements Sampler
{
    private final Object lock = new Object();
    private int current = 0;
    int count = 0;

    @Override
    public String maxY()
    {
        return getMarginCost() + " ms";
    }

    @Override
    public String startY()
    {
        return "0 ms";
    }

    @Override
    public float rate()
    {
        synchronized (lock)
        {
            float temp = current;
            int tempCount = count;
            current = 0;
            count = 0;
            if (tempCount != 0)
                return temp / tempCount / getMarginCost();
            return (float) 0.0;
        }
    }

    @Override
    public void took(Log log)
    {
        synchronized (lock)
        {
            MsgHandlingLog msgHandlingLog = (MsgHandlingLog) log;
            current += msgHandlingLog.timeCost;
            count++;
        }
    }

    public boolean support(Log log)
    {
        return log instanceof MsgHandlingLog;
    }

    protected float getMarginCost()
    {
        int default_cost = 1000;
        return default_cost;
    }
}
