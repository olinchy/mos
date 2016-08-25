package com.zte.mos.message;

import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoMsg;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface MsgService extends Remote
{
    Result<?> onMsg(MoMsg msg) throws RemoteException, MOSException;

    Result<?> onMsg(ArrayList<? extends MoMsg> msg) throws RemoteException, MOSException;
}
