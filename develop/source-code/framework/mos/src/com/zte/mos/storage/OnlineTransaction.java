package com.zte.mos.storage;

import com.odb.database.Odb;
import com.zte.mos.domain.DN;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;
import com.zte.mos.persistent.Log;

import java.util.List;

/**
 * Created by luoqingkai on 15-3-23.
 */
public class OnlineTransaction extends MosTransaction
{
    public OnlineTransaction(int transID, Odb<ManagementObject> odb, Revision revision)
    {
        super(transID, odb, revision);
    }

    @Override
    public List<Log<ManagementObject>> commit() throws MOSException
    {
        return odb.commit();
    }

    @Override
    public long createMoInTransaction(ManagementObject mo) throws MOSException
    {
        super.create(mo);
        return 0;
    }

    @Override
    public RemovedMoInTrans removeMoInTransaction(DN key) throws MOSException
    {
        ManagementObject mo = super.remove(key);
        return new RemovedMoInTrans(mo, 0);
    }

    @Override
    public long updateMoInTransaction(ManagementObject mo) throws MOSException
    {
        super.update(mo);
        return 0;
    }

    @Override
    public void commit(long operTransId) throws MOSException
    {
        super.commit(operTransId);
    }

    @Override
    public void rollback(long operTransId) throws MOSException
    {
        super.rollback(operTransId);
    }
}
