package com.odb.database.extended_odb;

import com.odb.database.Odb;
import com.zte.mos.domain.Group;
import com.zte.mos.exception.MOSException;
import com.zte.mos.persistent.Log;

import java.util.List;

/**
 * Created by olinchy on 5/20/14.
 */
public class StSet<T extends Group> extends StAdd<T>
{
    public StSet(T t)
    {
        super(t);
    }

    @Override
    public void commitTo(Odb<T> odb) throws MOSException
    {
        ((ExtendedODB<T>) odb).doSet(t);
    }

    @Override
    public void logTo(List<Log<T>> logs, ExtendedODB<T> parent) throws MOSException
    {
        logs.add(new Log<T>(parent.get(data()), data(), Log.LogType.Update));
    }
}
