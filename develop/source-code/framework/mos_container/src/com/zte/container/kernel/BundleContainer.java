package com.zte.container.kernel;

import com.zte.container.ext.ExtServiceProvider;
import com.zte.mos.container.RealBundleDomain;
import com.zte.mos.service.impl.VmServices;
import com.zte.mos.util.basic.Logger;

import static com.zte.domain.transaction.TransactionMonitorService.startTransactionMonitorService;
import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by love on 16-1-9.
 * BundleContainer provides resources for Bundle.
 */
public class BundleContainer {

    private static final Logger log = logger(BundleContainer.class);

    private final String selfURL;

    private ExtServiceProvider svHolder;

    public BundleContainer(String selfURL) {
        this.selfURL = selfURL;
        svHolder = ExtServiceProvider.getInstance();
    }

    public ExtServiceProvider getServicesHolder(){
        return  svHolder;
    }

    private RealBundleDomain startDomain(){
        return RealBundleDomain.getInstance();
    }

    public void start() throws Exception {
        VmServices.getInstance();
        //svHolder = new ExtServiceHolder();
        svHolder.getModelService().start();
        svHolder.getSouthService().start();
        svHolder.getSyncService().start();//timer等

        RealBundleDomain domain = startDomain();
        svHolder.init(domain);
        svHolder.getOperService().start();
        svHolder.getSyncDiffService().start();
        svHolder.getRegService().start();
        svHolder.getRegService().register(selfURL, domain);
        startTransactionMonitorService();
    }

    public void stop(){

    }
}
