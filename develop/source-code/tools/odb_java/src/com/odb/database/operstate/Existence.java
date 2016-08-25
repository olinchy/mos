package com.odb.database.operstate;

import com.odb.database.Key;
import com.odb.database.Odb;
import com.odb.database.extended_odb.OperatingSet;
import com.zte.mos.domain.Group;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by olinchy on 5/20/14.
 */
public enum Existence
{
    destroy, keep;

    public <T extends Group> void decide(Map<Key, OperationState<T>> store, Key key, OperationState<T> state)
    {
        if (this.equals(destroy))
        {
            store.remove(key);
        }
        else if (this.equals(keep))
        {
            store.put(key, state);
        }

    }

    public <T extends Group> void decide(OperatingSet<T> set, Key key, Odb<T> odb)
    {
        if (this.equals(destroy))
        {
            set.release(key, odb);
        }
    }
}
