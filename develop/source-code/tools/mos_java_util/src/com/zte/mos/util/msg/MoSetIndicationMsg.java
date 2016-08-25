package com.zte.mos.util.msg;

import com.zte.mos.message.Mo;

import static com.zte.mos.inf.MoCmds.MoSetInd;

@SuppressWarnings("serial")
public class MoSetIndicationMsg extends IndicateMsg
{
    private final Mo newValue;
    private final Mo oldValue;

    public MoSetIndicationMsg(String dn, Mo oldValue, Mo newValue)
    {
        super(MoSetInd, dn);
        this.newValue = newValue;
        this.oldValue = oldValue;
    }

    @Override
    public Mo oldValue()
    {
        return oldValue;
    }

    @Override
    public Mo newValue()
    {
        return newValue;
    }
}
