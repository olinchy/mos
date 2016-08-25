package com.zte.mos.logging_service.samplers.msg;

import com.zte.mos.inf.MoCmds;
import com.zte.mos.logging_service.Log;
import com.zte.smartlink.MsgHandlingLog;

/**
 * Created by olinchy on 16-7-22.
 */
public class Action_Cost_Sampler extends AverageCostSampler
{
    @Override
    public boolean support(Log log)
    {
        return super.support(log) && ((MsgHandlingLog) log).msg.getCmd().equals(
                MoCmds.MoAction) && ((MsgHandlingLog) log).name.equals("handled");
    }

    @Override
    public String name()
    {
        return "Action Cost";
    }

    @Override
    protected float getMarginCost()
    {
        return 3000;
    }

    @Override
    public String maxY()
    {
        return "3000 ms";
    }
}
