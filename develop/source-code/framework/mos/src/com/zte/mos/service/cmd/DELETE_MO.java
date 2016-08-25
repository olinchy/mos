package com.zte.mos.service.cmd;

import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.Maybe;
import com.zte.mos.inf.MoCmds;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.service.MOS;
import com.zte.mos.util.DistinguishedList;

import java.util.ArrayList;

/**
 * Created by root on 14-12-4.
 */
class DELETE_MO extends WriteCmdHandler
{
    @Override
    public MoCmds getCmd()
    {
        return MoCmds.MoDeleteRequest;
    }

    @Override
    void handle(MoMsg msg, MOS mos, DistinguishedList<String> affectedDN) throws MOSException
    {
        ManagementObject mo = mos.getMO(msg.dn(), msg.getTransactionID());
        if (mo == null)
        {
            return;
        }

        if (mo.isGroup())
        {
            ManagementObject[] children = mo.ls();
            for (ManagementObject child : children)
            {
                delete(child.dn().toString(), msg.getTransactionID(), mos, affectedDN);
            }
        }
        else
        {
            delete(mo.dn().toString(), msg.getTransactionID(), mos, affectedDN);
        }
    }

    private void delete(String dn, Maybe<Integer> transId, MOS mos, DistinguishedList<String> affectedDN) throws MOSException
    {
        ManagementObject mo = mos.deleteMO(dn, transId);
        affectedDN.addAll(mos.getAffectedDN(transId));
        mo.destroy();
    }

    public void handle(
            MOS mos, ManagementObject mo, Maybe<Integer> transId, DistinguishedList<String> affectDN) throws MOSException
    {
        ManagementObject toDelete = mos.delete(mo, transId);
        affectDN.addAll(mos.getAffectedDN(transId));
        toDelete.destroy();
    }
}
