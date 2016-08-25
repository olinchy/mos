package com.zte.mos.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.Maybe;
import com.zte.mos.util.tools.JsonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by olinchy on 5/5/15.
 */
public class GetMetaResult extends Result<MoMetaClass>
{
    private String metaString;
    private MoMetaClass meta;

    public GetMetaResult(MoMetaClass meta)
    {
        try
        {
            this.metaString = JsonUtil.toString(meta);
        }
        catch (MOSException e)
        {
            this.metaString = meta.toString();
        }
        this.meta = meta;
        this.meta.type = null;
    }

    @Override
    public long getResult()
    {
        return meta != null ? 0 : 1;
    }

    @Override
    public boolean isSuccess()
    {
        return meta != null;
    }

    @Override
    public Throwable exception()
    {
        return null;
    }

    @Override
    @JsonIgnore
    public List<MoMetaClass> getMo()
    {
        ArrayList<MoMetaClass> lst;
        (lst = new ArrayList<MoMetaClass>()).add(meta);
        return lst;
    }

    @Override
    @JsonIgnore
    public Maybe<Integer> getTransId()
    {
        return null;
    }

    public String getMeta()
    {
        return metaString;
    }
}
