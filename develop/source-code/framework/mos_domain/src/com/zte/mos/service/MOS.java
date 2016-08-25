package com.zte.mos.service;

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
import com.zte.mos.storage.RemovedMoInTrans;
import com.zte.mos.util.DistinguishedList;
import com.zte.mos.util.msg.MoActionMsg;

import java.util.List;

/**
 * Created by root on 14-12-1.
 * MOS interface
 */
public interface MOS
{
    String ROOT_INTERNAL_DN = "/";
    Maybe<Integer> SYNC_TRANS_ID = new Maybe<Integer>(-1);
    Maybe<Integer> SWITCH_MODEL_TRANS_ID = new Maybe<Integer>(-2);

    void stop() throws MOSException;

    String smartlinkName();

    String getRootExternalDN();

    ManagementObject getMO(String dn, Maybe<Integer> transactionID) throws MOSException;

    List<ManagementObject> find(
            String filterExpression, ManagementObject mo,
            Maybe<Integer> transactionID) throws MOSException;

    void all(ODBWalker walker, Maybe<Integer> transId)
            throws MOSException;

    int startTransaction() throws MOSException;

    int generateTransId();

    void bindTransaction(int masterTrans, int slaveTrans) throws MOSException;

    int getBindTransaction(int masterTrans) throws MOSException;

    void clearBindTransaction(int masterTrans) throws MOSException;

    long createMoInTransaction(
            ManagementObject mo,
            Maybe<Integer> transactionID) throws MOSException;

    RemovedMoInTrans deleteMoInTransaction(
            String dn,
            Maybe<Integer> transactionID) throws MOSException;

    long updateMoInTransaction(
            ManagementObject mo,
            Maybe<Integer> transactionID) throws MOSException;

    void rollback(Maybe<Integer> transactionID, long operId) throws MOSException;

    void commit(Maybe<Integer> transactionID, long operId) throws MOSException;

    void createMO(ManagementObject mo, Maybe<Integer> transactionID) throws MOSException;

    void recover(ManagementObject mo) throws MOSException;

    ManagementObject deleteMO(String dn, Maybe<Integer> transactionID) throws MOSException;

    void updateMO(ManagementObject mo, Mo delta, Maybe<Integer> transactionID) throws MOSException;

    List<Log<ManagementObject>> commit(Maybe<Integer> transactionID) throws MOSException;

    void rollback(Maybe<Integer> transactionID) throws MOSException;

    JsonNode act(MoActionMsg msg) throws MOSException;

    DistinguishedList<String> getAffectedDN(Maybe<Integer> transactionID) throws MOSException;

    ManagementObject delete(ManagementObject mo, Maybe<Integer> transId) throws MOSException;

    void clear(String dn) throws MOSException;

    MoMetaClass getMeta(String name) throws MOSException;

    AfterGetter<ManagementObject, Mo> getAfterGetter(Class<? extends ManagementObject> clazz) throws MOSException;

    void deref(String to, DN from, Maybe<Integer> transactionID) throws MOSException;

    void ref(String to, DN from, Maybe<Integer> transactionID) throws MOSException;
}
