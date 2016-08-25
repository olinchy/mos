package com.zte.container.ext.switchmodel.remote.handler;

import com.zte.container.ext.switchmodel.tm.ModelSwitchTransaction;
import com.zte.mos.container.BundleObject;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.util.msg.action.ModelSwitchReq;

import java.rmi.RemoteException;

import static com.zte.container.ext.switchmodel.tm.TransactionManager.createRemoteModelTransaction;

/**
 * Created by ccy on 5/5/16.
 */
public class RemoteSwitchModelReqHandler implements EventHandler
{
    @Override
    public void onMsg(MoMsg msg, BundleObject bundleObject) throws MOSException, RemoteException
    {
        ModelSwitchTransaction remoteTrans = createRemoteModelTransaction((ModelSwitchReq) msg, bundleObject);
        remoteTrans.start();
    }


}
