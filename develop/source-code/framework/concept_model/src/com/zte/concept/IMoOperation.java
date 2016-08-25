package com.zte.concept;

import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.Result;
import com.zte.mos.util.msg.MoAckMsg;

/**
 * Created by luoqingkai on 15-7-16.
 */
public interface IMoOperation {

    String mib();
    Result doOperation(MoMsg msg, IDomain domain) throws MOSException;
    void ack(MoAckMsg ack, IDomain domain) throws MOSException;

}
