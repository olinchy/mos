package com.odb.database.extended_odb;

import com.odb.database.Odb;
import com.zte.mos.domain.Group;
import com.zte.mos.exception.MOSException;
import com.zte.mos.persistent.Log;

import java.util.List;

/**
 * Created by olinchy on 5/20/14.
 */
public class StAdd<T extends Group> extends StNull<T>
{
    public StAdd(T t)
    {
        super(t);
    }

    @Override public void commitTo(Odb<T> odb) throws MOSException
    {
        ((ExtendedODB<T>) odb).doAdd(t);
    }

    @Override public boolean isDataDestroyed()
    {
        return false;
    }

    @Override
    public void logTo(List<Log<T>> logs, ExtendedODB<T> parent) throws MOSException
    {
        logs.add(new Log<T>(null, data(), Log.LogType.Insert));
    }

    @Override
    public void post(PostProcess postProcess) throws MOSException
    {
        postProcess.post();
    }
}
