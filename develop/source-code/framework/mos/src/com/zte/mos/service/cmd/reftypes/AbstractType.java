package com.zte.mos.service.cmd.reftypes;

import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.Maybe;
import com.zte.mos.persistent.Log;
import com.zte.mos.service.MOS;
import com.zte.mos.service.cmd.RefByType;
import com.zte.mos.util.DistinguishedList;

import java.util.ArrayList;

/**
 * Created by olinchy on 15-7-1.
 */
public abstract class AbstractType implements RefByType
{
    @Override
    public void ref(DistinguishedList<String> affectedDN, MOS mos, Log<ManagementObject> log, Maybe<Integer> transId) throws MOSException
    {
        ManagementObject mo = getValue(log);
        if (mo.isGroup())
        {
            return;
        }

        doRef(affectedDN, mos, log, transId);
    }

    protected abstract void doRef(DistinguishedList<String> affectedDN, MOS mos, Log<ManagementObject> log, Maybe<Integer> transId) throws MOSException;

    protected abstract ManagementObject getValue(Log<ManagementObject> log);


}

