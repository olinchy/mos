package com.zte.mos.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zte.mos.exception.ErrorCodeGetter;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.Maybe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by olinchy on 15-9-23.
 */
public class ConfResultSet extends Result<ConfResult>
{
    public ConfResultSet()
    {
    }

    @JsonIgnore
    private boolean isSuccess = true;
    private ArrayList<ConfResult> mo = new ArrayList<ConfResult>();
    private Maybe<Integer> transId;

    @Override
    public long getResult()
    {
        return isSuccess ? 0 : ErrorCodeGetter.getErrorCode("GENERAL_ERROR");
    }

    @Override
    public boolean isSuccess()
    {
        return isSuccess;
    }

    @Override
    public Throwable exception()
    {
        return null;
    }

    @Override
    public List<ConfResult> getMo()
    {
        return mo;
    }

    public void setMo(ArrayList<ConfResult> mo)
    {
        this.mo = mo;
        for (ConfResult confResult : mo)
        {
            if (!confResult.isSuccess())
                isSuccess = false;
        }
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

    public void setEx(MOSException ex)
    {
    }

    public void setEx(Throwable throwable)
    {

    }

    public void merge(ConfResultSet confResultSet)
    {
        for (ConfResult confResult : confResultSet.getMo())
        {
            this.add(confResult);
        }
    }

    public void add(ConfResult result)
    {
        mo.add(result);
        if (!result.isSuccess())
        {
            isSuccess = false;
        }
        if (transId == null || transId.nothing())
        {
            transId = result.getTransId();
        }
    }

    @Override
    public String toString()
    {
        return "ConfResultSet{" +
                "mo=" + mo +
                '}';
    }
}
