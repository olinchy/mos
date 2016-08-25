package com.zte.mos.util.msg;

import com.zte.mos.inf.Maybe;
import com.zte.mos.inf.MoCmds;

/**
 * Created by olinchy on 7/9/14 for MO_JAVA.
 */
public class MoGetMetaMsg extends MoMsgAdapter
{
    private final String name;
    public final boolean isDnValid;
    private Maybe<Integer> transId;

    public MoGetMetaMsg(String dn, String name, boolean isDnValid)
    {
        super(dn);
        this.name = name;
        this.isDnValid = isDnValid;
    }

    @Override public MoCmds getCmd()
    {
        return MoCmds.MoGetMetaRequest;
    }

    @Override public Maybe<Integer> getTransactionID()
    {
        return transId;
    }

    @Override public void setTransId(Maybe<Integer> transId)
    {
        this.transId = transId;
    }

    public String getName()
    {
        return name;
    }

    @Override public String toString()
    {
        return "MoGetMetaMsg{" +
                "name='" + name + '\'' +
                '}';
    }
}
