package com.zte.scope;

import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoCmds;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.Result;
import com.zte.mos.message.Successful;
import com.zte.mos.service.MOS;
import com.zte.mos.service.cmd.MoCmdHandler;
import com.zte.mos.util.msg.MoAckMsg;

/**
 * Created by luoqingkai on 15-8-17.
 */
public class UT_ACK implements MoCmdHandler {
    @Override
    public MoCmds getCmd() {
        return MoCmds.MoAck;
    }

    @Override
    public Result<?> execute(MoMsg msg, MOS mos) throws MOSException {
        if (msg.getCmd().equals(MoAckMsg.Ack.commit)){
            mos.commit(msg.getTransactionID());
        }else{
            mos.rollback(msg.getTransactionID());
        }
        return new Successful<Object>(msg.getTransactionID());
    }

}
