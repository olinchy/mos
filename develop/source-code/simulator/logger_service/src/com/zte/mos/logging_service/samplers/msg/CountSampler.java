package com.zte.mos.logging_service.samplers.msg;

import com.zte.mos.logging_service.Log;

/**
 * Created by olinchy on 16-7-21.
 */
public abstract class CountSampler extends AverageCostSampler
{
    private final Object lock = new Object();
    private int default_count = 500;

    @Override
    public String maxY()
    {
        return default_count + " times";
    }

    @Override
    public String startY()
    {
        return "0 times";
    }

    @Override
    public float rate()
    {
        synchronized (lock)
        {
            int tempCount = count;
            count = 0;
            return (float) tempCount / getMarginCount();
        }
    }

    protected float getMarginCount()
    {
        return default_count;
    }

    @Override
    public void took(Log log)
    {
        synchronized (lock)
        {
            count++;
        }
    }
}
