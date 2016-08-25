package com.zte.mos.service;

import com.zte.mos.domain.DN;
import com.zte.mos.exception.MOSException;

/**
 * Created by olinchy on 16-2-15.
 */
public class RefLog
{
    public RefLog(String dn, DN from)
    {
        this.dn = dn;
        this.from = from.toString();
    }

    protected final String dn;
    protected final String from;

    public void merge(ReferenceDBInBerkeley referenceDBInBerkeley) throws MOSException
    {
        ReferenceObject obj = referenceDBInBerkeley.get(dn);
        obj = doRef(obj);
        if (obj != null)
            referenceDBInBerkeley.put(obj);
    }

    protected ReferenceObject doRef(ReferenceObject obj)
    {
        if (obj == null)
        {
            obj = new ReferenceObject();
            obj.dn = dn;
        }
        obj.refBy.add(from);
        return obj;
    }

    @Override
    public String toString()
    {
        return this.getClass().getSimpleName() + "{" +
                "dn='" + dn + '\'' +
                ", from=" + from +
                '}';
    }
}
