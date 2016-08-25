package com.zte.mos.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zte.mos.inf.Maybe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by olinchy on 5/4/15.
 */
public class LsResult extends Result<String>
{
    @JsonProperty("result")
    public int res;
    public Throwable ex;
    @JsonProperty("children")
    public List<String> lst;
    public Maybe transId = new Maybe<Integer>(null);

    public void setTransId(Maybe transId)
    {
        this.transId = transId;
    }

    public LsResult()
    {
    }

    public LsResult(Maybe<Integer> transId, ArrayList<String> lst)
    {
        this.lst = lst;
        this.transId = transId;
    }

    public void setEx(Throwable ex)
    {
        this.ex = ex;
    }

    @Override
    public long getResult()
    {
        return res;
    }

    public void setResult(int res)
    {
        this.res = res;
    }

    @Override
    public boolean isSuccess()
    {
        return res == 0;
    }

    @Override
    public Throwable exception()
    {
        return ex;
    }

    @Override
    @JsonProperty("children")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    public List<String> getMo()
    {
        return lst;
    }

    @Override
    public Maybe<Integer> getTransId()
    {
        return transId;
    }
}
