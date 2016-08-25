package com.zte.mos.service.cmd;

import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.Maybe;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.ConfResult;
import com.zte.mos.message.Result;
import com.zte.mos.service.MOS;
import com.zte.mos.util.DistinguishedList;
import com.zte.mos.util.basic.Logger;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by root on 14-12-10.
 */
abstract class WriteCmdHandler implements MoCmdHandler
{
    private static final Logger log = logger(WriteCmdHandler.class);

    @Override
    public Result execute(MoMsg msg, MOS mos) throws MOSException
    {
        allocateTransaction(msg, mos);
        DistinguishedList<String> affectedDN = new DistinguishedList<String>();
        try
        {
            log.debug(" start handling " + msg.toString());
            handle(msg, mos, affectedDN);
            log.debug(" handle successfully with " + msg.toString());
        }
        catch (MOSException e)
        {
            log.warn(" handling failed with " + msg.toString(), e);
            return new ConfResult(e, affectedDN, msg.getTransactionID());
        }
        catch (Throwable e)
        {
            log.warn(" handling failed with " + msg.toString(), e);
            return new ConfResult(new MOSException(e), affectedDN, msg.getTransactionID());
        }
        return new ConfResult(affectedDN, msg.getTransactionID());
    }

    protected void allocateTransaction(MoMsg msg, MOS mos) throws MOSException
    {
        Maybe<Integer> msgTransID = msg.getTransactionID();
        if (msgTransID.nothing())
        {
            int transID = mos.startTransaction();
            msg.setTransId(new Maybe<Integer>(transID));
        }
    }

    abstract void handle(MoMsg msg, MOS mos, DistinguishedList<String> affectedDN) throws MOSException;
}
