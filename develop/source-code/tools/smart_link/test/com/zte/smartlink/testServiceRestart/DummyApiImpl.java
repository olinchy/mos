package com.zte.smartlink.testServiceRestart;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;

/**
 * Created by olinchy on 16-1-13.
 */
public class DummyApiImpl extends UnicastRemoteObject implements DummyApi
{
    protected DummyApiImpl() throws RemoteException
    {
    }

    public void print() throws RemoteException
    {
        System.out.println("somebody is poking me!! " + new Date());
    }
}
