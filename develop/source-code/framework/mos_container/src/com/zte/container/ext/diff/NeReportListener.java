package com.zte.container.ext.diff;

import com.fasterxml.jackson.databind.JsonNode;
import com.zte.mos.domain.TargetAddress;
import com.zte.mos.msg.framework.svr.RpcListener;


public class NeReportListener implements RpcListener {
    @Override
    public void process(TargetAddress address, JsonNode moOperList) {
        V11SyncDiffThread.addTask(new V11SyncDiffTask(address, moOperList));
    }
}
