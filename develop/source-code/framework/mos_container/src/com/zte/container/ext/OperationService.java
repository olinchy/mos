package com.zte.container.ext;

import com.zte.mos.container.BundleObject;
import com.zte.mos.container.RealBundleDomain;
import com.zte.mos.service.IOperationService;
import com.zte.mos.util.basic.Logger;

import java.rmi.Remote;
import java.rmi.RemoteException;

import static com.zte.mos.util.basic.Logger.logger;


public class OperationService implements IOperationService
{
    private final Logger log = logger(OperationService.class);

    private final BundleObject service;

    public OperationService(RealBundleDomain bundleDomain) throws RemoteException {
        service = new BundleObject(bundleDomain);
    }

    @Override
    public void start() {
        log.info("operation service started.");
    }

    @Override
    public void stop() {
        log.info("operation service stopped.");
    }

    @Override
    public Remote getRemote() {
        return service;
    }

}
