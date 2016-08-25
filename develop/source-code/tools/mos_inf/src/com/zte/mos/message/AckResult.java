package com.zte.mos.message;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zte.mos.exception.AckException;
import com.zte.mos.inf.Maybe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by olinchy on 16-4-12.
 */
public class AckResult extends Result<AckException>
{
    public AckResult()
    {
    }

    public AckResult(Maybe<Integer> transId)
    {
        this.transId = transId;
    }

    private Maybe<Integer> transId;
    private List<AckException> mo = new ArrayList<AckException>();

    @Override
    public long getResult()
    {
        return mo.size() == 0 ? 0 : 1;
    }

    @Override
    public boolean isSuccess()
    {
        return getResult() == 0;
    }

    @Override
    public Throwable exception()
    {
        return mo.size() > 0 ? new AckException(mo) : null;
    }

    @Override
    public List<AckException> getMo()
    {
        return this.mo;
    }

    @Override
    public Maybe<Integer> getTransId()
    {
        return transId;
    }

    public AckResult push(AckException e)
    {
        this.mo.add(e);

        return this;
    }

    public void push(AckResult result)
    {
        if (result != this)
        {
            for(AckException ex : result.getMo())
            {
                this.mo.add(ex);
            }
        }
    }
}
