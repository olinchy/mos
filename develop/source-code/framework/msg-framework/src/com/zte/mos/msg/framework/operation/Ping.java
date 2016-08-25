package com.zte.mos.msg.framework.operation;

/**
 * Created by luoqingkai on 15-5-21.
 */
public class Ping extends NeOperation
{
    public Ping(String dn)
    {
        super(dn);
    }

    @Override
    public String getOperation()
    {
        return OperEnum.Ping.name();
    }
}
