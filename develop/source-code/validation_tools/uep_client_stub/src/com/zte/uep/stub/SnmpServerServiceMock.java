package com.zte.uep.stub;

import com.zte.app.common.uep.client.service.SnmpServerService;
import com.zte.mos.util.basic.Logger;
import com.zte.ums.uep.protocol.snmp.beans.*;
import com.zte.ums.uep.protocol.snmp.snmp2.ProtocolOptions;
import com.zte.ums.uep.protocol.snmp.snmp2.SnmpVarBind;

import java.applet.Applet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pavel on 16-1-14.
 */
public class SnmpServerServiceMock implements SnmpServerService
{
    private Map<String,SnmpVarBind[]> varbindMap = new HashMap<String, SnmpVarBind[]>();
    private Map<String,Integer> errorCodeMap = new HashMap<String, Integer>();
    private String currentOids = null;

//    private Map<Integer,ResultEvent> eventMap = new HashMap<Integer, ResultEvent>();
//    private static Integer requestID = 0;
    private ResultEvent event = null;


    @Override
    public SnmpTarget newSnmpTarget()
    {
        return new SnmpTargetMock(this);
    }

    @Override
    public SnmpTarget newSnmpTarget(String localIpAddress)
    {
        return new SnmpTargetMock(this,localIpAddress);
    }

    @Override
    public SnmpTarget newSnmpTarget(int i, ProtocolOptions protocoloptions)
    {
        return new SnmpTargetMock(this,i,protocoloptions);
    }

    @Override
    public SnmpTarget newSnmpTarget(int port, String session)
    {
        return new SnmpTargetMock(this,port,session);
    }

    @Override
    public SnmpTarget newSnmpTarget(Applet applet)
    {
        return new SnmpTargetMock(this,applet);
    }

    @Override
    public SnmpTarget newSnmpTarget(Applet applet, int port, String session)
    {
        return new SnmpTargetMock(this,applet,port,session);
    }

    @Override
    public SnmpRequestServer newSnmpAsyncTarget()
    {
        return new SnmpAsyncTargetMock(this);
    }

    @Override
    public SnmpRequestServer newSnmpAsyncTarget(String localIpAddress)
    {
        return new SnmpAsyncTargetMock(this,localIpAddress);
    }

    @Override
    public SnmpRequestServer newSnmpAsyncTarget(int i, ProtocolOptions protocoloptions)
    {
        return new SnmpAsyncTargetMock(this,i,protocoloptions);
    }

    @Override
    public SnmpRequestServer newSnmpAsyncTarget(int localPort, String sessionName)
    {
        return new SnmpAsyncTargetMock(this,localPort,sessionName);
    }

    @Override
    public SnmpRequestServer newSnmpAsyncTarget(Applet applet)
    {
        return new SnmpAsyncTargetMock(this,applet);
    }

    @Override
    public SnmpRequestServer newSnmpAsyncTarget(Applet applet, int i, String string)
    {
        return new SnmpAsyncTargetMock(this,applet,i,string);
    }

    private String toString(String[] oids){
        StringBuilder sb = new StringBuilder();
        for (String oid:oids){
            sb.append(oid);
        }
        return sb.toString();
    }

    public void setResult(String[] oids, SnmpVarBind[] result, int errorCode)
    {
        varbindMap.put(toString(oids),result);
        errorCodeMap.put(toString(oids), errorCode);
    }

    public SnmpVarBind[] snmpGetVariableBindings() {
        return varbindMap.get(currentOids);
    }

    public int getErrorCode() {
        return errorCodeMap.containsKey(currentOids)?errorCodeMap.get(currentOids):0;
    }

    public void setObjectIDList(String[] strings) {
        this.currentOids = toString(strings);
    }

    public void setResultEvent(ResultEvent event){
        this.event = event;
//        this.eventMap.put(requestID, event);
        Logger.logger(SnmpServerServiceMock.class).info("setResultEvent event="+event.getSource());
//        this.event = event;
    }

    public ResultEvent getResultEvent()
    {
        return event;
    }

    public void clear(){
        varbindMap.clear();
        errorCodeMap.clear();
        currentOids=null;
    }
}
