package com.zte.mos.msg.framework.operation;

import com.zte.mos.domain.TransactionLog;

/**
 * Created by luoqingkai on 15-5-21.
 */
public class AckResponse extends AbstractResponse
{
    private final TransactionLog transactionLog;
    private final int revision;

    public AckResponse(int result)
    {
        this(result, TransactionLog.nullTransactionLog, 0);
    }

    public AckResponse(int result, TransactionLog transactionLog, int revision)
    {
        super(result);
        this.transactionLog = transactionLog;
        this.revision = revision;
    }

    public void merge(AckResponse ack)
    {
        if (this.getResult() == 0)
        {
            this.setResult(ack.getResult());
        }
        this.transactionLog.merge(ack.transactionLog);
    }

    public TransactionLog getTransactionLog() {
        return transactionLog;
    }

    public int getRevision() {
        return revision;
    }
}
