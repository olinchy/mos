package com.zte.mos.service.cmd;

import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoCmds;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.FindResult;
import com.zte.mos.message.Mo;
import com.zte.mos.message.Result;
import com.zte.mos.service.MOS;

import static com.zte.mos.service.cmd.FormatReference.parseMoClass;

/**
 * Created by root on 14-12-4.
 */
class GET_MO extends FIND
{
    @Override
    public MoCmds getCmd()
    {
        return MoCmds.MoGetRequest;
    }

    @Override
    public Result execute(MoMsg msg, final MOS mos) throws MOSException
    {
        ManagementObject mo = mos.getMO(msg.dn(), msg.getTransactionID());

        if (mo != null)
        {
            Mo moClass = additionOper(mo);

            parseMoClass(mos, mo, moClass, msg.getTransactionID());

            return new FindResult(moClass, msg.getTransactionID());
        }
        return new FindResult(msg.getTransactionID());
    }

    protected Mo additionOper(ManagementObject mo) throws MOSException
    {
        return mo.afterGet();
    }
}
