package com.zte.mos.logging_service.samplers.msg;

import com.zte.mos.inf.MoCmds;
import com.zte.mos.logging_service.Log;
import com.zte.smartlink.MsgHandlingLog;

/**
 * Created by olinchy on 16-7-21.
 */
public class Get_AverageCost_Sampler extends AverageCostSampler
{
    @Override
    public boolean support(Log log)
    {
        return super.support(log) && ((MsgHandlingLog) log).msg.getCmd().equals(MoCmds.MoGetRequest);
    }

    @Override
    public String name()
    {
        return "Average cost of Get";
    }
}
