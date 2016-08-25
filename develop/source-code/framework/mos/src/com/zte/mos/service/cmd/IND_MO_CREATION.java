package com.zte.mos.service.cmd;

import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoCmds;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.service.MOS;
import com.zte.mos.util.DistinguishedList;

import java.util.ArrayList;

/**
 * Created by root on 14-12-4.
 */
class IND_MO_CREATION extends CREATE_MO
{
    @Override
    public MoCmds getCmd()
    {
        return MoCmds.MoCreateInd;
    }

    protected void allocateTransaction(MoMsg msg, MOS mos) throws MOSException
    {
    }

    @Override
    void handle(MoMsg msg, MOS mos, DistinguishedList<String> affectedDN) throws MOSException
    {
        ManagementObject mo = mos.getMO(msg.dn(), msg.getTransactionID());
        if (mo != null)
        {
            mos.updateMO(mo, null, msg.getTransactionID());
        }
        else
        {
            mos.createMO(mo, msg.getTransactionID());
        }

        mos.commit(msg.getTransactionID());
    }
}
