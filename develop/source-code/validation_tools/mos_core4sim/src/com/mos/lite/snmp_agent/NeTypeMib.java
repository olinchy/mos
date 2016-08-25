package com.mos.lite.snmp_agent;

/**
 * Created by ruitao on 16-7-19.
 */

import org.snmp4j.agent.mo.MOAccessImpl;
import org.snmp4j.agent.mo.MOScalar;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;

public class NeTypeMib extends MOScalar<OctetString>
{
    public NeTypeMib()
    {
        super(new OID("1.3.6.1.4.1.3902.2400.1.1.9.1.6.3.1"), MOAccessImpl.ACCESS_READ_ONLY,
              new OctetString("NR8960A"));
    }

    @Override
    public OctetString getValue()
    {
        return new OctetString("NR8960A");
    }
}


