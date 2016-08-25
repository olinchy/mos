package com.zte.mos.service.cmd;

import com.zte.mos.domain.BaseManagementObject;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoCmds;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.Mo;
import com.zte.mos.message.MoMetaClass;
import com.zte.mos.service.MOS;
import com.zte.mos.util.DistinguishedList;
import com.zte.mos.util.msg.MoConfigMsg;

import static com.zte.mos.service.cmd.FormatReference.formatRefDNInLocalContext;
import static com.zte.mos.service.cmd.MOValidator.validate;

/**
 * Created by root on 14-12-4.
 */
class CREATE_MO extends WriteCmdHandler
{
    @Override
    public MoCmds getCmd()
    {
        return MoCmds.MoCreateRequest;
    }

    @Override
    void handle(MoMsg msg, MOS mos, DistinguishedList<String> affectedDN) throws MOSException
    {
        ManagementObject mo = getMo((MoConfigMsg) msg, mos);

        mos.createMO(mo, msg.getTransactionID());

        affectedDN.addAll(mos.getAffectedDN(msg.getTransactionID()));
    }

    ManagementObject getMo(MoConfigMsg msg, MOS mos) throws MOSException
    {
        ManagementObject mo;

        Mo moClass = msg.getMo();
        Class<? extends ManagementObject> prototype;

        MoMetaClass meta = mos.getMeta(moClass.getMoClass());
        if (meta == null)
            throw new MOSException("cannot find meta of " + moClass.getMoClass());
        prototype = (Class<? extends ManagementObject>) meta.type;

        validate(prototype, moClass, false);

        mo = moClass.to(prototype);
        mo.setMos(mos);

        // FIXME: 16-2-25 set referenced dn in local context
        formatRefDNInLocalContext(mos, mo, moClass);

        mo.setDn(msg.dn());

        return mo;
    }
}
