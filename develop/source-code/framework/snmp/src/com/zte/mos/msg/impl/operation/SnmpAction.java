package com.zte.mos.msg.impl.operation;

import com.zte.mos.msg.framework.operation.Action;
import com.zte.ums.uep.protocol.snmp.snmp2.SnmpVar;

/**
 * Created by ccy on 7/21/15.
 */
public class SnmpAction extends Action
{
    private String[] oids;
    private SnmpVar[] vars;

    public SnmpAction(Action oper)
    {
        super(oper.getMo(), oper.getActionName(), oper.getParas());
    }

    public void setOids(String[] oids)
    {
        this.oids = oids;
    }

    public void setVars(SnmpVar[] vars)
    {
        this.vars = vars;
    }

    public String[] getOids()
    {

        return oids;
    }

    public SnmpVar[] getVars()
    {
        return vars;
    }
}
