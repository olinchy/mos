package com.zte.mos.inf;

import com.zte.mos.exception.MOSException;
import com.zte.mos.message.Result;

import java.rmi.*;

/**
 * Created by olinchy on 10/13/14 for MO_JAVA.
 */
public interface MosContainerService extends Remote
{
    void startMos(String type, String jsonObject) throws RemoteException, MOSException;

    void createMos(String type, String jsonObject) throws RemoteException, MOSException;

    void stopMos(String dn) throws RemoteException, MOSException;

    Result onMsg(MoMsg msg, String rootDN) throws MOSException, RemoteException;

    void act(String mosDN, MoMsg msg) throws RemoteException, MOSException;
}
