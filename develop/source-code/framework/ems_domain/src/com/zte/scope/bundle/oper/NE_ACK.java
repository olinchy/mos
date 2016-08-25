package com.zte.scope.bundle.oper;

import com.zte.concept.IDomain;
import com.zte.concept.IMoOperation;
import com.zte.mos.domain.DN;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.Result;
import com.zte.mos.message.Successful;
import com.zte.mos.util.basic.Logger;
import com.zte.mos.util.msg.MoAckMsg;
import com.zte.scope.bundle.BundleDomain;

import static com.zte.domain.transaction.TransactionMonitorService.endMonitorTransaction;
import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by luoqingkai on 15-8-19.
 */
public class NE_ACK implements IMoOperation{
    private static Logger log = logger(NE_ACK.class);
    @Override
    public String mib() {
        return "/Ems/Ne";
    }

    @Override
    public Result doOperation(MoMsg msg, IDomain domain) throws MOSException {
        MoAckMsg ack = (MoAckMsg)msg;
        ack(ack, domain);
        return new Successful();
    }

    @Override
    public void ack(MoAckMsg ack, IDomain domain) throws MOSException {
        BundleDomain bundleDomain = (BundleDomain)domain;
        String[] dns = ack.dns();

        for(String dn : dns)
        {
            if (new DN(dn).value(-2).equalsIgnoreCase("Ne"))
            {
                endMonitorTransaction(bundleDomain, dn + "/Product/1", ack.getTransactionID().getValue());
            }
        }

        try
        {
            if (bundleDomain != null)
            {
                if (ack.getAck().equals(MoAckMsg.Ack.commit))
                {
                    bundleDomain.commit(ack.getTransactionID().getValue());
                }
                else
                {
                    bundleDomain.rollback(ack.getTransactionID().getValue());
                }
            }
            else
            {
                logger(NE_ACK.class).error(" bundle is null in ne ack , ack type " + ack.getAck().name() + " msg" + ack);
            }
        }
        catch (Throwable e)
        {
            logger(NE_ACK.class).error(" fail to do ack , type " + ack.getAck().name() + " msg" + ack);
        }
    }

}
