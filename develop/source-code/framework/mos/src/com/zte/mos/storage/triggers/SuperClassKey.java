package com.zte.mos.storage.triggers;

import com.sleepycat.bind.serial.SerialBinding;
import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.je.*;
import com.zte.mos.domain.ManagementObject;

/**
 * Created by olinchy on 15-9-6.
 */
public class SuperClassKey extends MoTypeKey
{
    private TupleBinding<String> parentBinding;

    public SuperClassKey(Environment env, Database db, SerialBinding<ManagementObject> dataBinding)
    {
        super(env, db, dataBinding);
    }

    @Override
    public void create()
    {
        parentBinding =
                TupleBinding.getPrimitiveBinding(String.class);

        /*
         * Open a secondary database to allow accessing the primary
         * database by the secondary key value.
         */
        SecondaryConfig secConfig = new SecondaryConfig();
//        secConfig.setTransactional(true);
        secConfig.setAllowCreate(true);
        secConfig.setSortedDuplicates(true);
        secConfig.setKeyCreator(new ParentKeyCreator(parentBinding, dataBinding));
//        secConfig.setDeferredWrite(true);
        secondaryDB =
                env.openSecondaryDatabase(
                        null, this.dbName = db.getDatabaseName() + "_superClass",
                        db, secConfig);
    }

    @Override
    public void close(Transaction txn)
    {
        secondaryDB.close();
    }

    @Override
    public boolean match(String keyword)
    {
        return keyword.equalsIgnoreCase("superClass");
    }

    @Override
    public SecondaryDatabase database()
    {
        return this.secondaryDB;
    }

    private class ParentKeyCreator implements SecondaryKeyCreator
    {
        private final TupleBinding<String> parentBinding;
        private final SerialBinding<ManagementObject> dataBinding;

        public ParentKeyCreator(
                TupleBinding<String> parentBinding, SerialBinding<ManagementObject> dataBinding)
        {
            this.parentBinding = parentBinding;
            this.dataBinding = dataBinding;
        }

        @Override
        public boolean createSecondaryKey(
                SecondaryDatabase secondary, DatabaseEntry key, DatabaseEntry data, DatabaseEntry result)
        {
            if (key != null)
            {
                ManagementObject mo = dataBinding.entryToObject(data);
                parentBinding.objectToEntry(mo.getClass().getSuperclass().getSimpleName(), result);
                return true;
            }
            return false;
        }
    }
}
