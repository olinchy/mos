package com.odb.database;

import com.zte.mos.exception.BerkeleyDBException;
import com.zte.mos.exception.MOSException;

/**
 * Created by luoqingkai on 15-9-14.
 */
public interface PersistDB<PK, T> {
    void all(Walker<PK, T> walker) throws MOSException;

    void remove(PK key) throws BerkeleyDBException;

    T get(PK key) throws BerkeleyDBException;

    void put(T data) throws MOSException;

    void stop();

    void clearAll();

    void sync();

    Indexer<T> getPrimaryIndex();
}
