package com.odb.database.extended_odb;

import com.odb.database.Odb;
import com.zte.mos.domain.Group;
import com.zte.mos.exception.MOSException;
import com.zte.mos.persistent.Log;

import java.util.List;

/**
 * Created by olinchy on 5/20/14.
 */
public class StReplace<T extends Group> extends StAdd<T>
{
    public StReplace(T t)
    {
        super(t);
    }

    @Override
    public void commitTo(Odb<T> odb) throws MOSException
    {
        ((ExtendedODB<T>) odb).doDel(t);
        ((ExtendedODB<T>) odb).doAdd(t);
    }

    @Override
    public void logTo(List<Log<T>> logs, ExtendedODB<T> parent)
    {
        logs.add(new Log<T>(data(), null, Log.LogType.Delete));
        logs.add(new Log<T>(null, data(), Log.LogType.Insert));
    }
}
