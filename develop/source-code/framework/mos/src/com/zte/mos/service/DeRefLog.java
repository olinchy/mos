package com.zte.mos.service;

import com.zte.mos.domain.DN;

/**
 * Created by olinchy on 16-2-15.
 */
public class DeRefLog extends RefLog
{
    public DeRefLog(String dn, DN from)
    {
        super(dn, from);
    }

    @Override
    protected ReferenceObject doRef(ReferenceObject obj)
    {
        if (obj != null)
        {
            obj.refBy.remove(from.toString());
        }
        return obj;
    }
}
