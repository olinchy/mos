package com.zte.mos.logging_service.samplers.msg;

import com.zte.mos.inf.MoCmds;
import com.zte.mos.logging_service.Log;
import com.zte.smartlink.MsgHandlingLog;

/**
 * Created by olinchy on 16-7-22.
 */
public class Action_Count_Sampler extends CountSampler
{
    @Override
    public boolean support(Log log)
    {
        return super.support(log) && ((MsgHandlingLog) log).msg.getCmd().equals(
                MoCmds.MoAction) && ((MsgHandlingLog) log).name.equalsIgnoreCase("handled");
    }

    @Override
    public String name()
    {
        return "Action Count";
    }
}
