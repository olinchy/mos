package com.zte.mos.msg.impl.snmp;

import com.zte.mos.msg.framework.MsgProcess;
import com.zte.mos.msg.framework.session.ISession;

/**
 * Created by luoqingkai on 15-9-29.
 */
public abstract class SnmpProcess extends MsgProcess {

    protected SnmpSession session = null;

    public void setSession(ISession session) {
        this.session = (SnmpSession) session;
    }

    @Override
    public String protocol() {
        return "SNMP";
    }
}
