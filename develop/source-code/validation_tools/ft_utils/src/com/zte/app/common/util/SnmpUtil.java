package com.zte.app.common.util;

import com.zte.ums.uep.protocol.snmp.snmp2.*;

/**
 * Created by pavel on 15-7-2.
 */
public class SnmpUtil {

    public static SnmpPDU createSnmpPDU(String ip,SnmpVarBind... varBinds){
        SnmpPDU pdu = new SnmpPDU();
        for (SnmpVarBind varBind:varBinds){
            pdu.addVariableBinding(varBind);
        }
        pdu.setRemoteHost(ip);
        return pdu;
    }

    public static SnmpVarBind createVarBind(String oid,String value){
        SnmpVarBind vb = null;
        if (value == null){
            value = "NULL";
        }
        try {
            vb = new SnmpVarBind(new SnmpOID(oid), SnmpVar.createVariable(value, (byte) 4));
        } catch (SnmpException e) {
            e.printStackTrace();
        }
        return vb;
    }



}
