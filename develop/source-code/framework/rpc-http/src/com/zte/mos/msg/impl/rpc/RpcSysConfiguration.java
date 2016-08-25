package com.zte.mos.msg.impl.rpc;

import com.zte.mos.msg.framework.session.ISessionConfiguration;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by luoqingkai on 15-5-15.
 */
public class RpcSysConfiguration implements ISessionConfiguration {

    // role
    // URL
    // session id
    private UUID localID;
    private String localURL;

    public RpcSysConfiguration() {
        this.localID = UUID.randomUUID();
        localURL = RpcLocalSvrConfigs.getLocalURL();
    }

    public String getLocalRole(){
        return "EMS";
    }

    public UUID getLocalID() {
        return localID;
    }

    public String getLocalURL() {
        return localURL;
    }

    @Override
    public String protocol()
    {
        return "RPC";
    }

    @Override
    public HashMap<String, String> toMap() {
        return null;
    }
}
