package com.zte.mos.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by olinchy on 10/13/14 for MO_JAVA.
 */
public interface VersionReg extends Remote
{
    String register(String version, String url) throws RemoteException;

    String update(String version, String url, String id) throws RemoteException;

    String get(String version) throws RemoteException;
}
