package com.zte.scope.bundle.oper;

import com.zte.concept.IDomain;
import com.zte.concept.IMoOperation;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.Result;
import com.zte.mos.util.msg.MoAckMsg;

/**
 * Created by luoqingkai on 15-12-10.
 */
public class PRODUCT_FIND implements IMoOperation {
    @Override
    public String mib() {
        return null;
    }

    @Override
    public Result doOperation(MoMsg msg, IDomain domain) throws MOSException {
        return null;
    }

    @Override
    public void ack(MoAckMsg ack, IDomain domain) throws MOSException {

    }
}
