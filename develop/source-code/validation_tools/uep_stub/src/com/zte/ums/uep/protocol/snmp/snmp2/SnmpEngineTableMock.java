package com.zte.ums.uep.protocol.snmp.snmp2;

/**
 * Created by pavel on 16-4-6.
 */
public class SnmpEngineTableMock extends SnmpEngineTable
{
    private SnmpEngineEntry engineEntry = null;

    public SnmpEngineTableMock()
    {
//        super();
    }

    public synchronized SnmpEngineEntry getEntry(String address, int port)
    {
        this.engineEntry = new SnmpEngineEntry(address, port);
        return engineEntry;
    }

    public SnmpEngineEntry getEngineEntry(){
        return engineEntry;
    }
}
