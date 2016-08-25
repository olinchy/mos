package com.zte.mos.msg.impl.snmp;

import com.zte.mos.message.Mo;
import com.zte.mos.msg.framework.operation.GetResponse;
import com.zte.ums.uep.protocol.snmp.snmp2.SnmpVar;

import java.util.HashMap;

/**
 * Created by zhangwei on 15-8-14.
 */
public class SnmpGetResponse extends GetResponse
{
    private HashMap<String, SnmpVar> data;


    public SnmpGetResponse(int result, HashMap<String, SnmpVar> data) {
        super(result, null);
        this.data = data;
    }

    public HashMap<String, SnmpVar> getData() {
        return data;
    }

    public void setMo(Mo mo) {
        this.mo = mo;
    }
}
