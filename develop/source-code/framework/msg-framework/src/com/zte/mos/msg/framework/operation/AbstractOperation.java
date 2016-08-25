package com.zte.mos.msg.framework.operation;

import com.zte.mos.inf.Maybe;

/**
 * Created by luoqingkai on 15-5-6.
 * modify by luoqingkai to support targetID
 */
public abstract class AbstractOperation implements IOperation
{
    private String dn = null;
    private int timeout = 0;
    private Maybe<Integer> transactionId;

    public AbstractOperation(String dn)
    {
        this.dn = dn;
        this.timeout = Integer.MIN_VALUE;
    }

    public AbstractOperation(String dn, int timeout, Maybe<Integer> transactionId)
    {
        this.dn = dn;
        this.timeout = timeout;
        this.transactionId = transactionId;
    }

    @Override
    public String getDN()
    {
        return dn;
    }

    @Override
    public int getTimeoutInMls()
    {
        return this.timeout;
    }

    @Override
    public Maybe<Integer> getTransactionID()
    {
        if (this.transactionId == null)
        {
            this.transactionId = new Maybe<Integer>(null);
        }
        return this.transactionId;
    }

    @Override
    public void setTransactionId(int i)
    {
        this.transactionId = new Maybe<Integer>(i);
    }
}
