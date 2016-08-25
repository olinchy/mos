package com.zte.mos.msg.impl.snmp;

import com.zte.mos.domain.TargetAddress;
import com.zte.mos.msg.impl.exception.ConnectException;
import com.zte.mos.util.basic.Logger;
import com.zte.ums.uep.protocol.snmp.beans.SnmpRequestServer;
import com.zte.ums.uep.protocol.snmp.beans.SnmpServer;
import com.zte.ums.uep.protocol.snmp.beans.SnmpTarget;
import com.zte.ums.uep.protocol.snmp.snmp2.Snmp3Message;
import com.zte.ums.uep.protocol.snmp.snmp2.SnmpException;
import com.zte.ums.uep.protocol.snmp.snmp2.usm.USMUserEntry;

import java.util.HashMap;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by luoqingkai on 15-5-20.
 */
public class SnmpV3Configuration extends SnmpConfiguration {
    private static Logger log = logger(SnmpV3Configuration.class);
    private byte securityLevel = Snmp3Message.AUTH_PRIV;
    private byte authProtocol = USMUserEntry.MD5_AUTH;
    private String principal;
    private String authPassword;
    private String privPassword;
    private int timeout;
    private int retry;

    public SnmpV3Configuration(HashMap<String, String> data) {
        super(Integer.valueOf(data.get("port")));
        this.securityLevel = Byte.valueOf(data.get("security_level"));
        this.authProtocol = Byte.valueOf(data.get("auth_protocol"));
        this.principal = String.valueOf(data.get("principal"));
        this.authPassword = data.get("authPassword");
        this.privPassword = data.get("privPassword");
        this.timeout = Integer.valueOf(data.get("retry_timeout"));
        this.retry = Integer.valueOf(data.get("retry_times"));
    }

    @Override
    public SnmpTarget buildSyncTarget(TargetAddress address) {
        SnmpTarget syncTarget = new SnmpTarget();
        syncTarget.setSnmpVersion(SnmpTarget.VERSION3);
        syncTarget.setTargetHost(address.getIpAddress());
        syncTarget.setTargetPort(this.port);
        syncTarget.setSecurityLevel(Snmp3Message.AUTH_PRIV);
        syncTarget.setAuthProtocol(this.authProtocol);
        syncTarget.setAuthPassword(this.authPassword);
        syncTarget.setPrincipal(this.principal);
        syncTarget.setPrivPassword(this.privPassword);
        syncTarget.setTimeout(timeout);
        syncTarget.setRetries(retry);
        try {
            syncTarget.manage_v3_tables();
            return syncTarget;
        }catch (Exception ex){
            return null;
        }
    }

    @Override
    public SnmpRequestServer buildAsyncTarget(TargetAddress address) {
        SnmpRequestServer asyncTarget = new SnmpRequestServer();
        asyncTarget.setSnmpVersion(SnmpTarget.VERSION3);
        asyncTarget.setTargetHost(address.getIpAddress());
        asyncTarget.setTargetPort(this.port);
        asyncTarget.setSecurityLevel(securityLevel);
        asyncTarget.setAuthProtocol(this.authProtocol);
        asyncTarget.setAuthPassword(this.authPassword);
        asyncTarget.setPrincipal(this.principal);
        asyncTarget.setPrivPassword(this.privPassword);
        asyncTarget.setSendTimeoutEvents(true);
        asyncTarget.setTimeout(timeout);
        asyncTarget.setRetries(retry);
        try {
            asyncTarget.manage_v3_tables();
            return asyncTarget;
        }catch (Exception ex){
            return null;
        }
    }

    @Override
    public void connect(SnmpServer target) {
        try {
            target.managing_v3_tables();
        } catch (SnmpException e) {
            // do nothing
        }
    }

    @Override
    public HashMap<String, String> toMap() {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("port", String.valueOf(this.port));
        map.put("security_level", String.valueOf(securityLevel));
        map.put("auth_protocol", String.valueOf(authProtocol));
        map.put("principal", String.valueOf(this.principal));
        map.put("authPassword", String.valueOf(this.authPassword));
        map.put("privPassword", String.valueOf(this.privPassword));
        map.put("retry_timeout", String.valueOf(this.timeout));
        map.put("retry_times", String.valueOf(this.retry));
        map.put("version", "v3");
        return map;
    }
}
