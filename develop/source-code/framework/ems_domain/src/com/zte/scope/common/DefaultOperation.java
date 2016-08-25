package com.zte.scope.common;

import com.zte.concept.IDomain;
import com.zte.concept.IMoOperation;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoCmds;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.Failure;
import com.zte.mos.message.Result;
import com.zte.mos.service.MOS;
import com.zte.mos.service.cmd.MoCommandExecutor;
import com.zte.mos.util.msg.MoAckMsg;
import com.zte.mos.util.msg.MoFindMsg;

/**
 * Created by luoqingkai on 15-7-16.
 */
public class DefaultOperation implements IMoOperation {
    @Override
    public String mib() {
        return null;
    }

    @Override
    public Result doOperation(MoMsg msg, IDomain domain) {
        MOS mos = domain.getMos();
        Result result;
        try
        {
            if (msg.getCmd().equals(MoCmds.MoFindRequest)
                    && !EmsUtil.isLocal((MoFindMsg)msg))
            {
                result = domain.broadcast((MoFindMsg)msg);
            }
            else
            {
                result = MoCommandExecutor.execute(msg, mos);
            }

        }
        catch (MOSException e)
        {
            result = new Failure(e, msg.getTransactionID());
        }
        return result;
    }

    @Override
    public void ack(MoAckMsg ack, IDomain domain) throws MOSException
    {
        MoCommandExecutor.execute(ack, domain.getMos());
    }

}
