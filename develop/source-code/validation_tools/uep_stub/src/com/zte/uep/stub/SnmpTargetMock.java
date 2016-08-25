package com.zte.uep.stub;

import com.zte.ums.uep.protocol.snmp.beans.SnmpServer;
import com.zte.ums.uep.protocol.snmp.beans.SnmpTarget;
import com.zte.ums.uep.protocol.snmp.snmp2.*;

import java.applet.Applet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pavel on 16-1-14.
 */
public class SnmpTargetMock extends SnmpTarget
{
    private SnmpServerServiceMock snmpServerServiceMock = null;
//    private int port = 161;
    private SnmpEngineTable engineTable = null;


    public SnmpTargetMock(SnmpServerServiceMock snmpServerServiceMock)
    {
        this.snmpServerServiceMock = snmpServerServiceMock;
    }

    public SnmpTargetMock(SnmpServerServiceMock snmpServerServiceMock, String localIpaddress)
    {
//        super(localIpaddress);
        this.snmpServerServiceMock = snmpServerServiceMock;
    }

    public SnmpTargetMock(SnmpServerServiceMock snmpServerServiceMock, int p, ProtocolOptions protocoloptions)
    {
//        super(p, protocoloptions);
        this.snmpServerServiceMock = snmpServerServiceMock;
    }

    public SnmpTargetMock(SnmpServerServiceMock snmpServerServiceMock, int aPort, String string)
    {
//        super(aPort, string);
        this.snmpServerServiceMock = snmpServerServiceMock;
    }

    public SnmpTargetMock(SnmpServerServiceMock snmpServerServiceMock, Applet applet)
    {
//        super(applet);
        this.snmpServerServiceMock = snmpServerServiceMock;
    }

    public SnmpTargetMock(SnmpServerServiceMock snmpServerServiceMock, Applet applet, int aPort, String string)
    {
//        super(applet, aPort, string);
        this.snmpServerServiceMock = snmpServerServiceMock;
    }

    public SnmpVarBind[] snmpGetVariableBindings()
    {
        return snmpServerServiceMock.snmpGetVariableBindings();
    }

    public int getErrorCode()
    {
        return snmpServerServiceMock.getErrorCode();
    }

    public void setObjectIDList(String[] strings)
    {
        snmpServerServiceMock.setObjectIDList(strings);
    }

    public void managing_v3_tables() throws SnmpException {this.engineTable = new SnmpEngineTableMock();}

    public SnmpEngineTable getSnmpEngineTable() {return engineTable;}

//    public int getTargetPort(){return port;}
}
