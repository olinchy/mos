package com.zte.mos.msg.framework.operation;

import com.zte.mos.inf.Maybe;

/**
 * Created by olinchy on 15-12-3.
 */
public abstract class NeOperation extends AbstractOperation
{

    public NeOperation(String dn)
    {
        super(dn);
    }

    public NeOperation(String dn, int timeout, Maybe<Integer> transactionId)
    {
        super(dn, timeout, transactionId);
    }

    @Override
    public String getMoType()
    {
        return "Ne";
    }
}
