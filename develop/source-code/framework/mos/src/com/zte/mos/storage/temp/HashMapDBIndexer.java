
package com.zte.mos.storage.temp;

import com.odb.database.Indexer;
import com.odb.database.Key;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;

/**
 * Created by luoqingkai on 15-9-15.
 */
public class HashMapDBIndexer implements Indexer<ManagementObject> {
    @Override
    public Key key(ManagementObject data) throws MOSException {
        return new Key(data.dn());
    }

    @Override
    public int compare(Key o1, Key o2) {
        return o1.compareTo(o2);
    }
}
