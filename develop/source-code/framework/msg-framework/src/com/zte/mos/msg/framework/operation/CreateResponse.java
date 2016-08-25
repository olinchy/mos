package com.zte.mos.msg.framework.operation;

/**
 * Created by luoqingkai on 15-5-21.
 */
public class CreateResponse extends AbstractResponse {
    private int transactionId;

    public CreateResponse(int result, int transactionId) {
        super(result);
        this.transactionId = transactionId;
    }

    public int getTransactionId() {
        return transactionId;
    }
}
