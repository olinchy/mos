package com.zte.mos.msg.framework.operation;

/**
 * Created by luoqingkai on 15-5-12.
 */
public class Connect extends NeOperation
{
    public Connect(String dn)
    {
        super(dn);
    }

    @Override
    public String getOperation()
    {
        return OperEnum.Connect.name();
    }
}
