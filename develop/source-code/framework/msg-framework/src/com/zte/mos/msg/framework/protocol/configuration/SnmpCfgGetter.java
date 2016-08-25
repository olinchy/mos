package com.zte.mos.msg.framework.protocol.configuration;

import com.zte.mos.domain.DN;
import com.zte.mos.exception.MOSException;
import com.zte.mos.exception.NonvalidKeywordException;
import com.zte.mos.message.Mo;

import java.util.HashMap;

/**
 * Created by dongyue on 16-7-9.
 */
public class SnmpCfgGetter extends ConfigurationFileGetter {
    protected HashMap<String, String> convert2CfgValue(Mo mo) throws MOSException {
        if(isV3(mo)) {
            return convertV3(mo);
        } else {
            return convertV2(mo);
        }
    }

    protected DN getFullDn(String dn) throws NonvalidKeywordException {
        DN neDN = new DN(dn);
        return neDN.to("Ne").append("/Communication/snmpV3");
    }

    private HashMap<String, String> convertV3(Mo v3) throws MOSException {
        HashMap<String, String> para = new HashMap<String, String>();
        para.put("port", "161");
        para.put("security_level", "3");
        para.put("auth_protocol", "21");
        para.put("principal", "zte");
        para.put("authPassword", String.valueOf(v3.get("authPassword")));
        para.put("privPassword", String.valueOf(v3.get("privPassword")));
        para.put("retry_timeout", "5");
        para.put("retry_times", "3");
        return para;
    }

    private HashMap<String, String> convertV2(Mo v2) throws MOSException {
        HashMap<String, String> para = new HashMap<String, String>();
        para.put("port", "161");
        para.put("readcommunity", String.valueOf(v2.get("readcommunity")));
        para.put("writecommunity", String.valueOf(v2.get("writecommunity")));
        para.put("trapcommunity", String.valueOf(v2.get("trapcommunity")));
        para.put("retry_timeout", "5");
        para.put("retry_times", "3");
        return para;
    }
    private boolean isV3(Mo mo) throws MOSException {
        return mo.get("version").equals("v3");
    }
}
