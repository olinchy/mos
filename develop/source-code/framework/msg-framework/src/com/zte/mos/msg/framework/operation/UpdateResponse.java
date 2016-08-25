package com.zte.mos.msg.framework.operation;

/**
 * Created by luoqingkai on 15-5-21.
 */
public class UpdateResponse extends AbstractResponse {
    public final int transactionId;

    public UpdateResponse(int result, int transactionId) {
        super(result);
        this.transactionId = transactionId;
    }

}
