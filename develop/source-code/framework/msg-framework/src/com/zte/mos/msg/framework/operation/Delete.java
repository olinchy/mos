package com.zte.mos.msg.framework.operation;

import com.zte.mos.domain.ManagementObject;
import com.zte.mos.inf.Maybe;

/**
 * Created by luoqingkai on 15-5-21.
 *
 */
public class Delete extends WriteOperation {
    public Delete(int timeout, Maybe<Integer> transactionId, ManagementObject mo) {
        super(timeout, transactionId, mo);
    }

    @Override
    public String getOperation() {
        return OperEnum.Delete.name();
    }

}
