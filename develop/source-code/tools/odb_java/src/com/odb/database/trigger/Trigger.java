package com.odb.database.trigger;

import com.odb.database.Odb;
import com.zte.mos.domain.Group;
import com.zte.mos.exception.MOSException;

/**
 * Created by olinchy on 5/21/14 for MO_JAVA.
 */
public interface Trigger<T extends Group>
{
    void activate(T t, Odb<T> odb) throws MOSException;

    void register(Odb<T> odb);
}
