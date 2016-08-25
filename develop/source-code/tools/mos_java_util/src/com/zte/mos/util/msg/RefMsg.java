package com.zte.mos.util.msg;

import com.zte.mos.domain.DN;
import com.zte.mos.inf.Maybe;
import com.zte.mos.inf.MoCmds;
import com.zte.mos.inf.MoMsg;

/**
 * Created by olinchy on 15-5-29.
 */
public class RefMsg implements MoMsg
{
    private final String from;
    private String to;
    private Maybe<Integer> transId;

    public RefMsg(DN to, DN from, Maybe<Integer> transId)
    {
        this.to = to.toString();
        this.from = from.toString();
        this.transId = transId;
    }

    public static RefMsg refMsg(DN to, DN from, Maybe<Integer> transId)
    {
        return new RefMsg(to, from, transId);
    }

    public static RefMsg refMsg(DN to, DN from)
    {
        return new RefMsg(to, from, new Maybe<Integer>(null));
    }

    @Override
    public MoCmds getCmd()
    {
        return MoCmds.MoRef;
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

    @Override
    public String dn()
    {
        return to.toString();
    }

    @Override
    public String[] dns()
    {
        return new String[]{to.toString()};
    }

    @Override
    public void setDN(String dn)
    {
        this.to = dn;
    }

    public DN getFrom()
    {
        return new DN(from);
    }

    @Override
    public String toString()
    {
        return this.getClass().getSimpleName() + "{" +
                "from=" + from +
                ", to=" + to +
                ", transId=" + transId +
                '}';
    }
}
