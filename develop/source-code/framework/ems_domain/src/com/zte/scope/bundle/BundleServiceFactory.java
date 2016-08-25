package com.zte.scope.bundle;

import com.zte.mos.service.BundleService;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Created by luoqingkai on 15-8-12.
 */
public class BundleServiceFactory
{
    private static BundleService service;

    public static BundleService getService(String address) throws Exception{
        if (service == null){
            return (BundleService) Naming.lookup(address);
        }
        return service;
    }

    public static void setService(BundleService userService){
        service = userService;
    }
}
