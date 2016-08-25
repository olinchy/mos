package com.odb.database.extended_odb;

import com.odb.database.Key;
import com.odb.database.ODBWalker;
import com.odb.database.Odb;
import com.odb.database.trigger.Trigger;
import com.odb.database.trigger.TriggerTiming;
import com.odb.exception.NotSupportedOperation;
import com.odb.exception.RemoveNullObjectException;
import com.zte.mos.domain.Group;
import com.zte.mos.exception.MOSException;
import com.zte.mos.persistent.Log;
import com.zte.mos.util.CloneHelper;

import java.util.List;

/**
 * Created by olinchy on 5/16/14.
 */
public class OperationODB<T extends Group> implements Odb<T>
{
    private final ExtendedODB<T> parent;
    private final ExtendedODB<T> owner;
    private T data;

    public OperationODB(ExtendedODB<T> parent) throws MOSException
    {
        this.parent = parent;
        owner = (ExtendedODB<T>) parent.startTransaction();
    }

    @Override
    public Odb<T> startTransaction() throws MOSException
    {
        throw new NotSupportedOperation("need to startTransaction in CommitedODB");
    }

    @Override
    public Odb<T> startEmptyTransaction() throws MOSException
    {
        throw new NotSupportedOperation("need to startEmptyTransaction in CommitedODB");
    }

    @Override
    public void add(T t) throws MOSException
    {
        this.data = t;
        owner.beforeAdd(t);
        owner.doAdd(t);
        owner.afterAdd(t);
    }

    @Override
    public void set(T t) throws MOSException
    {
        this.data = t;
        owner.beforeSet(t);
        owner.doSet(t);
        owner.afterSet(t);
        this.data = t;
    }

    @Override
    public void remove(Key key) throws MOSException
    {
        T value = this.get(key);
        try
        {
            delete(value);
        }
        catch (RemoveNullObjectException e)
        {
            throw new RemoveNullObjectException(key.toString());
        }
    }

    @Override
    public T get(Key key) throws MOSException
    {
        return parent.get(key);
    }

    @Override
    public void remove(T t) throws MOSException
    {
        delete(t);
    }

    @Override
    public List<Log<T>> commit() throws MOSException
    {
        return owner.commit();
    }

    @Override
    public void rollback() throws MOSException
    {
        clearSet();
        owner.rollback();
    }

    @Override
    public void all(ODBWalker<T> walker) throws MOSException
    {

    }

    @Override
    public void clear(T t)
    {

    }

    @Override
    public void recover(T t)
    {

    }

    @Override
    public boolean parentOf(Odb<T> operator)
    {
        return false;
    }

    @Override
    public void acquire(T data, Odb<T> odb, OperatingSet.OperationSection relation)
            throws MOSException
    {

    }

    @Override
    public void release(T data, Odb<T> odb) throws MOSException
    {

    }

    @Override
    public void setChildren(T t) throws MOSException
    {
        owner.doSet(t);
        this.data = t;
    }

    @Override
    public void regGlobalTrigger(TriggerTiming timming, Trigger trigger)
    {

    }

    @Override
    public void regObjTrigger(
            Class<?> objClass, TriggerTiming timming,
            Trigger<T> trigger)
    {

    }

    @Override
    public void clearTrigger()
    {

    }

    @Override
    public void removeChild(T parent, T child) throws MOSException
    {
        owner.removeChild(parent, child);
    }

    @Override
    public void addChild(T parent, T child) throws MOSException
    {
        owner.addChild(parent, child);
    }

    @Override
    public void stop()
    {
    }

    @Override
    public void clearAll()
    {

    }

    @Override
    public void renameWithPostfix(String postfix)
    {
    }

    @Override
    public void delPostfix(String postfix)
    {
    }

    private void clearSet() throws MOSException
    {
        if (data != null)
        {
            owner.release(data, owner);
            data = null;
        }
    }

    private void delete(T t) throws MOSException
    {
        if (t == null)
        {
            throw new RemoveNullObjectException();
        }
        this.data = CloneHelper.clone(t);
        owner.beforeDel(data);
        owner.doDel(data);
        owner.afterDel(data);
    }

    @Override
    public Key key(T t) throws MOSException
    {
        return parent.key(t);
    }
}
