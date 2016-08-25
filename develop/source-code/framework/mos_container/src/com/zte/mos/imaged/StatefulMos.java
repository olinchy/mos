package com.zte.mos.imaged;

import com.odb.database.ODBWalker;
import com.zte.mos.domain.AdminState;
import com.zte.mos.domain.AfterGetter;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.domain.ModelHead;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.Maybe;
import com.zte.mos.message.Mo;
import com.zte.mos.persistent.Log;
import com.zte.mos.service.MOS;
import com.zte.mos.service.RawMos;
import com.zte.mos.storage.RemovedMoInTrans;
import com.zte.mos.util.DistinguishedList;
import com.zte.mos.util.basic.Logger;

import java.util.LinkedList;
import java.util.List;

import static com.zte.mos.util.basic.Logger.logger;

public abstract class StatefulMos implements MOS
{
    private static Logger log = logger(StatefulMos.class);


    protected RawMos mos;
    protected MosAdminState adminState;

    protected StatefulMos(String rootDN, ModelHead head, String smartlinkName) throws MOSException
    {
        this.mos = new RawMos(rootDN, head, smartlinkName);
        //this.adminState = new MosIdleState(this);
    }

    protected StatefulMos(RawMos mos)
    {
        this.mos = mos;
    }

    protected abstract void initState();

    protected RawMos getRawMos()
    {
        return mos;
    }


    @Override
    public int generateTransId()
    {
        return mos.generateTransId();
    }


    public AdminState getAdminState()
    {
        return this.adminState.getAdminState();
    }

    @Override
    public void stop() throws MOSException
    {
        adminState.stop();
    }

    @Override
    public void clear(String dn) throws MOSException
    {
        adminState.clear(dn);
    }

    @Override
    public String getRootExternalDN()
    {
        return adminState.getRootExternalDN();
    }

    @Override
    public ManagementObject getMO(String dn, Maybe<Integer> transactionID) throws MOSException
    {
        return adminState.getMO(dn, transactionID);
    }

    @Override
    public List<ManagementObject> find(
            String filterExpression, ManagementObject mo,
            Maybe<Integer> transactionID) throws MOSException
    {
        return adminState.find(filterExpression, mo, transactionID);
    }

    @Override
    public int startTransaction() throws MOSException
    {
        return adminState.startTransaction();
    }

    @Override
    public void bindTransaction(int masterTrans, int slaveTrans) throws MOSException
    {
        this.mos.bindTransaction(masterTrans, slaveTrans);
    }

    @Override
    public int getBindTransaction(int masterTrans) throws MOSException
    {
        return this.mos.getBindTransaction(masterTrans);
    }

    @Override
    public void clearBindTransaction(int masterTrans) throws MOSException
    {
        this.mos.clearBindTransaction(masterTrans);
    }

    @Override
    public void createMO(ManagementObject mo, Maybe<Integer> transactionID) throws MOSException
    {
        adminState.createMO(mo, transactionID);
    }

    @Override
    public void recover(ManagementObject mo) throws MOSException
    {
        adminState.recover(mo);
    }

    @Override
    public ManagementObject deleteMO(String dn, Maybe<Integer> transactionID) throws MOSException
    {
        return adminState.deleteMO(dn, transactionID);
    }

    @Override
    public void updateMO(ManagementObject mo, Mo delta, Maybe<Integer> transactionID) throws MOSException
    {
        adminState.updateMO(mo, delta, transactionID);
    }

    @Override
    public List<Log<ManagementObject>> commit(Maybe<Integer> transactionID) throws MOSException
    {
        return adminState.commit(transactionID);
    }

    @Override
    public void rollback(Maybe<Integer> transactionID) throws MOSException
    {
        adminState.rollback(transactionID);
    }

    @Override
    public void all(
            ODBWalker iterator,
            Maybe<Integer> transId) throws MOSException
    {
        adminState.all(iterator, transId);
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
        return mos.getAffectedDN(transactionID);
    }

    @Override
    public String smartlinkName()
    {
        return mos.smartlinkName();
    }

    @Override
    public AfterGetter<ManagementObject, Mo> getAfterGetter(
            Class<? extends ManagementObject> clazz) throws MOSException
    {
        return mos.getAfterGetter(clazz);
    }
}
