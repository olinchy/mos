package com.mos.lite;

import com.mos.lite.store.MoIndexer;
import com.mos.lite.store.TriggerRegister;
import com.odb.database.Key;
import com.odb.database.ODBWalker;
import com.odb.database.Odb;
import com.odb.database.extended_odb.CommittedODB;
import com.odb.database.extended_odb.OperatingSet;
import com.zte.mos.domain.DN;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.domain.MetaStringStore;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.Maybe;
import com.zte.mos.message.MoMetaClass;
import com.zte.mos.persistent.Log;
import com.zte.mos.service.MOSAdaptor;
import com.zte.mos.util.Singleton;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by olinchy on 15-11-18.
 */
public class Mos extends MOSAdaptor
{
    public Mos(String rootClassName, String modelType, String modelVersion) throws Exception
    {
        this.modelType = modelType;
        this.modelVersion = modelVersion;
        MoIndexer primaryIndex = new MoIndexer("/");
        OperatingSet<ManagementObject> operatingSet = new OperatingSet<ManagementObject>(primaryIndex);
        odb = new CommittedODB<ManagementObject>(primaryIndex, operatingSet);

        new TriggerRegister().register(odb);

        autoCreateRoot(Singleton.getInstance(MetaStringStore.class).getMeta(modelType, modelVersion, rootClassName));
    }

    private static Mos self;
    private final String modelType;
    private final String modelVersion;

    private void autoCreateRoot(MoMetaClass meta) throws MOSException
    {
        if (this.odb.get(new Key(new DN(ROOT_INTERNAL_DN))) != null)
        {
            return;
        }

        Odb<ManagementObject> trans = null;
        try
        {
            ManagementObject root = (ManagementObject) meta.type.newInstance();
            root.setDn(ROOT_INTERNAL_DN);
            trans = odb.startTransaction();
            root.setMos(this);

            trans.add(root);
            trans.commit();
        }
        catch (InstantiationException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }

    private Odb<ManagementObject> odb;
    private ConcurrentHashMap<Integer, Odb<ManagementObject>> map = new ConcurrentHashMap<Integer, Odb<ManagementObject>>();

    public void add(final ManagementObject mo, Maybe<Integer> transId) throws MOSException
    {
        executeInTrans(new MoExecutor()
        {
            public void execute(Odb<ManagementObject> odb) throws MOSException
            {
                mo.setMos(Mos.this);
                odb.add(mo);
            }
        }, transId);
    }

    private void executeInTrans(MoExecutor moExecutor, Maybe<Integer> transId) throws MOSException
    {
        moExecutor.execute(locateTrans(transId));
    }

    private Odb<ManagementObject> locateTrans(Maybe<Integer> transId) throws MOSException
    {
        if (transId.nothing())
        {
            return this.odb;
        }
        if (map.containsKey(transId.getValue()))
        {
            return map.get(transId.getValue());
        }
        else
        {
            Odb<ManagementObject> trans = odb.startTransaction();
            map.put(transId.getValue(), trans);
            return trans;
        }
    }

    public void del(final ManagementObject mo, Maybe<Integer> transId) throws MOSException
    {
        this.del(mo.dn(), transId);
    }

    public void del(final DN dn, Maybe<Integer> transId) throws MOSException
    {
        executeInTrans(new MoExecutor()
        {
            @Override
            public void execute(Odb<ManagementObject> odb) throws MOSException
            {
                ManagementObject mo = odb.get(new Key(dn));
                mo.setMos(Mos.this);
                odb.remove(mo);
            }
        }, transId);
    }

    public void set(final ManagementObject mo, Maybe<Integer> transId) throws MOSException
    {
        executeInTrans(new MoExecutor()
        {
            @Override
            public void execute(Odb<ManagementObject> odb) throws MOSException
            {
                mo.setMos(Mos.this);
                odb.set(mo);
            }
        }, transId);
    }

    public ManagementObject get(final DN dn, Maybe<Integer> transId) throws MOSException
    {
        final ManagementObject[] toGet = new ManagementObject[1];
        executeInTrans(new MoExecutor()
        {
            @Override
            public void execute(Odb<ManagementObject> odb) throws MOSException
            {
                toGet[0] = odb.get(new Key(dn));
            }
        }, transId);
        if (toGet[0] != null)
            toGet[0].setMos(this);
        return toGet[0];
    }

    public static Mos getInstance()
    {
        return self;
    }

    public static void setInstance(Mos mos)
    {
        self = mos;
    }

    public void all(ODBWalker<ManagementObject> walker) throws MOSException
    {
        this.odb.all(walker);
    }

    public MoMetaClass getMeta(String moClass) throws MOSException
    {
        return Singleton.getInstance(MetaStringStore.class).getMeta(modelType, modelVersion, moClass);
    }

    @Override
    public LinkedList<Log<ManagementObject>> commit(
            Maybe<Integer> transactionID) throws MOSException
    {
        return (LinkedList<Log<ManagementObject>>) locateTrans(transactionID).commit();
    }

    @Override
    public void rollback(Maybe<Integer> transactionID) throws MOSException
    {
        locateTrans(transactionID).rollback();
    }
}
