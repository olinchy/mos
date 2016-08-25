package com.zte.mos.logging_service;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by olinchy on 16-7-20.
 */
public interface LoggingService extends Remote
{
    void log(Log log) throws RemoteException;
}
