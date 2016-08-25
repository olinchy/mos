package com.zte.mos.storage.triggers;

import com.odb.database.*;
import com.sleepycat.bind.serial.SerialBinding;
import com.sleepycat.bind.serial.StoredClassCatalog;
import com.sleepycat.bind.tuple.StringBinding;
import com.sleepycat.je.*;
import com.sleepycat.persist.PrimaryIndex;
import com.zte.mos.domain.DN;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.BerkeleyDBException;
import com.zte.mos.exception.MOSException;
import com.zte.mos.exception.NullMoException;
import com.zte.mos.util.tools.Prop;

import java.io.File;

/**
 * Created by olinchy on 5/26/14 for MO_JAVA.
 */
public class MoIndexer extends BerkeleyDBObjectIndexer<Key, ManagementObject> implements Indexer<ManagementObject>
{
    public MoIndexer(String rootExternalDN)
    {
        if (rootExternalDN.equals("/"))
        {
            this.dbName = "ems";
        }
        else
        {
            this.dbName = rootExternalDN.replace("/", "_");
        }
    }

    public Key key(DN dn)
    {
        return new Key(dn);
    }

    @Override
    public int compare(Key o1, Key o2)
    {
        return o1.compareTo(o2);
    }

    @Override
    public void createDB(Environment environment)
    {
        this.env = environment;
        createDb();
        createSecondaryIndexes();
    }

    private void createDb()
    {
        EnvironmentConfig envConfig = new EnvironmentConfig();
        File file = new File(Prop.get("storage_path") + File.separator + dbName);
        if (!file.exists())
        {
            file.mkdirs();
        }
        envConfig.setAllowCreate(true);
        env = new Environment(file, envConfig);

        dbConfig = new DatabaseConfig();
        dbConfig.setAllowCreate(true);
        dbConfig.setSortedDuplicates(false);
        dbConfig.setBtreeComparator(new StringKeyComparator());
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
                new SerialBinding<ManagementObject>(catalog, ManagementObject.class);
    }

    @Override
    public String toString()
    {
        return dbName;
    }

    private void createSecondaryIndexes()
    {
        this.secondaryKeys = new SecondaryKey[]
                {new MoTypeKey(env, db, dataBinding), new SuperClassKey(env, db, dataBinding), new ClassNameKey(
                        env, db, dataBinding)};
        for (SecondaryKey secondaryKey : secondaryKeys)
        {
            secondaryKey.create();
        }
    }

    @Override
    public void all(Walker<Key, ManagementObject> walker) throws BerkeleyDBException
    {
        try
        {
            walker.walk(this);
        }
        catch (MOSException e)
        {
            throw new BerkeleyDBException(e);
        }
    }

    @Override
    public void remove(Key key) throws BerkeleyDBException
    {
        DatabaseEntry keyEntry = new DatabaseEntry();
        StringBinding.stringToEntry(key.toString(), keyEntry);
        OperationStatus status =
                db.delete(null, keyEntry);
        if (status != OperationStatus.SUCCESS)
        {
            throw new BerkeleyDBException(
                    "delete " + key.toString() + " got status " +
                            status);
        }
    }

    @Override
    public ManagementObject get(Key key)
    {
        DatabaseEntry keyEntry = new DatabaseEntry();
        DatabaseEntry dataEntry = new DatabaseEntry();

        StringBinding.stringToEntry(key.toString(), keyEntry);
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
    public void put(ManagementObject data) throws MOSException
    {

        Key key = this.key(data);

        DatabaseEntry keyEntry = new DatabaseEntry();
        DatabaseEntry dataEntry = new DatabaseEntry();
        StringBinding.stringToEntry(key.toString(), keyEntry);

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
    public Key key(ManagementObject data) throws MOSException
    {
        if (data == null)
        {
            throw new NullMoException("mo is not existed!");
        }
        return new Key(data.dn());
    }

    @Override
    public void stop()
    {
        for (SecondaryKey secondaryKey : secondaryKeys)
        {
            secondaryKey.close(null);
        }
        db.close();
        for (SecondaryKey secondaryKey : secondaryKeys)
        {
            secondaryKey.remove(null);
        }
        env.removeDatabase(null, dbName);
    }

    @Override
    public void clearAll()
    {
        Cursor cursor = null;
        try
        {
            cursor = db.openCursor(null, null);
            DatabaseEntry keyEntry = new DatabaseEntry();
            DatabaseEntry dataEntry = new DatabaseEntry();

            while (OperationStatus.SUCCESS.equals(cursor.getNext(keyEntry, dataEntry, LockMode.DEFAULT)))
            {
                db.delete(null, keyEntry);
            }
        }
        finally
        {
            if (cursor != null)
                cursor.close();
        }
    }

    @Override
    public void sync()
    {
        db.sync();
        catalogDb.sync();
    }

    @Override
    public Database getDatabase()
    {
        return db;
    }

    @Override
    public PrimaryIndex<Key, ManagementObject> getPrimaryIndex()
    {
        return null;
    }

    @Override
    public SecondaryDatabase getSecondaryIndex(String keyword) throws BerkeleyDBException
    {
        for (SecondaryKey secondaryKey : secondaryKeys)
        {
            if (secondaryKey.match(keyword))
            {
                return secondaryKey.database();
            }
        }
        throw new BerkeleyDBException("Unsupported secondary key: " + keyword);
    }

    @Override
    public ManagementObject entryToObject(DatabaseEntry dataEntry)
    {
        return dataBinding.entryToObject(dataEntry);
    }
}
