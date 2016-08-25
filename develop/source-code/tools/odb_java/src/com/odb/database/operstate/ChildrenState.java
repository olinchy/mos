package com.odb.database.operstate;

import com.odb.database.Odb;
import com.zte.mos.domain.Group;
import com.zte.mos.exception.MOSException;

/**
 * Created by olinchy on 10/16/14 for MO_JAVA.
 */
public interface ChildrenState<T extends Group>
{
    void commitTo(Odb<T> odb) throws MOSException;

    void merge(T data);
}
