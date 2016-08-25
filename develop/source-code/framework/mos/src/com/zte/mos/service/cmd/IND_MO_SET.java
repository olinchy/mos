package com.zte.mos.service.cmd;

import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoCmds;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.service.MOS;
import com.zte.mos.util.DistinguishedList;

import java.util.ArrayList;

/**
 * Created by root on 14-12-4.
 */
public class IND_MO_SET extends SET_MO
{
    @Override
    public MoCmds getCmd()
    {
        return MoCmds.MoSetInd;
    }

    @Override
    void handle(MoMsg msg, MOS mos, DistinguishedList<String> affectedDN) throws MOSException
    {
        super.handle(msg, mos, affectedDN);
        mos.commit(msg.getTransactionID());
    }

    protected void allocateTransaction(MoMsg msg, MOS mos) throws MOSException
    {
    }
}
