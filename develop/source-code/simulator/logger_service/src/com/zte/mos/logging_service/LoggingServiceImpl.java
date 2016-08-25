package com.zte.mos.logging_service;

import com.zte.mos.logging_service.samplers.msg.*;
import com.zte.mos.logging_service.samplers.resource.MemorySampler;
import com.zte.mos.logging_service.samplers.resource.RMIConnectionSampler;
import com.zte.mos.massive.monitor.Sampler;
import com.zte.mos.util.basic.Logger;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by olinchy on 16-7-21.
 */
public class LoggingServiceImpl extends UnicastRemoteObject implements LoggingService
{
    final static Sampler[] SAMPLERS = new Sampler[]{
            new Get_AverageCost_Sampler(), new Get_Count_Sampler(),
            new Action_Count_Sampler(), new Action_Cost_Sampler(),
            new Event_Count_Sent_Sampler(), new Event_Cost_Sent_Sampler(),
            new Event_Count_Handled_Sampler(), new Event_Cost_Handled_Sampler(),
            new Config_Cost_Sampler(), new Config_Count_Sampler(), new Ack_Cost_Sampler(), new Ack_Count_Sampler(),
            new MemorySampler(), new RMIConnectionSampler()
    };

    protected LoggingServiceImpl() throws RemoteException
    {
    }

    @Override
    public void log(Log log) throws RemoteException
    {
        Logger.logger(this.getClass()).info(log);
        for (Sampler sampler : SAMPLERS)
        {
            if (sampler.support(log))
                sampler.took(log);
        }
    }
}
