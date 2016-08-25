package com.zte.mos.logging_service.samplers.resource;

import com.zte.mos.logging_service.Log;
import com.zte.mos.massive.monitor.Sampler;
import com.zte.smartlink.RMILog;

/**
 * Created by olinchy on 16-7-22.
 */
public class RMIConnectionSampler implements Sampler
{
    private final Object lock = new Object();
    private RMILog log;

    @Override
    public String name()
    {
        return "RMI Connections";
    }

    @Override
    public String maxY()
    {
        return "200";
    }

    @Override
    public String startY()
    {
        return "0";
    }

    @Override
    public float rate()
    {
        synchronized (lock)
        {
            if (log != null)
            {
                return (((float) log.count) / 200);
            }
            return 0;
        }
    }

    @Override
    public void took(Log log)
    {
        synchronized (lock)
        {
            if (support(log))
                this.log = (RMILog) log;
        }
    }

    public boolean support(Log log)
    {
        return log instanceof RMILog;
    }
}
