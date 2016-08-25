package com.zte.smartlink.testServiceRestart;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by olinchy on 16-1-13.
 */
public interface DummyApi extends Remote
{
    void print() throws RemoteException;
}
