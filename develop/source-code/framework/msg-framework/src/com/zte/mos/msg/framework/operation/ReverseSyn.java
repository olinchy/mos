package com.zte.mos.msg.framework.operation;

import com.zte.mos.inf.Maybe;

/**
 * Created by zhangbin10086509 on 15-6-3.
 */
public class ReverseSyn extends NeOperation
{

    public ReverseSyn(String dn)
    {
        super(dn);
    }

    public ReverseSyn(String dn, int timeout, Maybe<Integer> transactionId)
    {
        super(dn, timeout, transactionId);
    }

    @Override
    public String getOperation()
    {
        return OperEnum.ReverseSync.name();
    }

}
