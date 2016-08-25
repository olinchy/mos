package com.zte.mos.msg.impl.snmp;

import com.zte.mos.msg.impl.exception.ConnectException;
import com.zte.ums.uep.protocol.snmp.beans.DataException;
import com.zte.ums.uep.protocol.snmp.beans.SnmpRequestServer;
import com.zte.ums.uep.protocol.snmp.beans.SnmpTarget;
import com.zte.ums.uep.protocol.snmp.snmp2.SnmpVar;
import com.zte.ums.uep.protocol.snmp.snmp2.SnmpVarBind;

import java.util.HashMap;

/**
 * Created by zhangbin10086509 on 15-6-6.
 */
public interface SnmpMsgService {
    int syncSET(SnmpTarget t, String[] oids, SnmpVar[] values);

    int asyncSET(SnmpRequestServer t, String[] oids, SnmpVar[] values) throws DataException;

    HashMap<String, SnmpVar> syncGET(SnmpTarget t, String[] oids) throws DataException, ConnectException;

    void connect(SnmpTarget t) throws ConnectException;

    SnmpVarBind[][] syncGetBulk(SnmpTarget t, String[] oids, SnmpVar[] values) throws DataException, ConnectException;

    void asynConnect(SnmpRequestServer t) throws ConnectException;
}
