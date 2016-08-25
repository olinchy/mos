package com.zte.mos.util.msg;

import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.Maybe;
import com.zte.mos.inf.MoCmds;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.util.tools.JsonUtil;

/**
 * Created by olinchy on 6/17/14 for MO_JAVA.
 */

public class MoAckMsg implements MoMsg
{
    private Maybe<Integer> transId;
    private String[] dn;
    private Ack ack;

    public MoAckMsg(Maybe<Integer> transId, Ack ack, String... dns)
    {
        this.transId = transId;
        this.dn = dns;
        this.ack = ack;
    }

    public MoAckMsg clone(){
        return new MoAckMsg(transId, ack, dn);
    }

    @Override
    public MoCmds getCmd()
    {
        return MoCmds.MoAck;
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
        return dn != null && dn.length > 0 ? dn[0] : "";
    }

    @Override
    public void setDN(String dn)
    {

    }

    public String[] dns()
    {
        return dn;
    }

    public Ack getAck()
    {
        return ack;
    }

    public void setDn(String... dn)
    {
        this.dn = dn;
    }

    @Override
    public String toString()
    {
        try
        {
            return "MoAckMsg{" +
                    "transId=" + transId +
                    ", ack=" + ack + JsonUtil.toString(this.dn) +
                    '}';
        }
        catch (MOSException e)
        {
            return "MoAckMsg{} caught " + e.getClassName() + ":" + e.getMessage();
        }
    }

    public enum Ack
    {
        commit,
        rollback
    }
}

