package com.odb.database.extended_odb;

import com.odb.database.Indexer;
import com.odb.database.Key;
import com.odb.database.ODBWalker;
import com.odb.database.operation.OperTypes;
import com.odb.database.operstate.OperationState;
import com.odb.database.operstate.OperationStatePack;
import com.odb.database.trigger.TriggerGroup;
import com.odb.database.trigger.TriggerHolder;
import com.odb.database.trigger.TriggerTiming;
import com.zte.mos.domain.Group;
import com.zte.mos.exception.MOSException;
import com.zte.mos.persistent.Log;
import com.zte.mos.util.CloneHelper;

import java.util.*;

import static com.odb.database.operation.OperTypes.*;
import static com.odb.database.operstate.OperationStates.StNull;
import static com.odb.database.operstate.OperationStates.StOrigin;
import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by olinchy on 5/16/14 .
 */
public class TransactionODB<T extends Group> extends ExtendedODB<T>
{
    private final int transId;
    private final ExtendedODB<T> parent;
    private final TreeMap<Key, OperationState<T>> datas = new TreeMap<Key, OperationState<T>>();

    public TransactionODB(
            TriggerHolder triggerHolder, Indexer<T> primaryIndex, ExtendedODB<T> parent, int transId,
            OperatingSet<T> set)
    {
        super(triggerHolder, primaryIndex, set);
        this.parent = parent;
        this.transId = transId;
    }

    @Override
    public int hashCode()
    {
        return 0;
    }

    @Override
    protected void doAdd(T t) throws MOSException
    {
        merge(t, add);
    }

    @Override
    protected void doSet(T t) throws MOSException
    {
        merge(t, OperTypes.set);
    }

    @Override
    protected void doDel(T t) throws MOSException
    {
        merge(t, remove);
    }

    @Override
    public T get(Key key) throws MOSException
    {
        T to;
        OperationState<T> state = datas.get(key);
        if (state != null)
        {
            if (state.isDataDestroyed())
            {
                to = null;
            }
            else
            {
                T data = state.data();
                if (data == null)
                {
                    to = parent.get(key);
                }
                else
                {
                    to = CloneHelper.clone(state.data());
                }
            }
        }
        else
        {
            to = parent.get(key);
        }

        return to;
    }

    @Override
    public List<Log<T>> commit() throws MOSException
    {
        logger(this.getClass()).debug("commit " + this.toString());
        LinkedList<Log<T>> logs = new LinkedList<Log<T>>();
        try
        {
            for (Map.Entry<Integer, TransactionODB<T>> entry : children.entrySet())
            {
                entry.getValue().commit();
            }

            set.handover(this, parent);
            // commit self
            for (OperationState<T> state : datas.values())
            {
                try
                {
                    state.logTo(logs, parent);
                    state.commitTo(this.parent);
                }
                catch (Throwable e)
                {
                    logger(this.getClass()).debug(
                            "committing " + state + " caught exception under " + this.toString() + "!", e);
                }
            }
        }
        finally
        {
            parent.clear(transId);
            parent.postCommit();
            return logs;
        }
    }

    @Override
    public void rollback() throws MOSException
    {
        try
        {
            logger(this.getClass()).debug("rollback " + this.toString());
            for (Map.Entry<Integer, TransactionODB<T>> entry : children.entrySet())
            {
                entry.getValue().rollback();
            }

            // rollback self
            // clear all locks
            set.release(this);
            for (OperationState<T> state : datas.values())
            {
                T data = state.data();
                if (data != null)
                {
                    data.destroy();
                    logger(this.getClass()).debug(" roll back data " + data.toString());
                }
            }
            this.datas.clear();
        }
        finally
        {
            parent.clear(transId);
        }
    }

    @Override
    public void all(ODBWalker<T> walker) throws MOSException
    {
        parent.all(walker);
        for (OperationState<T> state : datas.values())
        {
            if (!state.isDataDestroyed())
            {
                if (walker.contains(state.data()))
                {
                    walker.remove(state.data());
                }
                walker.add(state.data());
            }
            else
            {
                walker.remove(state.data());
            }
        }
    }

    @Override
    public void removeChild(T parent, T child) throws MOSException
    {
        merge(parent, child, rmChild);
    }

    @Override
    public void addChild(T parent, T child) throws MOSException
    {
        merge(parent, child, addChild);
    }

    @Override
    public void clearAll()
    {

    }

    /**
     * fixme
     * a god damned back door
     * there is no business of odb
     *
     * @throws MOSException
     */
    public void replayObjTrigger() throws MOSException
    {
        ArrayList<Key> keySet = new ArrayList<Key>();
        keySet.addAll(this.datas.keySet());
        for (Key key : keySet)
        {
            final OperationState<T> operationState = datas.get(key);
            operationState.post(new PostProcess()
            {
                public void post() throws MOSException
                {
                    ((TriggerGroup) TransactionODB.this.trigger(TriggerTiming.afterAdd)).activateObject(
                            operationState.data(), TransactionODB.this);
                }
            });
        }
    }

    @Override
    public String toString()
    {
        return "TransactionODB{" +
                "transId=" + transId +
                ", parent=" + parent +
                ", datas=" + datas.size() +
                '}';
    }

    private void merge(T t, OperTypes oper) throws MOSException
    {
        this.merge(t, t, oper);
    }

    private OperationState<T> getOperationState(T t) throws MOSException
    {
        OperationState<T> state = datas.get(primaryIndex.key(t));
        if (state == null)
        {
            state = decideByExistence(t);
        }
        return state;
    }

    private OperationState<T> decideByExistence(T t) throws MOSException
    {
        OperationState<T> state;
        T data = this.get(primaryIndex.key(t));
        if (data == null)
        {
            state = new OperationStatePack<T>(StNull.<T>instance(null));
        }
        else
        {
            state = new OperationStatePack<T>(StOrigin.instance(data));
        }
        return state;
    }

    private void merge(T parent, T child, OperTypes oper) throws MOSException
    {
        OperationState<T> state = getOperationState(parent);
        try
        {
            state = state.merge(oper, child);
        }
        finally
        {
            if (state != null)
            {
                state.decideExistence(datas, primaryIndex.key(parent));
                state.decideExistence(set, primaryIndex.key(parent), this);
            }
        }
    }
}
