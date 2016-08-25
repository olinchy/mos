package com.zte.mos.logging_service.samplers.msg;

import com.zte.mos.inf.MoCmds;
import com.zte.mos.logging_service.Log;
import com.zte.smartlink.MsgHandlingLog;

/**
 * Created by olinchy on 16-7-22.
 */
public class Ack_Cost_Sampler extends AverageCostSampler
{
    @Override
    public String name()
    {
        return "Ack Cost";
    }

    @Override
    public boolean support(Log log)
    {
        return super.support(log) && ((MsgHandlingLog) log).msg.getCmd().equals(MoCmds.MoAck);
    }

    @Override
    protected float getMarginCost()
    {
        return 5000;
    }
}
