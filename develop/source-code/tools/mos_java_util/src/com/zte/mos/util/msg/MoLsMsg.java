package com.zte.mos.util.msg;

import com.zte.mos.inf.Maybe;
import com.zte.mos.inf.MoCmds;

import static com.zte.mos.inf.MoCmds.MoLsRequest;

/**
 * Created by olinchy on 6/16/14 for MO_JAVA.
 */
public class MoLsMsg extends MoMsgAdapter
{
    private Maybe<Integer> transId;

    public MoLsMsg(String dn, Maybe<Integer> transId)
    {
        super(dn);
        this.setTransId(transId);
    }

    public MoLsMsg(String dn)
    {
        this(dn, new Maybe<Integer>(null));
    }

    @Override public MoCmds getCmd()
    {
        return MoLsRequest;
    }

    @Override public Maybe<Integer> getTransactionID()
    {
        return transId;
    }

    @Override public void setTransId(Maybe<Integer> transId)
    {
        this.transId = transId;
    }

    @Override public String toString()
    {
        return "MoLsMsg{" +
                "dn='" + dn + '\'' +
                '}';
    }
}
