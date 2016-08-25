package com.zte.mos.msg.framework.protocol.configuration;

import com.zte.mos.domain.DN;
import com.zte.mos.exception.MOSException;
import com.zte.mos.exception.NonvalidKeywordException;
import com.zte.mos.message.Mo;

import java.util.HashMap;

/**
 * Created by dongyue on 16-7-21.
 */
public class RpcCfgGetter extends ConfigurationFileGetter {
    protected HashMap<String, String> convert2CfgValue(Mo mo) throws MOSException {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("isSecurity", String.valueOf(isV3(mo)));
        return map;
    }

    protected DN getFullDn(String dn) throws NonvalidKeywordException {
        DN neDN = new DN(dn);
        return neDN.to("Ne").append("/Communication/snmpV3");
    }

    private boolean isV3(Mo mo) throws MOSException {
        return mo.get("version").equals("v3");
    }
}
