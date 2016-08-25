package com.zte.mos.storage.triggers;

import com.sleepycat.bind.serial.SerialBinding;
import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.je.*;
import com.zte.mos.domain.ManagementObject;

/**
 * Created by olinchy on 15-12-10.
 */
public class ClassNameKey extends MoTypeKey
{
    public ClassNameKey(Environment env, Database db, SerialBinding<ManagementObject> dataBinding)
    {
        super(env, db, dataBinding);
    }

    private TupleBinding<String> classNameBinding;

    @Override
    public void create()
    {
        classNameBinding =
                TupleBinding.getPrimitiveBinding(String.class);

        /*
         * Open a secondary database to allow accessing the primary
         * database by the secondary key value.
         */
        SecondaryConfig secConfig = new SecondaryConfig();
//        secConfig.setTransactional(true);
        secConfig.setAllowCreate(true);
        secConfig.setSortedDuplicates(true);
        secConfig.setKeyCreator(new ClassKeyCreator(classNameBinding, dataBinding));
//        secConfig.setDeferredWrite(true);
        secondaryDB =
                env.openSecondaryDatabase(
                        null, this.dbName = db.getDatabaseName() + "_className",
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
        return keyword.equalsIgnoreCase("className");
    }

    @Override
    public SecondaryDatabase database()
    {
        return this.secondaryDB;
    }

    private class ClassKeyCreator implements SecondaryKeyCreator
    {
        private final TupleBinding<String> classNameBinding;
        private final SerialBinding<ManagementObject> dataBinding;

        public ClassKeyCreator(
                TupleBinding<String> classNameBinding, SerialBinding<ManagementObject> dataBinding)
        {
            this.classNameBinding = classNameBinding;
            this.dataBinding = dataBinding;
        }

        @Override
        public boolean createSecondaryKey(
                SecondaryDatabase secondary, DatabaseEntry key, DatabaseEntry data, DatabaseEntry result)
        {
            if (key != null)
            {
                ManagementObject mo = dataBinding.entryToObject(data);
                classNameBinding.objectToEntry(mo.getClass().getSimpleName(), result);
                return true;
            }
            return false;
        }
    }
}
