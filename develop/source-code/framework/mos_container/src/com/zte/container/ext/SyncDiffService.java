package com.zte.container.ext;

import com.zte.container.ext.diff.DiffPollingFutureProcessor;
import com.zte.container.ext.diff.NeReportListener;
import com.zte.container.ext.diff.NeTimerTask;
import com.zte.mos.container.RealBundleDomain;
import com.zte.mos.msg.framework.svr.RpcSvrRegister;
import com.zte.mos.service.ISyncDiffService;

import java.util.Timer;


class SyncDiffService implements ISyncDiffService {
    private Timer timer = new Timer("Timer-"+SyncDiffService.class.getSimpleName());
    private final RealBundleDomain domain;

    public SyncDiffService(RealBundleDomain domain) {
        this.domain = domain;
    }

    @Override
    public void start()
    {
        timer.schedule(new NeTimerTask(domain), 100, 10 * 1000);
        DiffPollingFutureProcessor.getInstance();
        RpcSvrRegister.register(new NeReportListener());
    }

    @Override
    public void stop() {
        timer.cancel();
    }
}
