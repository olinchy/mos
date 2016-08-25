package com.zte.mos.service.cmd;

import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoCmds;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.Result;
import com.zte.mos.service.MOS;

/**
 * Created by root on 14-12-1.
 */
public interface MoCmdHandler
{
    MoCmds getCmd();

    Result<?> execute(MoMsg msg, MOS mos) throws MOSException;
}
