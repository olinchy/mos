package com.zte.mos.msg.impl.snmp;

import com.zte.mos.domain.TargetAddress;
import com.zte.mos.msg.framework.session.ISessionConfiguration;
import com.zte.ums.uep.protocol.snmp.beans.SnmpRequestServer;
import com.zte.ums.uep.protocol.snmp.beans.SnmpServer;
import com.zte.ums.uep.protocol.snmp.beans.SnmpTarget;

/**
 * Created by luoqingkai on 15-5-20.
 */
public abstract class SnmpConfiguration implements ISessionConfiguration {

    protected int port = 161;

    public SnmpConfiguration(int port) {
        this.port = port;
    }

    public abstract SnmpTarget buildSyncTarget(TargetAddress address);
    public abstract SnmpRequestServer buildAsyncTarget(TargetAddress address);
    public abstract void connect(SnmpServer server);

    @Override
    public String protocol()
    {
        return "SNMP";
    }
}
