package com.zte.mos.service.cmd;

import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.AckException;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoCmds;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.AckResult;
import com.zte.mos.message.Result;
import com.zte.mos.persistent.Log;
import com.zte.mos.service.MOS;
import com.zte.mos.service.NoSuchTransException;
import com.zte.mos.service.ReferenceDBInBerkeley;
import com.zte.mos.service.SLRouterPool;
import com.zte.mos.util.basic.Logger;
import com.zte.mos.util.msg.MoAckMsg;

import java.util.List;

/**
 * Created by root on 14-12-4.
 */
class ACK implements MoCmdHandler
{
    @Override
    public MoCmds getCmd()
    {
        return MoCmds.MoAck;
    }

    @Override
    public Result execute(MoMsg msg, MOS mos) throws MOSException
    {
        MoAckMsg ack = (MoAckMsg) msg;
        if (ack.getAck().equals(MoAckMsg.Ack.commit))
        {
            List<Log<ManagementObject>> logs = null;
            try
            {
                Logger.logger(ACK.class).info("commit " + msg);
                logs = mos.commit(msg.getTransactionID());
                SLRouterPool.getRouter(mos.getRootExternalDN()).noti(logs);

                ReferenceDBInBerkeley.get(mos).commit(msg.getTransactionID());
                if (logs != null)
                    ReferenceDBInBerkeley.get(mos).refresh(logs);
            }
            catch (NoSuchTransException e)
            {
                Logger.logger(ACK.class).warn("no such transaction:" + msg.getTransactionID(), e);
                return new AckResult(msg.getTransactionID()).push(new AckException(e, msg.dns()));
            }
            catch (Throwable e)
            {
                Logger.logger(ACK.class).warn("committing data caught exception:" + msg.getTransactionID(), e);
                Logger.logger(ACK.class).warn("rollback mos and reference db");
                ReferenceDBInBerkeley.get(mos).rollback(msg.getTransactionID());

                AckException ackException;
                if (e instanceof MOSException)
                    ackException = new AckException((MOSException) e, msg.dns());
                else
                    ackException = new AckException(new MOSException(e), msg.dns());
                return new AckResult(msg.getTransactionID()).push(ackException);
            }
        }
        else
        {
            Logger.logger(ACK.class).info("rollback " + msg);
            mos.rollback(msg.getTransactionID());
            ReferenceDBInBerkeley.get(mos).rollback(msg.getTransactionID());
        }
        return new AckResult(msg.getTransactionID());
    }
}
