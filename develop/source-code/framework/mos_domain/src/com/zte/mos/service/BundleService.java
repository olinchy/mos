package com.zte.mos.service;

import com.zte.mos.domain.NeIdentity;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.FindResult;
import com.zte.mos.message.Result;
import com.zte.mos.util.msg.MoActionMsg;
import com.zte.mos.util.msg.MoFindMsg;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by luoqingkai on 15-7-24.
 */
public interface BundleService extends Remote
{
    Result onMessage(MoMsg msg)throws RemoteException, MOSException;

    void createNeDomain(NeIdentity identity, int transId) throws RemoteException,MOSException;

    void deleteNeDomain(String dn, int transId)throws RemoteException,MOSException;

    void updateNeIP(String dn, String ip, int transId)throws RemoteException,MOSException;

    void updateModel(String dn, String type, String version) throws RemoteException;

    FindResult broadcast(MoFindMsg msg) throws RemoteException;


    void switchModelReq(MoActionMsg msg) throws RemoteException, MOSException;
    void switchModelAck(MoActionMsg msg) throws RemoteException, MOSException;

    String identity() throws RemoteException;

    void syncConfig(MoActionMsg action) throws RemoteException, MOSException;
}
