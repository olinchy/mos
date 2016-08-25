package com.zte.mos.util.msg;

import com.zte.mos.inf.MoMsg;

/**
 * Created by olinchy on 9/29/14 for MO_JAVA.
 */
public abstract class MoMsgAdapter implements MoMsg
{
    public MoMsgAdapter(String dn)
    {
        this.dn = dn;
    }

    protected String dn;

    @Override public String dn()
    {
        return dn;
    }

    @Override public String[] dns()
    {
        return new String[] { dn };
    }

    @Override public void setDN(String dn)
    {
        this.dn = dn;
    }

}
