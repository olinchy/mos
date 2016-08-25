package com.zte.mos.service.cmd;

import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoCmds;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.service.MOS;
import com.zte.mos.util.DistinguishedList;

/**
 * Created by root on 14-12-4.
 */
public class IND_MO_DEL extends DELETE_MO
{
    @Override
    public MoCmds getCmd()
    {
        return MoCmds.MoDeleteInd;
    }

    @Override
    void handle(MoMsg msg, MOS mos, DistinguishedList<String> affectedDN) throws MOSException
    {
        mos.deleteMO(msg.dn(), msg.getTransactionID());
        mos.commit(msg.getTransactionID());
    }

    protected void allocateTransaction(MoMsg msg, MOS mos) throws MOSException
    {
    }
}
