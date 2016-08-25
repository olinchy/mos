package com.odb.database.extended_odb;

import com.odb.database.Indexer;
import com.odb.database.Key;
import com.odb.database.Odb;
import com.odb.database.trigger.Trigger;
import com.odb.database.trigger.TriggerHolder;
import com.odb.database.trigger.TriggerTiming;
import com.odb.exception.MaxTransactionAchievedException;
import com.zte.mos.domain.Group;
import com.zte.mos.exception.MOSException;
import com.zte.mos.util.CloneHelper;
import com.zte.mos.util.tools.IDAllocator;

import java.util.TreeMap;

import static com.odb.database.extended_odb.OperatingSet.OperationSection.EXISTENCE;
import static com.odb.database.extended_odb.OperatingSet.OperationSection.VALUE;
import static com.odb.database.trigger.TriggerTiming.*;
import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by olinchy on 5/19/14.
 */
@SuppressWarnings("unchecked")
public abstract class ExtendedODB<T extends Group> implements Odb<T>
{
    protected final Indexer<T> primaryIndex;
    protected final OperatingSet<T> set;
    protected TreeMap<Integer, TransactionODB<T>> children = new TreeMap<Integer, TransactionODB<T>>();
    private IDAllocator allocator = new IDAllocator(1, 65535);
    private TriggerHolder triggerHolder;

    public ExtendedODB(TriggerHolder triggerHolder, Indexer<T> primaryIndex, OperatingSet<T> set)
    {
        this.primaryIndex = primaryIndex;
        this.set = set;
        this.triggerHolder = triggerHolder;
    }

    public T get(T t) throws MOSException
    {
        return this.get(primaryIndex.key(t));
    }

    @Override
    synchronized public Odb<T> startTransaction() throws MOSException
    {
        if (children.size() >= MAX_TRANS_COUNT)
        {
            throw new MaxTransactionAchievedException();
        }

        int transId = allocator.allocate();
        TransactionODB<T> child = new TransactionODB<T>(triggerHolder, primaryIndex, this, transId, set);
        if (this instanceof CommittedODB)
            logger(this.getClass()).debug(
                    "starting transaction odb with transID: " + transId + " by " + this.toString());
        children.put(transId, child);

        return child;
    }

    @Override
    synchronized public Odb<T> startEmptyTransaction() throws MOSException
    {
        if (children.size() >= MAX_TRANS_COUNT)
        {
            throw new MaxTransactionAchievedException();
        }

        int transId = allocator.allocate();
        TransactionODB<T> child = new TransactionODB<T>(new TriggerHolder(), primaryIndex, this, transId, set);
        children.put(transId, child);

        return child;
    }

    @Override
    public void add(T t) throws MOSException
    {
        Odb<T> operation = null;
        try
        {
            operation = new OperationODB<T>(this);
            operation.add(CloneHelper.clone(t));
            operation.commit();
        }
        catch (Throwable e)
        {
            handleException(operation, e);
        }
    }

    @Override
    public void set(T t) throws MOSException
    {
        Odb<T> operation = null;
        try
        {
            operation = new OperationODB<T>(this);
            operation.set(CloneHelper.clone(t));
            operation.commit();
        }
        catch (Throwable e)
        {
            handleException(operation, e);
        }
    }

    @Override
    public void remove(Key key) throws MOSException
    {
        Odb<T> operation = null;
        try
        {
            operation = new OperationODB<T>(this);
            operation.remove(key);
            operation.commit();
        }
        catch (Throwable e)
        {
            handleException(operation, e);
        }
    }

    @Override
    public void remove(T t) throws MOSException
    {
        Odb<T> operation = null;
        try
        {
            operation = new OperationODB<T>(this);
            operation.remove(t);
            operation.commit();
        }
        catch (Throwable e)
        {
            handleException(operation, e);
        }
    }

    @Override
    public void clear(T t) throws MOSException
    {
    }

    @Override
    public void recover(T t) throws MOSException
    {
    }

    @Override
    public boolean parentOf(Odb<T> that)
    {
        for (TransactionODB tranOdb : children.values())
        {
            if (tranOdb == that)
            {
                return true;
            }
            else
            {
                if (tranOdb.parentOf(that))
                {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void acquire(T data, Odb<T> odb, OperatingSet.OperationSection relation) throws MOSException
    {
        set.acquire(primaryIndex.key(data), odb, relation);
    }

    @Override
    public void release(T data, Odb<T> odb) throws MOSException
    {
        set.release(primaryIndex.key(data), odb);
    }

    @Override
    public void setChildren(T t) throws MOSException
    {
        Odb<T> operation = null;
        try
        {
            operation = new OperationODB<T>(this);
            operation.setChildren(CloneHelper.clone(t));
            operation.commit();
        }
        catch (Throwable e)
        {
            handleException(operation, e);
        }
    }

    @Override
    public void regGlobalTrigger(TriggerTiming timing, Trigger trigger)
    {
        triggerHolder.registerUniversal(timing, trigger);
    }

    @Override
    public void regObjTrigger(Class<?> objClass, TriggerTiming timing, Trigger<T> trigger)
    {
        triggerHolder.register(objClass, timing, trigger);
    }

    @Override
    public void clearTrigger()
    {
        triggerHolder.clear();
    }

    public Trigger trigger(TriggerTiming timing)
    {
        return triggerHolder.trigger(timing);
    }

    @Override
    public int hashCode()
    {
        return primaryIndex != null ? primaryIndex.hashCode() : 0;
    }

    @Override
    public boolean equals(Object o)
    {
        return o == this;
    }

    protected abstract void doAdd(T t) throws MOSException;

    protected abstract void doSet(T t) throws MOSException;

    protected abstract void doDel(T t) throws MOSException;

    protected void clear(int transId)
    {
        TransactionODB<T> transOdb = children.get(transId);
        if (transOdb != null)
        {
            children.remove(transId);
        }
    }

    protected void beforeAdd(T t) throws MOSException
    {
        set.acquire(t, this, EXISTENCE);
        trigger(beforeAdd).activate(t, this);
    }

    protected void afterAdd(T t) throws MOSException
    {
        trigger(afterAdd).activate(t, this);
    }

    protected void beforeSet(T t) throws MOSException
    {
        set.acquire(t, this, VALUE);
        trigger(beforeSet).activate(t, this);
    }

    protected void afterSet(T t) throws MOSException
    {
        trigger(afterSet).activate(t, this);
    }

    protected void beforeDel(T t) throws MOSException
    {
        set.acquire(t, this, EXISTENCE);
        trigger(beforeDelete).activate(t, this);
    }

    protected void afterDel(T t) throws MOSException
    {
        trigger(afterDelete).activate(t, this);
    }

    private void handleException(Odb<T> operation, Throwable e) throws MOSException
    {
        if (operation != null)
        {
            operation.rollback();
        }
        if (e instanceof MOSException)
            throw (MOSException) e;
        else
            throw new MOSException(e);
    }

    protected void postCommit() throws MOSException
    {
    }

    @Override
    public void stop()
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

    @Override
    public Key key(T t) throws MOSException
    {
        return this.primaryIndex.key(t);
    }
}
