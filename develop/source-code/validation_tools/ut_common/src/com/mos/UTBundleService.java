package com.mos;

import com.zte.mos.domain.NeIdentity;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.FindResult;
import com.zte.mos.message.Result;
import com.zte.mos.service.BundleService;
import com.zte.mos.util.msg.MoActionMsg;
import com.zte.mos.util.msg.MoFindMsg;

import java.rmi.RemoteException;

/**
 * Created by luoqingkai on 15-8-12.
 */
public class UTBundleService implements BundleService {
    private Result onMsgResult;

    public void setOnMsgResult(Result onMsgResult) {
        this.onMsgResult = onMsgResult;
    }

    @Override
    public Result onMessage(MoMsg msg) throws RemoteException, MOSException {
        return onMsgResult;
    }

    @Override
    public void createNeDomain(NeIdentity identity, int transId) throws RemoteException, MOSException {

    }

    @Override
    public void deleteNeDomain(String dn, int transId) throws RemoteException, MOSException {

    }

    @Override
    public void updateNeIP(String dn, String ip, int transId) throws RemoteException, MOSException {

    }

    @Override
    public void updateModel(String dn, String type, String version) throws RemoteException {

    }


    @Override
    public FindResult broadcast(MoFindMsg msg) throws RemoteException {
        return null;
    }

    @Override
    public void syncConfig(MoActionMsg action) throws RemoteException {

    }

    @Override
    public void switchModelReq(MoActionMsg msg) throws RemoteException, MOSException
    {

    }

    @Override
    public void switchModelAck(MoActionMsg msg) throws RemoteException, MOSException
    {

    }

    @Override
    public String identity() throws RemoteException
    {
        return null;
    }
}
