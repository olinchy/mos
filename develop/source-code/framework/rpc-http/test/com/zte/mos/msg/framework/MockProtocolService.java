package com.zte.mos.msg.framework;

import com.zte.mos.msg.SupportedProtocol;
import com.zte.mos.msg.framework.protocol.ProtocolService;

import java.util.HashMap;

/**
 * Created by dongyue on 16-7-21.
 */
public class MockProtocolService extends ProtocolService {
    @Override
    protected HashMap<String, String> getConfigData(SupportedProtocol protocol) {
        switch (protocol){
            case SNMP:
                return getSnmpConfigData();
            case RESTFUL:
                return getRestfulConfigData();
            default:
                return getRpcfulConfigData();
        }

    }

    protected HashMap<String, String> getRpcfulConfigData() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("isSecurity", "false");
        return map;
    }

    protected HashMap<String, String> getRestfulConfigData() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("emsIp", "127.0.0.1");
        map.put("net", "net-name");
        map.put("guid", "e000001");
        return map;
    }

    protected HashMap<String, String> getSnmpConfigData() {
        HashMap<String, String> para = new HashMap<String, String>();
        para.put("port", "161");
        para.put("security_level", "3");
        para.put("auth_protocol", "21");
        para.put("principal", "zte");
        para.put("authPassword", "ztesnmp2014");
        para.put("encryptPassword", "ztesnmp2014");
        para.put("retry_timeout", "5");
        para.put("retry_times", "3");
        return para;
    }
}
