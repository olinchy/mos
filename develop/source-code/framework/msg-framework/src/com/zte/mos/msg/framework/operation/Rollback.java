package com.zte.mos.msg.framework.operation;

import com.zte.mos.inf.Maybe;

/**
 * Created by luoqingkai on 15-5-21.
 */
public class Rollback extends NeOperation
{
    public Rollback(String dn, int timeout, Maybe<Integer> transactionId)
    {
        super(dn, timeout, transactionId);
    }

    @Override
    public String getOperation()
    {
        return OperEnum.Rollback.name();
    }
}
