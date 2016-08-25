package com.odb.database;

import com.sleepycat.je.Database;
import com.sleepycat.je.Environment;
import com.sleepycat.persist.PrimaryIndex;
import com.zte.mos.exception.BerkeleyDBException;
import com.zte.mos.exception.MOSException;

/**
 * Created by olinchy on 15-7-22.
 */
public interface BerkeleyDBIndexer<PK, E>
{
    void createDB(Environment env);

    void all(Walker<PK, E> walker) throws MOSException;

    void remove(PK key) throws BerkeleyDBException;

    E get(PK key);

    void put(E data) throws MOSException;

    void stop();

    void clearAll();

    void sync();

    Database getDatabase();

    PrimaryIndex<PK, E> getPrimaryIndex();

    void rename(String dbName);
}
