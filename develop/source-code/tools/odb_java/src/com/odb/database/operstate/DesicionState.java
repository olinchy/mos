package com.odb.database.operstate;

import com.odb.database.Key;
import com.odb.database.Odb;
import com.odb.database.extended_odb.OperatingSet;
import com.odb.database.operation.OperTypes;
import com.zte.mos.domain.Group;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by olinchy on 10/16/14 for MO_JAVA.
 */
public interface DesicionState<T extends Group>
{
    void decideExistence(Map<Key, OperationState<T>> store, Key key);

    void decideExistence(OperatingSet set, Key key, Odb<T> odb);

}
