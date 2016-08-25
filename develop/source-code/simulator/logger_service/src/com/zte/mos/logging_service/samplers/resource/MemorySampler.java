package com.zte.mos.logging_service.samplers.resource;

import com.zte.mos.logging_service.Log;
import com.zte.mos.massive.monitor.Sampler;
import com.zte.smartlink.MemoryLog;

/**
 * Created by olinchy on 16-7-22.
 */
public class MemorySampler implements Sampler
{
    private MemoryLog log;
    private final Object lock = new Object();

    @Override
    public String name()
    {
        return "Memory";
    }

    @Override
    public String maxY()
    {
        return "512 M";
    }

    @Override
    public String startY()
    {
        return "0 M";
    }

    @Override
    public float rate()
    {
        if (log != null)
        {
            float used = log.used;
            float total = log.total;

            return used / total;
        }
        else
        {
            return 0;
        }
    }

    @Override
    public void took(Log log)
    {
        synchronized (lock)
        {
            if (support(log))
                this.log = (MemoryLog) log;
        }
    }

    public boolean support(Log log)
    {
        return log instanceof MemoryLog;
    }
}
