package com.zte.mos.util.msg;

import com.fasterxml.jackson.databind.JsonNode;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.Maybe;
import com.zte.mos.inf.MoCmds;
import com.zte.mos.util.tools.JsonUtil;

import java.io.IOException;

@SuppressWarnings("unused")
public class MoFindMsg extends MoMsgAdapter
{

    private final String dnRegex;
    private String exp = "";
    private Template template = null;
    private Maybe<Integer> transId = new Maybe<Integer>(null);

    public MoFindMsg(String exp, Template template, String dn)
    {
        this(exp, template, new Maybe<Integer>(null), dn, "**");
    }

    public MoFindMsg(String exp, Template template,
                     Maybe<Integer> transId, String dn)
    {
        this(exp, template, transId, dn, "**");
    }

    /**
     * @param exp      表达式
     * @param template 模板
     * @param transId
     * @param dn       Under
     * @param dnRegex
     */
    public MoFindMsg(String exp, Template template,
                     Maybe<Integer> transId, String dn, String dnRegex)
    {
        super(dn);
        this.exp = exp;
        this.template = template;
        this.transId = transId;
        this.dnRegex = dnRegex;
    }

    private int startIndex = 0;
    private int expectedCount = -1;

    public void setStartIndex(int startIndex)
    {
        this.startIndex = startIndex;
    }

    public void setExpectedCount(int expectedCount)
    {
        this.expectedCount = expectedCount;
    }

    public String getDnRegex()
    {
        return dnRegex;
    }

    public String getExp()
    {
        return exp;
    }

    public void setExp(String exp)
    {
        this.exp = exp;
    }

    @Override
    public MoCmds getCmd()
    {
        return MoCmds.MoFindRequest;
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

    public JsonNode nodeByTemplate(
            Object mo) throws MOSException, IOException, IllegalAccessException
    {
        if (template != null)
        {
            return template.toNode(mo);
        }
        else
        {
            return JsonUtil.toNode(JsonUtil.toString(mo));
        }
    }

    @Override
    public String toString()
    {
        return "MoFindMsg{" +
                "exp='" + exp + '\'' +
                ", dnRegex='" + dnRegex + '\'' +
                '}';
    }

    public int getStartIndex()
    {
        return startIndex;
    }

    public int getExpectedCount()
    {
        return expectedCount;
    }
}
