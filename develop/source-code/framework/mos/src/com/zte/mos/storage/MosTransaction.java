package com.zte.mos.storage;

import com.odb.database.Odb;
import com.odb.database.extended_odb.TransactionODB;
import com.zte.mos.domain.DN;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;
import com.zte.mos.exception.NullMoException;
import com.zte.mos.inf.Maybe;
import com.zte.mos.persistent.Log;
import com.zte.mos.service.MOS;
import com.zte.mos.service.cmd.Ref;
import com.zte.mos.util.DistinguishedList;
import com.zte.mos.util.basic.Logger;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by root on 14-12-1.
 * Transaction created by Triggerable DB
 */
public class MosTransaction extends OdbOperation
{
    private static final Logger logger = logger(MosTransaction.class);
    public final int transactionID;
    protected final Revision revision;
    private final MOS parent;
    private long operId = 0;
    private DistinguishedList<String> affectedDN = new DistinguishedList<String>();
    private Map<Long, Odb<ManagementObject>> innerTransMap = new ConcurrentHashMap<Long, Odb<ManagementObject>>();

    public MosTransaction(int transID, Odb<ManagementObject> odb, Revision revision)
    {
        this(transID, odb, revision, null);
    }

    public MosTransaction(int transID, Odb<ManagementObject> odb, Revision revision, MOS parent)
    {
        super();
        this.odb = odb;
        this.transactionID = transID;
        this.revision = revision;
        this.parent = parent;
    }

    @Override
    public void create(ManagementObject mo) throws MOSException
    {
        Odb<ManagementObject> innerTransaction = newTransaction();
        try
        {
            innerTransaction.add(mo);
            ref(innerTransaction.commit());
        }
        catch (Exception e)
        {
            innerTransaction.rollback();
            throw new MOSException(e);
        }
        //this.odb.add(mo);
    }

    private Odb<ManagementObject> newTransaction() throws MOSException
    {
        Odb<ManagementObject> odbTransaction = odb.startTransaction();
        //((ExtendedODB<ManagementObject>)odbTransaction).setCommitListener(null);
        return odbTransaction;
    }

    @Override
    public void update(ManagementObject mo) throws MOSException
    {
        Odb<ManagementObject> innerTransaction = newTransaction();
        try
        {
            innerTransaction.set(mo);
            ref(innerTransaction.commit());
        }
        catch (MOSException e)
        {
            innerTransaction.rollback();
            throw e;
        }
    }

    private void ref(List<Log<ManagementObject>> logs) throws MOSException
    {
        for (Log<ManagementObject> log : logs)
        {
            Ref.by(log.type()).ref(affectedDN, parent, log, getTransId());
        }
    }

    private Maybe<Integer> getTransId()
    {
        return new Maybe<Integer>(this.transactionID);
    }

    @Override
    public ManagementObject remove(DN key) throws MOSException
    {
        Odb<ManagementObject> innerTransaction = newTransaction();
        try
        {
            ManagementObject mo = innerTransaction.get(primaryIndex.key(key));
            if (mo == null)
            {
                logger.warn("mo " + key.toString() + " is not exist!");
                innerTransaction.rollback();
                return mo;
            }
            mo.setMos(parent);
            innerTransaction.remove(mo);
            ref(innerTransaction.commit());
            return mo;
        }
        catch (Exception e)
        {
            innerTransaction.rollback();
            throw new MOSException(e);
        }
    }

    @Override
    public void delete(ManagementObject mo) throws MOSException
    {
        Odb<ManagementObject> innerTransaction = newTransaction();
        try
        {
            innerTransaction.remove(mo);
            ref(innerTransaction.commit());
        }
        catch (Exception e)
        {
            innerTransaction.rollback();
            throw new MOSException(e);
        }
    }

    @Override
    public long createMoInTransaction(ManagementObject mo) throws MOSException
    {
        Odb<ManagementObject> innerTransaction = newTransaction();
        try
        {
            innerTransaction.add(mo);
            return keepInnerTrans(innerTransaction);
        }
        catch (Exception e)
        {
            logger.warn("", e);
            innerTransaction.rollback();
            throw new MOSException(e);
        }
    }

    @Override
    public RemovedMoInTrans removeMoInTransaction(DN key) throws MOSException
    {
        Odb<ManagementObject> innerTransaction = newTransaction();
        ManagementObject mo;
        try
        {
            mo = innerTransaction.get(primaryIndex.key(key));
            if (mo != null)
            {
                innerTransaction.remove(mo);
                return new RemovedMoInTrans(mo, keepInnerTrans(innerTransaction));
            }
            else
            {
                throw new NullMoException("mo " + key.toString() + " is not exist!");
            }
        }
        catch (Exception e)
        {
            logger.warn("", e);
            innerTransaction.rollback();
            throw new MOSException(e);
        }
    }

    private long keepInnerTrans(Odb<ManagementObject> innerTransaction)
    {
        long myOperId = this.operId;
        this.innerTransMap.put(myOperId, innerTransaction);
        operId++;
        return myOperId;
    }

    @Override
    public long updateMoInTransaction(ManagementObject mo) throws MOSException
    {
        Odb<ManagementObject> innerTransaction = newTransaction();
        try
        {
            innerTransaction.set(mo);
            return keepInnerTrans(innerTransaction);
        }
        catch (Exception e)
        {
            logger.warn("", e);
            innerTransaction.rollback();
            throw new MOSException(e);
        }
    }

    @Override
    public void commit(long operTransId) throws MOSException
    {
        Odb<ManagementObject> innerTransaction = this.innerTransMap.remove(operTransId);
        if (innerTransaction != null)
        {
            ref(innerTransaction.commit());
        }
    }

    @Override
    public void rollback(long operTransId) throws MOSException
    {
        Odb<ManagementObject> innerTransaction = this.innerTransMap.remove(operTransId);
        if (innerTransaction != null)
        {
            innerTransaction.rollback();
        }
    }

    @Override
    public List<Log<ManagementObject>> commit() throws MOSException
    {
        for (Odb<ManagementObject> innerTransaction : this.innerTransMap.values())
        {
            innerTransaction.commit();
        }
        return revision.commit(odb);
    }

    @Override
    public void rollback() throws MOSException
    {
        for (Odb<ManagementObject> innerTransaction : this.innerTransMap.values())
        {
            innerTransaction.rollback();
        }
        odb.rollback();
    }

    @Override
    public DistinguishedList<String> getAffectedDN()
    {
        return this.affectedDN;
    }

    @Override
    public RemovedMoInTrans removeMoInTransaction(ManagementObject mo) throws MOSException
    {
        Odb<ManagementObject> innerTransaction = newTransaction();
        try
        {
            if (mo != null)
            {
                innerTransaction.remove(mo);
                return new RemovedMoInTrans(mo, keepInnerTrans(innerTransaction));
            }
            else
            {
                throw new NullMoException("mo " + mo.dn().toString() + " is not exist!");
            }
        }
        catch (Exception e)
        {
            logger.warn("", e);
            innerTransaction.rollback();
            throw new MOSException(e);
        }
    }

    public Odb<ManagementObject> odb()
    {
        return odb;
    }

    public void initBusinessProcess() throws MOSException
    {
        ((TransactionODB) odb).replayObjTrigger();
    }
}
