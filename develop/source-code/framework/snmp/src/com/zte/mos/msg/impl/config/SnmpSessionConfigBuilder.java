package com.zte.mos.msg.impl.config;

import com.zte.mos.annotation.PreLoad;
import com.zte.mos.msg.framework.session.ISessionConfigBuilder;
import com.zte.mos.msg.framework.session.ISessionConfiguration;
import com.zte.mos.msg.framework.session.SessionConfigFactory;
import com.zte.mos.msg.impl.snmp.SnmpV2Configuration;
import com.zte.mos.msg.impl.snmp.SnmpV3Configuration;

import java.util.HashMap;


@PreLoad
public class SnmpSessionConfigBuilder implements ISessionConfigBuilder {
    static {
        SessionConfigFactory.register(new SnmpSessionConfigBuilder());
    }

    public SnmpSessionConfigBuilder(){

    }

    @Override
    public String name() {
        return "SNMP";
    }

    @Override
    public ISessionConfiguration build(HashMap<String, String> data) {
        if(null == data.get("readcommunity")){
            return new SnmpV3Configuration(data);
        }
        return new SnmpV2Configuration(data);
    }
}
