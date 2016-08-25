package com.odb.database.extended_odb;

import com.odb.database.Odb;
import com.odb.database.operstate.DataState;
import com.zte.mos.domain.Group;
import com.zte.mos.exception.MOSException;
import com.zte.mos.persistent.Log;

import java.util.List;

/**
 * Created by olinchy on 5/20/14.
 */
public class StNull<T extends Group> implements DataState<T>
{
    protected T t;

    public StNull(T t)
    {
        this.t = t;
    }

    @Override
    public T data()
    {
        return t;
    }

    @Override
    public void commitTo(Odb<T> odb) throws MOSException
    {

    }

    @Override
    public boolean isDataDestroyed()
    {
        return true;
    }

    @Override
    public void logTo(List<Log<T>> logs, ExtendedODB<T> parent) throws MOSException
    {

    }

    @Override
    public void post(PostProcess postProcess) throws MOSException
    {

    }

    @Override
    public String toString()
    {
        return this.getClass().getSimpleName() + "{" +
                "t=" + t +
                '}';
    }
}
