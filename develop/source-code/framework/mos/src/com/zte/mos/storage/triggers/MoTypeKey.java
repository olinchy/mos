package com.zte.mos.storage.triggers;

import com.odb.database.SecondaryKey;
import com.sleepycat.bind.serial.SerialBinding;
import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.je.*;
import com.zte.mos.domain.DNWrapper;
import com.zte.mos.domain.ManagementObject;

/**
 * Created by olinchy on 15-7-13.
 */
public class MoTypeKey implements SecondaryKey
{
    protected final Environment env;
    protected final Database db;
    protected final SerialBinding<ManagementObject> dataBinding;
    protected String dbName;
    private TupleBinding<String> objClassBinding;
    protected SecondaryDatabase secondaryDB;

    public MoTypeKey(Environment env, Database db, SerialBinding<ManagementObject> dataBinding)
    {
        this.env = env;
        this.db = db;
        this.dataBinding = dataBinding;
    }

    @Override
    public void create()
    {
        objClassBinding =
                TupleBinding.getPrimitiveBinding(String.class);

        /*
         * Open a secondary database to allow accessing the primary
         * database by the secondary key value.
         */
        SecondaryConfig secConfig = new SecondaryConfig();
        secConfig.setAllowCreate(true);
        secConfig.setSortedDuplicates(true);
        secConfig.setKeyCreator(new StringKeyCreator(objClassBinding, dataBinding));
        secondaryDB =
                env.openSecondaryDatabase(
                        null, this.dbName = db.getDatabaseName() + "_typeDb",
                        db, secConfig);
    }

    @Override
    public void close(Transaction txn)
    {
        this.secondaryDB.close();
    }

    @Override
    public boolean match(String keyword)
    {
        return keyword.equalsIgnoreCase("moType");
    }

    @Override
    public SecondaryDatabase database()
    {
        return this.secondaryDB;
    }

    @Override
    public void remove(Transaction txn)
    {
        this.env.removeDatabase(txn, dbName);
    }

    @Override
    public void rename(String name)
    {
        this.dbName = name;
    }

    @Override
    public String getDBName()
    {
        return dbName;
    }

    @Override
    public void setDatabase(SecondaryDatabase secondaryDatabase)
    {
        this.secondaryDB = secondaryDatabase;
    }

    private class StringKeyCreator implements SecondaryKeyCreator
    {
        private final TupleBinding<String> moTypeBinding;
        private final SerialBinding<ManagementObject> dataBinding;

        public StringKeyCreator(
                TupleBinding<String> moTypeBinding, SerialBinding<ManagementObject> dataBinding)
        {
            this.moTypeBinding = moTypeBinding;
            this.dataBinding = dataBinding;
        }

        @Override
        public boolean createSecondaryKey(
                SecondaryDatabase secondary, DatabaseEntry key, DatabaseEntry data, DatabaseEntry result)
        {
            ManagementObject mo = dataBinding.entryToObject(data);
            String type;
            if (mo.isGroup())
                type = "Group";
            else
                type = new DNWrapper(mo.dn().toString()).type();
            if (key != null)
            {
                moTypeBinding.objectToEntry(type, result);
                return true;
            }
            return false;
        }
    }
}
