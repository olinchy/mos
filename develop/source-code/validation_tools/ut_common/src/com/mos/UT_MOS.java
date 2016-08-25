package com.mos;

import com.fasterxml.jackson.databind.JsonNode;
import com.odb.database.ODBWalker;
import com.zte.mos.domain.*;
import com.zte.mos.exception.LockedByTransException;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.Maybe;
import com.zte.mos.message.Mo;
import com.zte.mos.message.MoMetaClass;
import com.zte.mos.persistent.Log;
import com.zte.mos.service.MOS;
import com.zte.mos.storage.RemovedMoInTrans;
import com.zte.mos.util.DistinguishedList;
import com.zte.mos.util.msg.MoActionMsg;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class UT_MOS implements MOS, ImagedSystem
{
    private String metaVersion;
    private String rootExternalDN;
    private NeIdentity identity;
    private HashMap<String, ManagementObject> moMap = new HashMap<String, ManagementObject>();
    private HashMap<Integer, List<ManagementObject>> nonCommitMo =
            new HashMap<Integer, List<ManagementObject>>();

    private ModelMETA meta;

    protected UUID key = null;


    @Override
    public void stop()
    {

    }

    @Override
    public String smartlinkName() {
        return null;
    }

    @Override
    public String getRootExternalDN()
    {
        return rootExternalDN;
    }


    @Override
    public ManagementObject getMO(String dn, Maybe<Integer> transactionID) throws MOSException
    {
        if (this.moMap.containsKey(dn))
        {
            return this.moMap.get(dn);
        }
        throw new MOSException("unexisted mo");
    }

    @Override
    public ModelMETA getModelMETA()
    {
        return meta;
    }

    public void setModelMeta(ModelMETA meta)
    {
        this.meta = meta;
    }

    @Override
    public ImagedSystem switchTo(ModelMETA meta) {
        return null;
    }

    @Override
    public void hangUp()
    {

    }

    @Override
    public void resume()
    {

    }


    @Override
    public List<ManagementObject> find(
            String filterExpression, ManagementObject mo, Maybe<Integer> transactionID) throws MOSException
    {
        return null;
    }

    @Override
    public void all(ODBWalker walker, Maybe<Integer> transId) throws MOSException
    {

    }

    @Override
    public int startTransaction() throws MOSException
    {
        return 0;
    }

    @Override
    public int generateTransId()
    {
        return 0;
    }

    @Override
    public void bindTransaction(int masterTrans, int slaveTrans) throws MOSException
    {

    }

    @Override
    public int getBindTransaction(int masterTrans) throws MOSException
    {
        return 0;
    }

    @Override
    public void clearBindTransaction(int masterTrans) throws MOSException
    {

    }

    @Override
    public long createMoInTransaction(ManagementObject mo, Maybe<Integer> transactionID) throws MOSException
    {
        return 0;
    }

    @Override
    public RemovedMoInTrans deleteMoInTransaction(String dn, Maybe<Integer> transactionID) throws MOSException
    {
        return null;
    }

    @Override
    public long updateMoInTransaction(ManagementObject mo, Maybe<Integer> transactionID) throws MOSException
    {
        return 0;
    }

    @Override
    public void rollback(Maybe<Integer> transactionID, long operId) throws MOSException
    {

    }

    @Override
    public void commit(Maybe<Integer> transactionID, long operId) throws MOSException
    {

    }

    @Override
    public void createMO(ManagementObject mo, Maybe<Integer> transactionID) throws MOSException
    {
        this.moMap.put(mo.dn().toString(), mo);
    }

    @Override
    public void recover(ManagementObject mo) throws MOSException
    {
        this.createMO(mo, new Maybe<Integer>(null));
    }

    @Override
    public ManagementObject deleteMO(String dn, Maybe<Integer> transactionID) throws MOSException
    {
        return null;
    }

    @Override
    public void updateMO(ManagementObject mo, Mo delta, Maybe<Integer> transactionID) throws MOSException
    {

    }

    @Override
    public LinkedList<Log<ManagementObject>> commit(Maybe<Integer> transactionID) throws MOSException
    {
        return null;
    }

    @Override
    public void rollback(Maybe<Integer> transactionID) throws MOSException
    {

    }

    @Override
    public JsonNode act(MoActionMsg msg) throws MOSException
    {
        return null;
    }

    @Override
    public DistinguishedList<String> getAffectedDN(Maybe<Integer> transactionID) throws MOSException
    {
        return null;
    }

    @Override
    public ManagementObject delete(ManagementObject mo, Maybe<Integer> transId) throws MOSException
    {
        return null;
    }

    @Override
    public void clear(String dn) throws MOSException
    {

    }

    @Override
    public MoMetaClass getMeta(String name) throws MOSException
    {
        return null;
    }

    @Override
    public AfterGetter<ManagementObject, Mo> getAfterGetter(
            Class<? extends ManagementObject> clazz) throws MOSException
    {
        return null;
    }

    @Override
    public void deref(String to, DN from, Maybe<Integer> transactionID) throws MOSException
    {

    }

    @Override
    public void ref(String to, DN from, Maybe<Integer> transactionID) throws MOSException
    {

    }

    @Override
    public int revision()
    {
        return 0;
    }

    @Override
    public DataState getDataState() {
        return null;
    }

    @Override
    public void setDataState(DataState state)
    {

    }

    @Override
    public UUID lock() throws LockedByTransException
    {
        if (key != null)
        {
            throw new LockedByTransException(this.getRootExternalDN() + "has locked by " + this.key);
        }
        return key = UUID.randomUUID();
    }

    @Override
    public void unlock(UUID key)
    {
        if (this.key.equals(key))
        {
            this.key = null;
        }
    }

    @Override
    public boolean isLocked() {
        return false;
    }

    @Override
    public boolean sync(SysSnapSpot sysData, UUID key) throws MOSException
    {
        return true;
    }

    @Override
    public void syncDiff(Diff diff) throws MOSException {

    }

}
