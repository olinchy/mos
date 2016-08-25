package com.zte.mos.logging_service.samplers.msg;

import com.zte.mos.logging_service.Log;
import com.zte.mos.util.msg.MoConfigMsg;
import com.zte.smartlink.MsgHandlingLog;

/**
 * Created by olinchy on 16-7-22.
 */
public class Config_Cost_Sampler extends AverageCostSampler
{
    @Override
    public String name()
    {
        return "Config Cost";
    }

    @Override
    public boolean support(Log log)
    {
        return super.support(log) && ((MsgHandlingLog) log).msg instanceof MoConfigMsg;
    }
}
