package com.zte.mos.util.msg;

import com.zte.mos.domain.DN;
import com.zte.mos.inf.Maybe;
import com.zte.mos.inf.MoCmds;
import com.zte.mos.message.Mo;

@SuppressWarnings("serial")
public class MoConfigMsg extends MoMsgAdapter
{
    private final MoCmds cmd;
    private final Mo mo;
    private Maybe<Integer> transId = new Maybe<Integer>(null);

    public MoConfigMsg(MoCmds cmd, DN dn, Mo mo)
    {
        super(dn.toString());
        this.cmd = cmd;
        this.mo = mo;
    }

    @Override
    public MoCmds getCmd()
    {
        return cmd;
    }

    @Override
    public Maybe<Integer> getTransactionID()
    {
        return this.transId;
    }

    @Override
    public void setTransId(Maybe<Integer> transId)
    {
        this.transId = transId;
    }

    public Mo getMo()
    {
        return mo;
    }

    @Override
    public String toString()
    {
        return this.getCmd().name() + "{" +
                "dn='" + dn + '\'' +
                ", mo='" + mo + '\'' +
                ", transId=" + transId +
                '}';
    }
}
