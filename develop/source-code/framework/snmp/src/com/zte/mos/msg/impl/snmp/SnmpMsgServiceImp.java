package com.zte.mos.msg.impl.snmp;

import com.zte.mos.msg.impl.exception.ConnectException;
import com.zte.mos.util.basic.Logger;
import com.zte.ums.uep.protocol.snmp.beans.DataException;
import com.zte.ums.uep.protocol.snmp.beans.SnmpRequestServer;
import com.zte.ums.uep.protocol.snmp.beans.SnmpTarget;
import com.zte.ums.uep.protocol.snmp.snmp2.SnmpException;
import com.zte.ums.uep.protocol.snmp.snmp2.SnmpVar;
import com.zte.ums.uep.protocol.snmp.snmp2.SnmpVarBind;

import java.util.HashMap;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by zhangbin10086509 on 15-6-6.
 */
public class SnmpMsgServiceImp implements SnmpMsgService{
    private static Logger log = logger(SnmpMsgServiceImp.class);

    @Override
    public int asyncSET(SnmpRequestServer t, String[] oids, SnmpVar[] values) throws DataException {
        t.setObjectIDList(oids);
        return t.sendSetRequestVariables(values);
    }

    @Override
    public HashMap<String, SnmpVar> syncGET(SnmpTarget t, String[] oids) throws DataException, ConnectException {

        t.setObjectIDList(oids);
        SnmpVarBind[] binds = t.snmpGetVariableBindings();
        if(null == binds){
            throw new ConnectException(t.getErrorCode());
        }
        HashMap<String, SnmpVar> response = getResponseFromBind(binds);
        return response;
    }



    @Override
    public int syncSET(SnmpTarget target, String[] oids, SnmpVar[] vars) {
        target.setObjectIDList(oids);
        //retry 2 times
        for(int i=0; i<2; i++){
            try {
                target.snmpSetVariables(vars);
            } catch (DataException e) {
                log.warn("syn set error", e);
            }
            if(target.getErrorCode() == 0 || target.getErrorCode() > 64){
                break;
            }
            else{
                log.info("syn set errorcode=" + target.getErrorCode());
            }
        }
        return target.getErrorCode();
    }

    private HashMap<String, SnmpVar> getResponseFromBind(SnmpVarBind[] binds){
        HashMap<String, SnmpVar> response = new HashMap<String, SnmpVar>();
        for(SnmpVarBind b : binds){
            response.put(b.getObjectID().toString(), b.getVariable());
        }
        return response;
    }

    @Override
    public void asynConnect(SnmpRequestServer t) throws ConnectException {
    }

    @Override
    public void connect(SnmpTarget t) throws ConnectException {
        //retry 2 times
        for(int i=0; i<2; i++){
            try {
                printSnmpInfo(t);
                t.managing_v3_tables();
                return;
            } catch (SnmpException e) {
                log.warn(t.getTargetHost() + " managing_v3_table error,code=" + t.getErrorCode(), e);
            }
        }
        throw new ConnectException((t.getErrorCode() == -1) ? 951095 : t.getErrorCode());
    }

    @Override
    public SnmpVarBind[][] syncGetBulk(SnmpTarget t, String[] oids, SnmpVar[] values) throws DataException, ConnectException {
        t.setObjectIDList(oids);
        SnmpVarBind[][] binds = t.snmpGetBulkVariableBindings();

        if(null == binds){
            log.warn("connect to " + t.getTargetHost() + " failed!");
            throw new ConnectException(t.getErrorCode());
        }
        return binds;

    }

    private void printSnmpInfo(SnmpTarget t){
        log.info(String.format("snmpver=%s,ip=%s,port=%s,comm=%s,name=%s,level=%s,authprotol=%s,priv=%s,auth=%s",
                t.getSnmpVersion(), t.getTargetHost(), t.getTargetPort(),t.getCommunity(),
                t.getPrincipal(), t.getSecurityLevel(),t.getAuthProtocol(),
                t.getPrivPassword(), t.getAuthPassword()));
    }
}
