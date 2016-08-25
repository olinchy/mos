package com.zte.mos.storage;

import com.odb.database.ODBWalker;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;
import com.zte.mos.persistent.Log;
import com.zte.mos.tools.Expression;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by root on 14-12-2.
 */
public interface DbOperation<K, V>
{

    void create(V e) throws MOSException;

    V remove(K key) throws MOSException;

    void update(V e) throws MOSException;

    V get(K key) throws MOSException;

    void delete(V e) throws MOSException;

    List<Log<ManagementObject>> commit() throws MOSException;

    void rollback() throws Exception;

    List<V> find(Expression expression, V v) throws MOSException;

    void all(ODBWalker<V> odbWalker) throws MOSException;

    long createMoInTransaction(V e) throws MOSException;

    RemovedMoInTrans removeMoInTransaction(K key) throws MOSException;

    long updateMoInTransaction(V e) throws MOSException;

    void commit(long operTransId) throws MOSException;

    void rollback(long operTransId) throws MOSException;

}
