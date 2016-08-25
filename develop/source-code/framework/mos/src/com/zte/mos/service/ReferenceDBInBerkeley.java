package com.zte.mos.service;

import com.odb.database.BerkeleyDB;
import com.odb.database.BerkeleyDBDPLIndexer;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.SecondaryIndex;
import com.zte.mos.domain.DN;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.BerkeleyDBException;
import com.zte.mos.exception.MOSException;
import com.zte.mos.exception.NoTransactionIsNotAllowedInReferenceDB;
import com.zte.mos.inf.Maybe;
import com.zte.mos.persistent.Log;

import java.util.HashMap;
import java.util.List;

/**
 * Created by olinchy on 16-2-14.
 */
public class ReferenceDBInBerkeley extends BerkeleyDBDPLIndexer<String, ReferenceObject>
{
    static HashMap<String, ReferenceDBInBerkeley> map = new HashMap<String, ReferenceDBInBerkeley>();
    private BerkeleyDB<String, ReferenceObject> db;

    private ReferenceDBInBerkeley(String mosName)
    {
        this.mosName = mosName;
        db = new BerkeleyDB<String, ReferenceObject>(this);
    }

    private String mosName;
    private HashMap<Maybe<Integer>, ReferenceTrans> mapTrans = new HashMap<Maybe<Integer>, ReferenceTrans>();

    public static ReferenceDBInBerkeley get(MOS mos)
    {
        return init(mos.getRootExternalDN());
    }

    public static void stop(MOS mos)
    {
        ReferenceDBInBerkeley db = get(mos);
        if (db != null)
        {
            db.stop();
            map.remove(mos.getRootExternalDN());
        }
    }

    public static ReferenceDBInBerkeley hangUp(MOS mos)
    {
        ReferenceDBInBerkeley db = map.get(mos.getRootExternalDN());
        db.rename(db.getDatabase().getDatabaseName() + "_bak");
        map.remove(mos.getRootExternalDN());
        map.put(mos.getRootExternalDN() + "_bak", db);
        return db;
    }

    public static void restore(MOS mos)
    {
        ReferenceDBInBerkeley referenceDBInBerkeley = map.remove(mos.getRootExternalDN() + "_bak");
        if (referenceDBInBerkeley != null)
        {
            String dbName = referenceDBInBerkeley.getDatabase().getDatabaseName();
            referenceDBInBerkeley.rename(dbName.replace("_bak", ""));
            map.put(mos.getRootExternalDN(), referenceDBInBerkeley);
        }
    }

    public static void halt(MOS mos)
    {
        ReferenceDBInBerkeley referenceDBInBerkeley = map.remove(mos.getRootExternalDN() + "_bak");
        if (referenceDBInBerkeley != null)
        {
            referenceDBInBerkeley.stop();
        }
    }


    public static ReferenceDBInBerkeley init(String mosdn)
    {
        ReferenceDBInBerkeley db = map.get(mosdn);
        if (db == null)
        {
            db = new ReferenceDBInBerkeley(mosdn);
            map.put(mosdn, db);
        }
        return db;
    }

    public ReferenceObject get(String dn, Maybe<Integer> transId) throws NoTransactionIsNotAllowedInReferenceDB
    {
        if (transId.isPresent())
        {
            ReferenceTrans trans = this.getTrans(transId);
            ReferenceObject obj = trans.get(this, dn);
            if (obj == null)
            {
                return this.get(dn);
            }
            return obj;
        }
        else
            return this.get(dn);
    }

    public void deRef(String dn, DN from, Maybe<Integer> transactionID) throws MOSException
    {
        ReferenceTrans trans = getTrans(transactionID);
        trans.deRef(dn, from);
    }

    private ReferenceTrans getTrans(Maybe<Integer> transactionID) throws NoTransactionIsNotAllowedInReferenceDB
    {
        if (transactionID.nothing())
        {
            throw new NoTransactionIsNotAllowedInReferenceDB();
        }
        ReferenceTrans trans = mapTrans.get(transactionID);
        if (trans == null)
        {
            mapTrans.put(transactionID, trans = new ReferenceTrans());
        }
        return trans;
    }

    public void ref(String dn, DN from, Maybe<Integer> transactionID) throws MOSException
    {
        ReferenceTrans trans = getTrans(transactionID);
        trans.ref(dn, from);
    }

    public void commit(Maybe<Integer> transactionID) throws MOSException
    {
        ReferenceTrans trans = popTrans(transactionID);
        if (trans != null)
            trans.commit(this);
    }

    private ReferenceTrans popTrans(Maybe<Integer> transactionID)
    {
        return mapTrans.remove(transactionID);
    }

    @Override
    protected String dbName()
    {
        return "reference_of_mo" + mosName;
    }

    @Override
    protected PrimaryIndex<String, ReferenceObject> createPrimaryIndex()
    {
        return store.getPrimaryIndex(String.class, ReferenceObject.class);
    }

    @Override
    protected HashMap<String, SecondaryIndex> createSecondIndexes()
    {
        return null;
    }

    public void rollback(Maybe<Integer> transactionID)
    {
        popTrans(transactionID);
    }

    public void refresh(List<Log<ManagementObject>> logs)
    {
        for (Log<ManagementObject> log : logs)
        {
            if (log.type().equals(Log.LogType.Delete.name()))
            {
                try
                {
                    this.remove(log.oldValue().dn().toString());
                }
                catch (BerkeleyDBException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
