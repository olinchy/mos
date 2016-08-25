package com.zte.mos.ems;

import com.fasterxml.jackson.databind.JsonNode;
import com.odb.database.ODBWalker;
import com.zte.mos.domain.AfterGetter;
import com.zte.mos.domain.DN;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.Maybe;
import com.zte.mos.message.Mo;
import com.zte.mos.message.MoMetaClass;
import com.zte.mos.persistent.Log;
import com.zte.mos.service.MOS;
import com.zte.mos.storage.RemovedMoInTrans;
import com.zte.mos.util.DistinguishedList;
import com.zte.mos.util.msg.MoActionMsg;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by luoqingkai on 15-8-3.
 */
public class UtMos4RegBundle implements MOS {
    @Override
    public void stop() throws MOSException {

    }

    @Override
    public String smartlinkName() {
        return null;
    }


    @Override
    public String getRootExternalDN() {
        return null;
    }


    @Override
    public ManagementObject getMO(String dn, Maybe<Integer> transactionID) throws MOSException {
        return null;
    }

    @Override
    public List<ManagementObject> find(String filterExpression, ManagementObject mo, Maybe<Integer> transactionID) throws MOSException {
        return null;
    }

    @Override
    public void all(ODBWalker walker, Maybe<Integer> transId) throws MOSException {

    }

    @Override
    public int startTransaction() throws MOSException {
        return 0;
    }

    @Override
    public int generateTransId() {
        return 0;
    }

    @Override
    public void bindTransaction(int masterTrans, int slaveTrans) throws MOSException {

    }

    @Override
    public int getBindTransaction(int masterTrans) throws MOSException {
        return 0;
    }

    @Override
    public void clearBindTransaction(int masterTrans) throws MOSException {

    }

    @Override
    public long createMoInTransaction(ManagementObject mo, Maybe<Integer> transactionID) throws MOSException {
        return 0;
    }

    @Override
    public RemovedMoInTrans deleteMoInTransaction(String dn, Maybe<Integer> transactionID) throws MOSException {
        return null;
    }

    @Override
    public long updateMoInTransaction(ManagementObject mo, Maybe<Integer> transactionID) throws MOSException {
        return 0;
    }

    @Override
    public void rollback(Maybe<Integer> transactionID, long operId) throws MOSException {

    }

    @Override
    public void commit(Maybe<Integer> transactionID, long operId) throws MOSException {

    }

    @Override
    public void createMO(ManagementObject mo, Maybe<Integer> transactionID) throws MOSException {

    }

    @Override
    public void recover(ManagementObject mo) throws MOSException
    {

    }

    @Override
    public ManagementObject deleteMO(String dn, Maybe<Integer> transactionID) throws MOSException {
        return null;
    }

    @Override
    public void updateMO(ManagementObject mo, Mo delta, Maybe<Integer> transactionID) throws MOSException {

    }

    @Override
    public LinkedList<Log<ManagementObject>> commit(Maybe<Integer> transactionID) throws MOSException {
        return null;
    }

    @Override
    public void rollback(Maybe<Integer> transactionID) throws MOSException {

    }

    @Override
    public JsonNode act(MoActionMsg msg) throws MOSException {
        return null;
    }

    @Override
    public DistinguishedList<String> getAffectedDN(Maybe<Integer> transactionID) throws MOSException {
        return null;
    }

    @Override
    public ManagementObject delete(ManagementObject mo, Maybe<Integer> transId) throws MOSException {
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
}
