package com.zte.container.ext.switchmodel.remote.handler;

import com.zte.mos.container.BundleObject;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.msg.framework.except.MsgFrameException;

import java.rmi.RemoteException;

/**
 * Created by ccy on 5/5/16.
 */
public interface EventHandler
{
    void onMsg(MoMsg msg, BundleObject currentBundle) throws MOSException, RemoteException;
}
