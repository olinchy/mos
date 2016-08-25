package com.zte.container.ext;


import com.zte.mos.container.RealBundleDomain;
import com.zte.mos.service.*;
import com.zte.mos.storage.MapDbService;
import com.zte.mos.domain.ModelService;

import java.rmi.RemoteException;

public class ExtServiceProvider
{
    private IRegService     regService;
    private IOperationService   operService;
    private ISyncService      syncService;
    private ISyncDiffService  syncDiffService;
    private ISouthboundService southService;
    private IModelService modelService;

    private static final ExtServiceProvider instance = new ExtServiceProvider();

    public static ExtServiceProvider getInstance()
    {
        return instance;
    }

    private ExtServiceProvider()
    {
        MapDbService.initService();
        modelService = new ModelService();
        southService = new SouthboundService();
        syncService = new SyncService();
        regService = new BundleRegService();
    }

    public void init(RealBundleDomain domain) throws RemoteException {
        operService = new OperationService(domain);
        syncDiffService = new SyncDiffService(domain);
    }

    public IRegService getRegService() {
        return regService;
    }

    public IOperationService getOperService() {
        return operService;
    }

    public ISyncService getSyncService() {
        return syncService;
    }

    public ISyncDiffService getSyncDiffService() {
        return syncDiffService;
    }

    public ISouthboundService getSouthService() {
        return southService;
    }

    public void setRegService(IRegService regService) {
        this.regService = regService;
    }

    public IModelService getModelService(){
        return modelService;
    }
}
