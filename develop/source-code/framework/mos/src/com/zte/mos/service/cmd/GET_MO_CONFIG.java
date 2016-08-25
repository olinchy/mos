package com.zte.mos.service.cmd;

import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoCmds;
import com.zte.mos.message.Mo;

/**
 * Created by root on 14-12-4.
 */
class GET_MO_CONFIG extends GET_MO
{
    @Override
    public MoCmds getCmd()
    {
        return MoCmds.MoGetConfigRequest;
    }

    @Override
    protected Mo additionOper(ManagementObject mo) throws MOSException
    {
        return mo.toMoClass();
    }
}
