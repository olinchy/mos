package com.odb.database;

import com.odb.database.extended_odb.OperatingSet;
import com.odb.database.trigger.Trigger;
import com.odb.database.trigger.TriggerTiming;
import com.zte.mos.domain.Group;
import com.zte.mos.exception.MOSException;
import com.zte.mos.persistent.Log;

import java.util.List;

/**
 * Created by l10121897 on 5/14/2014.
 */
public interface Odb<T extends Group>
{
    int MAX_TRANS_COUNT = 256;

    Odb<T> startTransaction() throws MOSException;

    Odb<T> startEmptyTransaction() throws MOSException;

    void add(T t) throws MOSException;

    void set(T t) throws MOSException;

    void remove(Key key) throws MOSException;

    T get(Key key) throws MOSException;

    void remove(T t) throws MOSException;

    List<Log<T>> commit() throws MOSException;

    void rollback() throws MOSException;

    void all(ODBWalker<T> walker) throws MOSException;

    void clear(T t) throws MOSException;

    void recover(T t) throws MOSException;

    boolean parentOf(Odb<T> that);

    void acquire(T data, Odb<T> odb, OperatingSet.OperationSection relation) throws MOSException;

    void release(T data, Odb<T> odb) throws MOSException;

    void setChildren(T t) throws MOSException;

    void regGlobalTrigger(TriggerTiming timming, Trigger trigger);

    void regObjTrigger(Class<?> objClass, TriggerTiming timming, Trigger<T> trigger);

    void clearTrigger();

    void removeChild(T parent, T child) throws MOSException;

    void addChild(T parent, T child) throws MOSException;

    void stop();

    void clearAll();

    void renameWithPostfix(String postfix);

    void delPostfix(String postfix);

    Key key(T t) throws MOSException;
}
