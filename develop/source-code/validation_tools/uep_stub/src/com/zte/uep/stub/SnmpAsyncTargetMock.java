package com.zte.uep.stub;

import com.zte.mos.util.basic.Logger;
import com.zte.ums.uep.protocol.snmp.beans.ResultEvent;
import com.zte.ums.uep.protocol.snmp.beans.ResultListener;
import com.zte.ums.uep.protocol.snmp.beans.SnmpRequestServer;
import com.zte.ums.uep.protocol.snmp.snmp2.ProtocolOptions;
import com.zte.ums.uep.protocol.snmp.snmp2.SnmpEngineEntry;
import com.zte.ums.uep.protocol.snmp.snmp2.SnmpEngineTable;
import com.zte.ums.uep.protocol.snmp.snmp2.SnmpPDU;
import com.zte.ums.uep.protocol.snmpagent.api.SnmpConstants;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;

import java.applet.Applet;
import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.createControl;

/**
 * Created by pavel on 16-1-14.
 */
public class SnmpAsyncTargetMock extends SnmpRequestServer
{
    private SnmpServerServiceMock snmpServerService = null;
    private List<ResultListener> listeners = new ArrayList<ResultListener>();
    //    private SnmpPDU snmpPDU = null;
    private SnmpEngineTable engineTableMock = null;
    private static Integer requestID = 0;

    public SnmpAsyncTargetMock(SnmpServerServiceMock snmpServerService) {
        super();
        this.snmpServerService = snmpServerService;
        IMocksControl mocksControl = createControl();
        engineTableMock = mocksControl.createMock(SnmpEngineTable.class);
        EasyMock.expect(engineTableMock.addEntry((SnmpEngineEntry)EasyMock.anyObject())).andReturn(true).anyTimes();
        mocksControl.replay();
    }

    public SnmpAsyncTargetMock(SnmpServerServiceMock snmpServerService, String localIpAddress) {
//        super(localIpAddress);
        this.snmpServerService = snmpServerService;
    }

    public SnmpAsyncTargetMock(SnmpServerServiceMock snmpServerService, int i, ProtocolOptions protocoloptions) {
//        super(i, protocoloptions);
        this.snmpServerService = snmpServerService;
    }

    public SnmpAsyncTargetMock(SnmpServerServiceMock snmpServerService, int localPort, String sessionName) {
//        super(localPort, sessionName);
        this.snmpServerService = snmpServerService;
    }

    public SnmpAsyncTargetMock(SnmpServerServiceMock snmpServerService, Applet applet) {
//        super(applet);
        this.snmpServerService = snmpServerService;
    }

    public SnmpAsyncTargetMock(SnmpServerServiceMock snmpServerService, Applet applet, int i, String string) {
//        super(applet, i, string);
        this.snmpServerService = snmpServerService;
    }

    public SnmpEngineTable getSnmpEngineTable(){
        return engineTableMock;
    }

    public void setSendTimeoutEvents(boolean flag){
    }

    public int sendGetRequest(){
        requestID++;

        new Thread(){
            public void run(){
                for(ResultListener listener:listeners){
                    listener.setResult(getResultEvent());
                }
            }
        }.start();

        return requestID;
    }

    public void addResultListener(ResultListener listener){
        listeners.add(listener);
    }

    public void setSnmpPara(String[] oids,SnmpConstants cons){
    }

    private ResultEvent getResultEvent(){
        ResultEvent event = snmpServerService.getResultEvent();
        ((SnmpPDU) event.getResponse()).setReqid(requestID);
        Logger.logger(SnmpAsyncTargetMock.class).info("getResultEvent requestID=" + requestID+",evnet="+event.getSource());
        return event;
    }

}
