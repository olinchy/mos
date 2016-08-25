package com.zte.mos.message;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.Maybe;
import com.zte.mos.util.DistinguishedList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by olinchy on 5/6/15.
 */
public class ConfResult extends Result<String>
{
    public ConfResult()
    {
    }

    public ConfResult(Throwable throwable)
    {
        this(throwable, new DistinguishedList<String>(), new Maybe<Integer>(null));
    }

    public ConfResult(DistinguishedList<String> lstDN, Maybe<Integer> transId)
    {
        this(null, lstDN, transId);
    }

    public ConfResult(Throwable e, DistinguishedList<String> lstDN, Maybe<Integer> transId)
    {
        this.ex = e == null ? null : e instanceof MOSException ? (MOSException) e : new MOSException(e);
        this.transId = transId;
        this.affectDNs.addAll(lstDN);
    }

    private MOSException ex;
    private Maybe<Integer> transId;
    private List<String> affectDNs = new ArrayList<String>();

    public void setEx(MOSException ex)
    {
        this.ex = ex;
    }

    @Override
    public long getResult()
    {
        return ex == null ? 0 : (int) ex.getErrorCode().getErrorCode();
    }

    @Override
    public boolean isSuccess()
    {
        return ex == null;
    }

    @Override
    public Throwable exception()
    {
        return ex;
    }

    @Override
    public List<String> getMo()
    {
        ArrayList<String> list = new ArrayList<String>();
        list.addAll(affectDNs);
        return list;
    }

    public void setMo(List<String> affectDNs)
    {
        this.affectDNs = affectDNs;
    }

    @Override
    @JsonSerialize(using = TransIdSerializer.class)
    public Maybe<Integer> getTransId()
    {
        return transId;
    }

    public void setTransId(int transId)
    {
        this.transId = new Maybe<Integer>(transId);
    }

    @Override
    public String toString()
    {
        return "ConfResult{" +
                "ex=" + ex +
                ", transId=" + transId +
                '}';
    }
}
