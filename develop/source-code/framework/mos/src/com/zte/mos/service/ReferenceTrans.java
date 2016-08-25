package com.zte.mos.service;

import com.zte.mos.domain.DN;
import com.zte.mos.exception.MOSException;

import java.util.ArrayList;

/**
 * Created by olinchy on 16-2-14.
 */
public class ReferenceTrans
{
    private ArrayList<RefLog> logList = new ArrayList<RefLog>();

    public void deRef(String dn, DN from)
    {
        logList.add(new DeRefLog(dn, from));
    }

    public void ref(String dn, DN from)
    {
        logList.add(new RefLog(dn, from));
    }

    public void commit(ReferenceDBInBerkeley referenceDBInBerkeley) throws MOSException
    {
        for (RefLog refLog : logList)
        {
            refLog.merge(referenceDBInBerkeley);
        }
    }

    public ReferenceObject get(ReferenceDBInBerkeley referenceDBInBerkeley, String dn)
    {
        if (0 == logList.size()) return null;

        ReferenceObject obj = referenceDBInBerkeley.get(dn);
        for (int i = logList.size() - 1; i >= 0; i--)
        {
            RefLog log = logList.get(i);
            if (log.dn.equals(dn))
            {
                log.doRef(obj);
            }
        }
        return obj;
    }
}
