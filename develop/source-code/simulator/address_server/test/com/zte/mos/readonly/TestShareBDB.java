package com.zte.mos.readonly;

import com.odb.database.BerkeleyDB;
import com.odb.database.BerkeleyDBObjectIndexer;
import com.odb.database.Key;
import com.odb.database.Walker;
import com.sleepycat.bind.serial.SerialBinding;
import com.sleepycat.bind.serial.StoredClassCatalog;
import com.sleepycat.bind.tuple.StringBinding;
import com.sleepycat.je.*;
import com.sleepycat.persist.PrimaryIndex;
import com.zte.mos.domain.DN;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.BerkeleyDBException;
import com.zte.mos.exception.MOSException;
import com.zte.mos.util.tools.Prop;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.Properties;

/**
 * Created by olinchy on 16-7-7.
 */
public class TestShareBDB
{
    @BeforeClass
    public static void setUp() throws Exception
    {
        Properties properties = new Properties();
        properties.setProperty("readOnly", "true");
        Prop.set(properties);
    }

    @Test
    public void test() throws Exception
    {
        EnvironmentConfig envConfig = new EnvironmentConfig();
        envConfig.setAllowCreate(false);
        envConfig.setReadOnly(true);
        envConfig.setSharedCache(true);

        Environment env = new Environment(
                new File("/home/olinchy/workspace/ems-mos/build/address-server/store/"), envConfig);

        BerkeleyDB<Key, ManagementObject> berkeleyDB = new BerkeleyDB<Key, ManagementObject>(
                new BerkeleyDBObjectIndexer<Key, ManagementObject>()
                {
                    public SecondaryDatabase getSecondaryIndex(String keyword) throws BerkeleyDBException
                    {
                        return null;
                    }

                    public ManagementObject entryToObject(DatabaseEntry dataEntry)
                    {
                        return dataBinding.entryToObject(dataEntry);
                    }

                    public void createDB(Environment env)
                    {
                        dbName = "ems";
                        dbConfig = new DatabaseConfig();
                        dbConfig.setAllowCreate(false);
                        dbConfig.setReadOnly(true);
                        db = env.openDatabase(null, dbName, dbConfig);

                        DatabaseConfig catalogConfig = new DatabaseConfig();
                        catalogConfig.setAllowCreate(false);
                        catalogConfig.setReadOnly(true);
                        catalogDb = env.openDatabase(
                                null,
                                dbName + "_catalogDb",
                                catalogConfig);
                        StoredClassCatalog catalog = new StoredClassCatalog(catalogDb);

                        this.dataBinding =
                                new SerialBinding<ManagementObject>(catalog, ManagementObject.class);
                    }

                    public void all(Walker<Key, ManagementObject> walker) throws MOSException
                    {

                    }

                    public void remove(Key key) throws BerkeleyDBException
                    {

                    }

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

                    public void put(ManagementObject data) throws MOSException
                    {

                    }

                    public void stop()
                    {

                    }

                    public void clearAll()
                    {

                    }

                    public void sync()
                    {

                    }

                    public Database getDatabase()
                    {
                        return null;
                    }

                    public PrimaryIndex<Key, ManagementObject> getPrimaryIndex()
                    {
                        return null;
                    }
                }, env);

        ManagementObject mo = berkeleyDB.get(new Key(new DN("/")));

        System.out.println(mo.toString());
    }
}
