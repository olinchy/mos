package com.zte.mos.util.msg;

import com.zte.mos.domain.DN;
import com.zte.mos.inf.Maybe;
import com.zte.mos.inf.MoCmds;

/**
 * Created by MOS on 14-6-19.
 */
public class MoVersionRequestMsg extends MoMsgAdapter
{

    private Maybe<Integer> transId;

    public MoVersionRequestMsg(DN dn)
    {
        super(dn.toString());
        transId = new Maybe<Integer>(null);
    }

    @Override
    public MoCmds getCmd()
    {
        return MoCmds.MoVersionRequest;
    }

    @Override
    public Maybe<Integer> getTransactionID()
    {
        return transId;
    }

    @Override
    public void setTransId(Maybe<Integer> transId)
    {
        this.transId = transId;
    }

    @Override public String toString()
    {
        return "MoVersionRequestMsg{" +
                "dn=" + dn +
                '}';
    }

}
