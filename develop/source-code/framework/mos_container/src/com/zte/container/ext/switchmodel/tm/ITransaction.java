package com.zte.container.ext.switchmodel.tm;

import com.zte.mos.exception.MOSException;

import java.rmi.RemoteException;

/**
 * Created by ccy on 5/21/16.
 */
public interface ITransaction
{
    void start() throws MOSException, RemoteException;
    void commit() throws MOSException, RemoteException;
    void rollback() throws MOSException, RemoteException;
    void onTimeOut();
    boolean isTimeOut();
}
