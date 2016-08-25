package com.zte.mos.msg.framework.protocol.configuration;

import com.zte.mos.domain.DN;
import com.zte.mos.exception.MOSException;
import com.zte.mos.exception.NonvalidKeywordException;
import com.zte.mos.message.Mo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dongyue on 16-7-21.
 */
public class RestfulCfgGetter extends ConfigurationFileGetter {


    protected DN getFullDn(String dn) throws NonvalidKeywordException {
        DN neDN = new DN(dn);
        return neDN.to("Ne").append("/Communication/snmpV3");////// TODO: 16-7-14 ensure the dn to get the Net msg
    }

    @Override
    protected HashMap<String, String> convert2CfgValue(Mo mo) throws MOSException {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("emsIp", mo.get("emsIp").toString());
        map.put("net", mo.get("net").toString());
        map.put("guid", mo.get("guid").toString());
        return map;
    }

    private HashMap<String, String> ObjectMap2StringMap(HashMap<String, Object> moMap){
        HashMap<String, String> strMap = new HashMap<String, String>();
        for (Map.Entry<String, Object> entry : moMap.entrySet()){
            strMap.put(entry.getKey(), entry.getValue().toString());
        }
        return strMap;
    }
}
