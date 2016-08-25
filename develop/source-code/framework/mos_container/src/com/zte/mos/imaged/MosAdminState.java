package com.zte.mos.imaged;

import com.fasterxml.jackson.databind.JsonNode;
import com.odb.database.ODBWalker;
import com.zte.mos.domain.AdminState;
import com.zte.mos.domain.AfterGetter;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.Maybe;
import com.zte.mos.message.Mo;
import com.zte.mos.service.MOS;
import com.zte.mos.storage.RemovedMoInTrans;
import com.zte.mos.util.DistinguishedList;
import com.zte.mos.util.msg.MoActionMsg;

/**
 * Created by root on 14-11-20.
 */
public abstract class MosAdminState implements MOS
{
    protected ImagedMos stateMos;
    private long startRunTime = Long.MAX_VALUE;

    //public abstract void nextState();

    protected MosAdminState(ImagedMos mos)
    {
        this.stateMos = mos;
    }

    public void setStartRunTime()
    {
        this.startRunTime = System.currentTimeMillis();
    }

    public long getStartRunTime()
    {
        return startRunTime;
    }

    @Override
    public String smartlinkName()
    {
        return stateMos.mos.smartlinkName();
    }

    @Override
    public String toString()
    {
        return this.getClass().getSimpleName();
    }

    public abstract AdminState getAdminState();

    @Override
    public int generateTransId()
    {
        return stateMos.mos.generateTransId();
    }

    @Override
    public JsonNode act(MoActionMsg msg) throws MOSException
    {
        return stateMos.mos.act(msg);
    }

    @Override
    public void all(
            ODBWalker iterator,
            Maybe<Integer> transId) throws MOSException
    {
        stateMos.mos.all(iterator, transId);
    }

    @Override
    public int getBindTransaction(int masterTrans) throws MOSException
    {
        return stateMos.mos.getBindTransaction(masterTrans);
    }

    @Override
    public void clearBindTransaction(int masterTrans) throws MOSException
    {
        this.stateMos.mos.clearBindTransaction(masterTrans);
    }

    @Override
    public void recover(ManagementObject mo) throws MOSException
    {
        this.stateMos.mos.recover(mo);
    }

    @Override
    public void clear(String dn) throws MOSException
    {
        stateMos.mos.clear(dn);
    }

    @Override
    public void bindTransaction(int masterTrans, int slaveTrans) throws MOSException
    {
        stateMos.mos.bindTransaction(masterTrans, slaveTrans);
    }

    @Override
    public long createMoInTransaction(
            ManagementObject mo,
            Maybe<Integer> transactionID) throws MOSException
    {
        throw new MOSException();
    }

    @Override
    public RemovedMoInTrans deleteMoInTransaction(
            String dn,
            Maybe<Integer> transactionID) throws MOSException
    {
        throw new MOSException();
    }

    @Override
    public long updateMoInTransaction(
            ManagementObject mo,
            Maybe<Integer> transactionID) throws MOSException
    {
        throw new MOSException();
    }

    @Override
    public void rollback(Maybe<Integer> transactionID, long operId) throws MOSException
    {
        throw new MOSException();
    }

    @Override
    public void commit(Maybe<Integer> transactionID, long operId) throws MOSException
    {
        throw new MOSException();
    }

    @Override
    public DistinguishedList<String> getAffectedDN(Maybe<Integer> transactionID) throws MOSException
    {
        return stateMos.mos.getAffectedDN(transactionID);
    }

    @Override
    public AfterGetter<ManagementObject, Mo> getAfterGetter(
            Class<? extends ManagementObject> clazz) throws MOSException
    {
        return stateMos.mos.getAfterGetter(clazz);
    }
}
