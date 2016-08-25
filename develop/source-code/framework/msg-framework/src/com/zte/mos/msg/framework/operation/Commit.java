package com.zte.mos.msg.framework.operation;

import com.zte.mos.inf.Maybe;

/**
 * Created by luoqingkai on 15-5-21.
 */
public class Commit extends NeOperation
{
    private Maybe<Integer> localTransId;

    public Commit(String dn, int timeout, Maybe<Integer> transactionId)
    {
        super(dn, timeout, transactionId);
    }

    public Commit(String dn, Maybe<Integer> transId)
    {
        super(dn, 50, transId);
    }

    public Commit(String dn,Maybe<Integer> localTransId,Maybe<Integer> remoteTransId)
    {
        this(dn,remoteTransId);
        this.localTransId = localTransId;

    }



    @Override
    public final String getOperation()
    {
        return OperEnum.Commit.name();
    }

    public Maybe<Integer> getLocalTransId()
    {
        return localTransId;
    }
}
