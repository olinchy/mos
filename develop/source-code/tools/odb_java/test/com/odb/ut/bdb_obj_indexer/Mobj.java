package com.odb.ut.bdb_obj_indexer;

import com.odb.database.BerkeleyDBIndexer;
import com.odb.database.BerkeleyDBObjectIndexer;
import com.odb.database.Walker;
import com.sleepycat.bind.serial.SerialBinding;
import com.sleepycat.bind.serial.StoredClassCatalog;
import com.sleepycat.bind.tuple.StringBinding;
import com.sleepycat.je.*;
import com.sleepycat.persist.PrimaryIndex;
import com.zte.mos.exception.BerkeleyDBException;
import com.zte.mos.exception.MOSException;

import java.io.Serializable;

/**
 * Created by olinchy on 16-5-18.
 */
public class Mobj implements Serializable
{
    public String name;
    public String value;

    public static BerkeleyDBIndexer<String, Mobj> createIndex()
    {
        return new BerkeleyDBObjectIndexer<String, Mobj>()
        {
            @Override
            public SecondaryDatabase getSecondaryIndex(String keyword) throws BerkeleyDBException
            {
                return null;
            }

            @Override
            public Mobj entryToObject(DatabaseEntry dataEntry)
            {
                return dataBinding.entryToObject(dataEntry);
            }

            @Override
            public void createDB(Environment env)
            {
                this.dbName = "test";
                this.env = env;
                dbConfig = new DatabaseConfig();
                dbConfig.setAllowCreate(true);
                dbConfig.setSortedDuplicates(false);
                dbConfig.setDeferredWrite(true);
                db = env.openDatabase(null, dbName, dbConfig);

                DatabaseConfig catalogConfig = new DatabaseConfig();
                catalogConfig.setAllowCreate(true);
                catalogConfig.setDeferredWrite(true);
                catalogDb = env.openDatabase(
                        null,
                        dbName + "_catalogDb",
                        catalogConfig);
                StoredClassCatalog catalog = new StoredClassCatalog(catalogDb);

                this.dataBinding =
                        new SerialBinding<Mobj>(catalog, Mobj.class);
            }

            @Override
            public void all(Walker<String, Mobj> walker) throws MOSException
            {

            }

            @Override
            public void remove(String key) throws BerkeleyDBException
            {
                DatabaseEntry keyEntry = new DatabaseEntry();
                StringBinding.stringToEntry(key, keyEntry);
                OperationStatus status =
                        db.delete(null, keyEntry);
                if (status != OperationStatus.SUCCESS)
                {
                    throw new BerkeleyDBException(
                            "delete " + key + " got status " +
                                    status);
                }
            }

            @Override
            public Mobj get(String key)
            {
                DatabaseEntry keyEntry = new DatabaseEntry();
                DatabaseEntry dataEntry = new DatabaseEntry();

                StringBinding.stringToEntry(key, keyEntry);
                OperationStatus status =
                        db.get(null, keyEntry, dataEntry, LockMode.READ_UNCOMMITTED_ALL);

                if (status != OperationStatus.SUCCESS)
                {
                    return null;
                }
                else
                {
                    return dataBinding.entryToObject(dataEntry);
                }
            }

            @Override
            public void put(Mobj data) throws MOSException
            {
                DatabaseEntry keyEntry = new DatabaseEntry();
                DatabaseEntry dataEntry = new DatabaseEntry();
                StringBinding.stringToEntry(data.name, keyEntry);

                dataBinding.objectToEntry(data, dataEntry);

                OperationStatus statue;
                try
                {
                    statue = db.put(null, keyEntry, dataEntry);
                }
                catch (Throwable e)
                {
                    throw new BerkeleyDBException(e);
                }
                if (!statue.equals(OperationStatus.SUCCESS))
                {
                    throw new BerkeleyDBException("put " + data.toString() + " into " + dbName + " failed!");
                }
            }

            @Override
            public void stop()
            {

            }

            @Override
            public void clearAll()
            {

            }

            @Override
            public void sync()
            {

            }

            @Override
            public Database getDatabase()
            {
                return db;
            }

            @Override
            public PrimaryIndex<String, Mobj> getPrimaryIndex()
            {
                return null;
            }
        };
    }
}
