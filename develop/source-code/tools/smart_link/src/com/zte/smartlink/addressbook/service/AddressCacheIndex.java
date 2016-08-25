package com.zte.smartlink.addressbook.service;

import com.odb.database.BerkeleyDBObjectIndexer;
import com.odb.database.Walker;
import com.sleepycat.bind.serial.SerialBinding;
import com.sleepycat.bind.serial.StoredClassCatalog;
import com.sleepycat.bind.tuple.StringBinding;
import com.sleepycat.je.*;
import com.sleepycat.persist.PrimaryIndex;
import com.zte.mos.exception.BerkeleyDBException;
import com.zte.mos.exception.MOSException;
import com.zte.mos.util.tools.Prop;
import com.zte.smartlink.Address;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by olinchy on 16-1-8.
 */
public class AddressCacheIndex extends BerkeleyDBObjectIndexer<Address, ClientAddress>
{
    public AddressCacheIndex()
    {
        dbName = "address";
    }

    @Override
    public SecondaryDatabase getSecondaryIndex(String keyword) throws BerkeleyDBException
    {
        return null;
    }

    @Override
    public ClientAddress entryToObject(DatabaseEntry dataEntry)
    {
        return dataBinding.entryToObject(dataEntry);
    }

    @Override
    public void createDB(Environment env)
    {
        this.env = env;
        createDb();
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
        dbConfig.setBtreeComparator(new AddressKeyComparator());
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
                new SerialBinding<ClientAddress>(catalog, ClientAddress.class);

        new Timer("Timer-" + AddressCacheIndex.class.getSimpleName()).schedule(
                new TimerTask()
                {
                    @Override
                    public void run()
                    {
                        AddressCacheIndex.this.sync();
                    }
                }, 0, 5000);
    }

    @Override
    public void all(
            Walker<Address, ClientAddress> walker) throws MOSException
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
    public void remove(Address key) throws BerkeleyDBException
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
    public ClientAddress get(Address key)
    {
        DatabaseEntry keyEntry = new DatabaseEntry();
        DatabaseEntry dataEntry = new DatabaseEntry();

        StringBinding.stringToEntry(key.toString(), keyEntry);
        OperationStatus status =
                db.get(null, keyEntry, dataEntry, LockMode.READ_COMMITTED);

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
    public void put(ClientAddress data) throws MOSException
    {
        DatabaseEntry keyEntry = new DatabaseEntry();
        DatabaseEntry dataEntry = new DatabaseEntry();
        StringBinding.stringToEntry(data.address.toString(), keyEntry);

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
        this.sync();
    }

    @Override
    public void stop()
    {
        db.close();
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
    public PrimaryIndex<Address, ClientAddress> getPrimaryIndex()
    {
        return null;
    }
}