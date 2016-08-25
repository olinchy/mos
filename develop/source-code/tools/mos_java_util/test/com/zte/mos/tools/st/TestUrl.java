package com.zte.mos.tools.st;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Created by olinchy on 4/15/15 for ems-mos.
 */
public class TestUrl
{
    public static void main(String[] args)
            throws RemoteException, NotBoundException, MalformedURLException
    {
        Object obj = Naming.lookup("//10.86.58.3:8000/client/APP_CES_CLIENT");
        assert obj != null;
    }
}
