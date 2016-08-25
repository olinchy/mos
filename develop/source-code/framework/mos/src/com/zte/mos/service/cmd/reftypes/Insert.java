package com.zte.mos.service.cmd.reftypes;

import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.Maybe;
import com.zte.mos.persistent.Log;
import com.zte.mos.service.MOS;
import com.zte.mos.service.cmd.Ref;
import com.zte.mos.util.DistinguishedList;

/**
 * Created by olinchy on 15-7-1.
 */
public class Insert extends AbstractType
{
    @Override
    protected void doRef(
            DistinguishedList<String> affectedDN, MOS mos, Log<ManagementObject> log,
            Maybe<Integer> transId) throws MOSException
    {
        ManagementObject mo = this.getValue(log);
        mo.setMos(mos);
        affectedDN.addAll(Ref.add(mo, transId, mos));
    }

    @Override
    protected ManagementObject getValue(Log<ManagementObject> log)
    {
        return log.newValue();
    }
}
