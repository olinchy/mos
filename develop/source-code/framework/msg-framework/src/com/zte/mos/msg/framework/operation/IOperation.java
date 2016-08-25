package com.zte.mos.msg.framework.operation;

import com.zte.mos.inf.Maybe;


/**
 * Created by luoqingkai on 15-5-6.
 */
public interface IOperation {

    String getDN();

    int getTimeoutInMls();

    Maybe<Integer> getTransactionID();

    String getOperation();

    String getMoType();

    void setTransactionId(int i);
}
