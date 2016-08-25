package com.zte.mos.msg.impl.config;

import com.zte.mos.annotation.PreLoad;
import com.zte.mos.msg.framework.session.ISessionConfigBuilder;
import com.zte.mos.msg.framework.session.SessionConfigFactory;
import com.zte.mos.msg.impl.rpc.RpcUserConfiguration;

import java.util.HashMap;

/**
 * Created by luoqingkai on 15-6-8.
 */
@PreLoad
public class RpcSessionConfigBuilder implements ISessionConfigBuilder {

    static {
        SessionConfigFactory.register(new RpcSessionConfigBuilder());
    }

    public RpcSessionConfigBuilder(){

    }

    @Override
    public String name() {
        return "RPC";
    }

    @Override
    public RpcUserConfiguration build(HashMap<String, String> data) {
        boolean isSecurity = Boolean.valueOf(data.get("isSecurity"));
        return new RpcUserConfiguration(isSecurity);
    }

    public RpcUserConfiguration buildFromString(HashMap<String, String> data){
        boolean isSecurity = Boolean.valueOf(data.get("isSecurity"));
        return new RpcUserConfiguration(isSecurity);
    }
}
