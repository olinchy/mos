package com.odb.database.operstate;

import com.odb.database.Odb;
import com.zte.mos.domain.Group;
import com.zte.mos.exception.MOSException;

/**
 * Created by olinchy on 10/16/14 for MO_JAVA.
 */
public class RemoveChild<T extends Group> extends AddChild<T>
{
    public RemoveChild(T parent, T child)
    {
        super(parent, child);
    }

    @Override
    public void commitTo(Odb<T> odb) throws MOSException
    {
        odb.removeChild(parent, child);
    }

    @Override
    public void merge(T data)
    {
        data.remove(child);
    }
}
