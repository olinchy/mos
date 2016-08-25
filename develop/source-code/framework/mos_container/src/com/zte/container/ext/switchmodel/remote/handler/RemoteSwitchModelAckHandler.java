package com.zte.container.ext.switchmodel.remote.handler;

import com.zte.container.ext.switchmodel.tm.ModelSwitchTransaction;
import com.zte.container.ext.switchmodel.tm.TransactionType;
import com.zte.mos.container.BundleObject;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.util.msg.action.ModelSwitchAck;

import java.rmi.RemoteException;

import static com.zte.container.ext.switchmodel.tm.TransactionManager.removeModelSwitchTransaction;
import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by ccy on 5/5/16.
 */
public class RemoteSwitchModelAckHandler implements EventHandler
{
    @Override
    public void onMsg(MoMsg msg, BundleObject bundleObject) throws MOSException, RemoteException
    {
        ModelSwitchAck req = (ModelSwitchAck)msg;
        ModelSwitchAck.Ack ack = (ModelSwitchAck.Ack)req.paras()[0].second();
        ModelSwitchTransaction trans = removeModelSwitchTransaction(req.dn(), TransactionType.remote);

        if (trans != null)
        {
            try
            {
                if (ack == ModelSwitchAck.Ack.commit)
                {
                    trans.commit();
                }
                else
                {
                    trans.rollback();
                }
            }
            catch (Throwable e)
            {
                logger(this.getClass()).error(trans.getTargetId() + " fail to commit model switch transaction", e);
                trans.rollback();
                throw new MOSException(e);
            }
        }


    }

}
