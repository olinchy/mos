package com.odb.database;

import com.sleepycat.bind.serial.SerialBinding;
import com.sleepycat.je.*;
import com.zte.mos.exception.BerkeleyDBException;

/**
 * Created by olinchy on 15-7-13.
 */
public abstract class BerkeleyDBObjectIndexer<PK, T> implements BerkeleyDBIndexer<PK, T>
{
    protected String dbName;
    protected Environment env;
    protected Database catalogDb;
    protected SerialBinding<T> dataBinding;
    protected Database db;
    protected DatabaseConfig dbConfig;
    protected SecondaryKey[] secondaryKeys;

    public abstract SecondaryDatabase getSecondaryIndex(String keyword) throws BerkeleyDBException;

    public abstract T entryToObject(DatabaseEntry dataEntry);

    public void rename(String dbName)
    {
        for (int i = 0; i < secondaryKeys.length; i++)
        {
            SecondaryKey secondaryKey = secondaryKeys[i];

            String secondaryName = secondaryKey.database().getDatabaseName();
            String name = dbName + "_" + i;

            secondaryKey.database().close();
            secondaryKey.rename(name);
            env.renameDatabase(null, secondaryName, name);
        }
        this.getDatabase().close();
        env.renameDatabase(null, this.dbName, this.dbName = dbName);
        this.db = env.openDatabase(null, this.dbName, this.dbConfig);

        for (SecondaryKey secondaryKey : secondaryKeys)
        {
            secondaryKey.setDatabase(env.openSecondaryDatabase(
                    null, secondaryKey.getDBName(), this.db, secondaryKey.database().getConfig()));
        }
    }
}
