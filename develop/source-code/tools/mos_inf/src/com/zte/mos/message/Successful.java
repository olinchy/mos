package com.zte.mos.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zte.mos.inf.Maybe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by olinchy on 3/18/15 for mosjava.
 */
public class Successful<T> extends Result<T>
{
    private ArrayList<T> lst = new ArrayList<T>();
    public Maybe<Integer> transId = new Maybe<Integer>(null);

    public Successful(T t)
    {
        lst.clear();
        lst.add(t);
    }

    public Successful()
    {
    }

    public Successful(Maybe<Integer> transId)
    {
        this(null, transId);
    }

    public Successful(ArrayList<T> lst, Maybe<Integer> transId)
    {
        this.lst.clear();
        if (lst != null)
            this.lst.addAll(lst);
        this.transId = transId;
    }

    public Successful(ArrayList<T> lst)
    {
        this(lst, new Maybe<Integer>(null));
    }

    @Override
    public long getResult()
    {
        return 0;
    }

    @Override
    public boolean isSuccess()
    {
        return true;
    }

    @Override
    public Throwable exception()
    {
        return null;
    }

    @Override
    public List<T> getMo()
    {
        return lst;
    }

    @Override
    @JsonIgnore
    public Maybe<Integer> getTransId()
    {
        return transId;
    }

}
