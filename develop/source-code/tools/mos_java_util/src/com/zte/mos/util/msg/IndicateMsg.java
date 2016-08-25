package com.zte.mos.util.msg;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.*;
import com.zte.mos.message.Mo;
import com.zte.mos.util.tools.JsonUtil;

import java.util.*;

@SuppressWarnings("serial")
public abstract class IndicateMsg implements MoMsg
{

    private final MoCmds cmd;
    private String dn;

    public IndicateMsg(MoCmds cmd, String dn)
    {
        this.cmd = cmd;
        this.dn = dn;
    }

    @Override
    public MoCmds getCmd()
    {
        return cmd;
    }

    @Override
    public Maybe<Integer> getTransactionID()
    {
        return new Maybe<Integer>(null);
    }

    @Override
    public void setTransId(Maybe<Integer> transId)
    {
    }

    @Override
    @JsonProperty("dn")
    public String dn()
    {
        return dn;
    }

    @Override
    public String[] dns()
    {
        return new String[]{dn};
    }

    @Override
    public void setDN(String dn)
    {
        this.dn = dn;
    }

    @Override
    public String toString()
    {
        try
        {
            return JsonUtil.toString(this);
        }
        catch (MOSException e)
        {
            return "";
        }
    }

    @JsonProperty("oldValue")
    public abstract Mo oldValue();

    @JsonProperty("newValue")
    public abstract Mo newValue();

}
