package com.zte.container.ext.switchmodel.remote.handler;

import com.zte.mos.container.BundleObject;
import com.zte.mos.exception.MOSException;
import com.zte.mos.util.msg.MoActionMsg;

import java.rmi.RemoteException;

/**
 * Created by ccy on 5/5/16.
 */
public enum ModelSwitchEventDef
{
    ModelSwitchReq(new RemoteSwitchModelReqHandler()),
    ModelSwitchAck(new RemoteSwitchModelAckHandler());

    ModelSwitchEventDef(EventHandler handler)
    {
        this.handler = handler;
    }

    public void onEvent(MoActionMsg msg, BundleObject bundleObject) throws MOSException, RemoteException
    {
        this.handler.onMsg(msg,bundleObject);
    }


    public static void handleSwitchEvent(MoActionMsg actionMsg, BundleObject bundleObject) throws MOSException, RemoteException
    {
        valueOf(actionMsg.actionName()).onEvent(actionMsg, bundleObject);
    }

    private EventHandler handler;

}
