package com.odb.database;

import com.zte.mos.exception.MOSException;

/**
 * Created by olinchy on 15-7-22.
 */
public interface Walker<PK, T>
{
    void walk(BerkeleyDBIndexer<PK, T> indexer) throws MOSException;
}
