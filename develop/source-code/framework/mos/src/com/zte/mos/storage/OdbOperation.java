package com.zte.mos.storage;

import com.odb.database.*;
import com.sleepycat.je.*;
import com.zte.mos.domain.DN;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;
import com.zte.mos.exception.NullMoException;
import com.zte.mos.persistent.Log;
import com.zte.mos.storage.triggers.MoIndexer;
import com.zte.mos.tools.Expression;
import com.zte.mos.util.CloneHelper;
import com.zte.mos.util.DistinguishedList;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by luoqingkai on 14-12-2.
 * super class for Triggerable DB and Transaction class.
 */
public abstract class OdbOperation implements DbOperation<DN, ManagementObject>
{
    protected static MoIndexer primaryIndex;
    protected Odb<ManagementObject> odb;
    protected Integer slaveTransId = Integer.MIN_VALUE;

    protected OdbOperation()
    {
    }

    public void bindTransaction(int slave)
    {
        slaveTransId = slave;
    }

    public int getBindTransaction(int master)
    {
        return slaveTransId.intValue();
    }

    public void clearBindTransaction(int master)
    {
        this.slaveTransId = Integer.MIN_VALUE;
    }

    @Override
    public void create(ManagementObject mo) throws MOSException
    {
        odb.add(mo);
    }

    @Override
    public ManagementObject remove(DN key) throws MOSException
    {
        ManagementObject mo = this.get(key);
        if (mo == null)
        {
            throw new NullMoException("mo " + key.toString() + " is not exist!");
        }
        odb.remove(mo);
        return mo;
    }

    @Override
    public void update(ManagementObject mo) throws MOSException
    {
        odb.set(mo);
    }

    @Override
    public ManagementObject get(DN key) throws MOSException
    {
        return odb.get(primaryIndex.key(key));
    }

    @Override
    public void delete(ManagementObject mo) throws MOSException
    {
        odb.remove(mo);
    }

    @Override
    public List<Log<ManagementObject>> commit() throws MOSException
    {
        return odb.commit();
    }

    @Override
    public void rollback() throws MOSException
    {
        odb.rollback();
    }

    @Override
    public List<ManagementObject> find(final Expression expression, final ManagementObject local) throws MOSException
    {
        final List<ManagementObject> list = new LinkedList<ManagementObject>();
        Walker<Key, ManagementObject> odbWalker = new Walker<Key, ManagementObject>()
        {
            @Override
            public void walk(BerkeleyDBIndexer<Key, ManagementObject> indexer) throws MOSException
            {
                BerkeleyDBObjectIndexer<Key, ManagementObject> moIndexer = (BerkeleyDBObjectIndexer<Key, ManagementObject>) indexer;
                Cursor cursor = null;
                CursorConfig config = new CursorConfig();
                config.setReadUncommitted(true);
                try
                {
                    cursor = moIndexer.getDatabase().openCursor(null, config);
                    DatabaseEntry keyEntry = new DatabaseEntry();
                    DatabaseEntry dataEntry = new DatabaseEntry();
                    while (OperationStatus.SUCCESS.equals(cursor.getNext(keyEntry, dataEntry, LockMode.DEFAULT)))
                    {
                        ManagementObject mo = moIndexer.entryToObject(dataEntry);
                        if (expression.evaluate(mo.toMoClass().setDn(mo.dn()), local))
                        {
                            list.add(CloneHelper.clone(mo));
                        }
                    }
                }
                finally
                {
                    if (cursor != null)
                    {
                        cursor.close();
                    }
                }
            }
        };
        odbWalker.walk(primaryIndex);
        return list;
    }

    public DistinguishedList<String> getAffectedDN()
    {
        return null;
    }

    public abstract RemovedMoInTrans removeMoInTransaction(ManagementObject mo) throws MOSException;

    @Override
    public void all(ODBWalker<ManagementObject> odbWalker) throws MOSException
    {
        this.odb.all(odbWalker);
    }
}
