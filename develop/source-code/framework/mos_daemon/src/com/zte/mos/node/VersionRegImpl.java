package com.zte.mos.node;

import com.zte.mos.exception.MOSException;
import com.zte.mos.service.VersionReg;
import com.zte.scope.ems.EmsDomain;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.UUID;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by olinchy on 10/13/14 for MO_JAVA.
 */
public class VersionRegImpl extends UnicastRemoteObject implements VersionReg
{
    private EmsDomain ems;

    public VersionRegImpl(EmsDomain ems) throws RemoteException
    {
        this.ems = ems;
    }


    @Override
    public String register(String version, String url) throws RemoteException {
        String id = null;
        try{
            id = UUID.randomUUID().toString();
            ems.createBundle(id, url);
        }catch (MOSException e){
            throw new RemoteException(e.getMessage());
        }

        logger(VersionRegImpl.class).info(id + " allocated, version " + version + " regist on " + url);
        return id;
    }

    @Override
    public String update(String version, String url, String id) throws RemoteException {
        try {
            ems.updateBundle(id, url);
            logger(VersionRegImpl.class).info(id + " updated, V=" + version + ", URL=" + url);
            return id;
        } catch (MOSException e) {
            throw new RemoteException(e.getMessage());
        }
    }

    @Override public String get(String version) throws RemoteException
    {
        return "";
    }

}
