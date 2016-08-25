package com.zte.scope.common;

import com.zte.concept.IMoOperation;
import com.zte.scope.bundle.oper.NE_ACK;

import java.util.HashMap;

/**
 * Created by luoqingkai on 15-10-19.
 */
public class AckPool {
    private static HashMap<String, IMoOperation> operMap = new HashMap<String, IMoOperation>();

    static {
//        IMoOperation product_ack = new PRODUCT_ACK();
//        operMap.put(product_ack.mib(), product_ack);

        IMoOperation ne_ack = new NE_ACK();
        operMap.put(ne_ack.mib(), ne_ack);
    }

    public static IMoOperation getOperation(String mib) {
        return operMap.get(mib);
    }
}
