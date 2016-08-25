package com.zte.mos.msg.impl.snmp;

import com.zte.mos.domain.TargetAddress;
import com.zte.ums.uep.protocol.snmp.beans.SnmpRequestServer;
import com.zte.ums.uep.protocol.snmp.beans.SnmpServer;
import com.zte.ums.uep.protocol.snmp.beans.SnmpTarget;

import java.util.HashMap;

/**
 * Created by luoqingkai on 15-5-21.
 */
public class SnmpV2Configuration extends SnmpConfiguration {

    private String readCommunity;
    private String writeCommunity;
    private String trapCommunity;
    private int timeout;
    private int retry;

    public SnmpV2Configuration(HashMap<String, String> data) {
        super(Integer.valueOf(data.get("port")));
        this.readCommunity = data.get("readcommunity");
        this.writeCommunity = data.get("writecommunity");
        this.trapCommunity = data.get("trapcommunity");
        this.timeout = Integer.valueOf(data.get("retry_timeout"));
        this.retry = Integer.valueOf(data.get("retry_times"));
    }

    @Override
    public SnmpTarget buildSyncTarget(TargetAddress address) {
        SnmpTarget syncTarget = new SnmpTarget();
        syncTarget.setSnmpVersion(SnmpTarget.VERSION2C);
        syncTarget.setTargetHost(address.getIpAddress());
        syncTarget.setTargetPort(this.port);
        syncTarget.setWriteCommunity(this.writeCommunity);
        syncTarget.setCommunity(this.readCommunity);
        syncTarget.setTimeout(timeout);
        syncTarget.setRetries(retry);
        return syncTarget;
    }

    @Override
    public SnmpRequestServer buildAsyncTarget(TargetAddress address) {
        SnmpRequestServer asynTarget = new SnmpRequestServer();
        asynTarget.setSnmpVersion(SnmpTarget.VERSION2C);
        asynTarget.setTargetHost(address.getIpAddress());
        asynTarget.setTargetPort(this.port);
        asynTarget.setWriteCommunity(this.writeCommunity);
        asynTarget.setCommunity(this.readCommunity);
        asynTarget.setTimeout(timeout);
        asynTarget.setRetries(retry);
        asynTarget.setSendTimeoutEvents(true);
        return asynTarget;
    }

    @Override
    public void connect(SnmpServer server) {

    }

    @Override
    public HashMap<String, String> toMap() {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("port", String.valueOf(this.port));
        map.put("readcommunity", this.readCommunity);
        map.put("writecommunity", this.writeCommunity);
        map.put("trapcommunity", this.trapCommunity);
        map.put("retry_timeout", String.valueOf(this.timeout));
        map.put("retry_times", String.valueOf(this.retry));
        map.put("version", "v2c");
        return map;
    }
}
