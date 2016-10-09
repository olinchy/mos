package com.zte.domain.transaction;

/**
 * Created by ccy on 7/16/16.
 */
public class TransactionContext
{
    TransactionContext(String id, int transId)
    {
        this.id = id;
        this.transId = transId;
    }

    private final String id;
    private final int transId;

    public int getTransactionId()
    {
        return transId;
    }

    public String getTargeId()
    {
        return id;
    }
}
