package com.zte.container.ext;

import com.zte.container.ext.total.ReverseCfgListener;
import com.zte.mos.msg.framework.svr.RpcSvrRegister;
import com.zte.mos.service.ISyncService;


class SyncService implements ISyncService {
    @Override
    public void start() {
        RpcSvrRegister.register(new ReverseCfgListener());
    }

    @Override
    public void stop() {

    }
}
