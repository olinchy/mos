package com.odb.database.extended_odb;

import com.odb.database.*;
import com.odb.database.operation.OperTypes;
import com.odb.database.trigger.TriggerHolder;
import com.odb.database.trigger.TriggerTiming;
import com.sleepycat.je.Environment;
import com.zte.mos.domain.Group;
import com.zte.mos.exception.MOSException;
import com.zte.mos.persistent.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by olinchy on 5/16/14.
 */
@SuppressWarnings("ALL")
public class CommittedODB<T extends Group> extends ExtendedODB<T>
{
    private LinkedList<Log<T>> logList = new LinkedList<Log<T>>();
    private PersistDB db;

    public CommittedODB(PersistDB db, OperatingSet<T> set)
    {
        super(new TriggerHolder(), db.getPrimaryIndex(), set);
        this.db = db;
    }

    public CommittedODB(Indexer<T> primaryIndex, OperatingSet<T> set, Environment env)
    {
        super(new TriggerHolder(), primaryIndex, set);
        db = new BerkeleyDB((BerkeleyDBObjectIndexer) primaryIndex, env);
    }

    /**
     * @param primaryIndex
     * @param set
     */
    public CommittedODB(Indexer<T> primaryIndex, OperatingSet<T> set)
    {
        super(new TriggerHolder(), primaryIndex, set);
        db = new BerkeleyDB((BerkeleyDBObjectIndexer) primaryIndex);
    }

    @Override
    public List<Log<T>> commit() throws MOSException
    {
        try
        {
            db.sync();
            return new LinkedList<Log<T>>(logList);
        }
        finally
        {
            clearLog();
        }
    }

    @Override
    public void all(ODBWalker<T> walker) throws MOSException
    {
        db.all(walker);
    }

    @Override
    public void clear(T t) throws MOSException
    {
        db.remove(primaryIndex.key(t));
    }

    @Override
    public void recover(T t) throws MOSException
    {
        this.save(t);
    }

    @Override
    public void removeChild(T parent, T child) throws MOSException
    {
        T data = this.get(primaryIndex.key(parent));
        if (data != null && data instanceof Group)
        {
            ((Group) data).remove(child);
            this.save(data);
        }
    }

    @Override
    public void addChild(T parent, T child) throws MOSException
    {
        T data = this.get(primaryIndex.key(parent));
        if (data != null && data instanceof Group)
        {
            ((Group) data).add(child);
            this.save(data);
        }
    }

    @Override
    public void rollback() throws MOSException
    {
    }

    @Override
    public T get(Key key) throws MOSException
    {
        T toDuplicate = (T) db.get(key);
        return toDuplicate;
    }

    @Override
    public int hashCode()
    {
        return 0;
    }

    @Override
    protected void doAdd(T t) throws MOSException
    {
        beforeCommit(OperTypes.add, t);
        operLog(Log.add(t));
        this.save(t);
        afterCommit(OperTypes.add, t);
    }

    @Override
    protected void doSet(T t) throws MOSException
    {
        beforeCommit(OperTypes.set, t);
        operLog(Log.set(get(primaryIndex.key(t)), t));
        this.save(t);
        afterCommit(OperTypes.set, t);
    }

    @Override
    public String toString()
    {
        return "CommittedODB:" + primaryIndex.toString();
    }

    protected void doDel(T t) throws MOSException
    {
        beforeCommit(OperTypes.remove, t);
        operLog(Log.del(t));
        db.remove(primaryIndex.key(t));
        afterCommit(OperTypes.remove, t);
    }

    private void operLog(Log<T> log)
    {
        logList.add(log);
    }

    protected void save(T data) throws MOSException
    {
        db.put(data);
    }

    private void beforeCommit(OperTypes oper, T t) throws MOSException
    {
        trigger(TriggerTiming.valueOf("beforeCommit_" + oper.name())).activate(t, this);
    }

    private void afterCommit(OperTypes oper, T t) throws MOSException
    {
        set.release(primaryIndex.key(t), this);
        trigger(TriggerTiming.valueOf("afterCommit_" + oper.name())).activate(t, this);
    }

    @Override
    protected synchronized void postCommit() throws MOSException
    {
        commit();
    }

    private void clearLog()
    {
        logList.clear();
    }

    @Override
    public void stop()
    {
        db.stop();
    }

    @Override
    public void clearAll()
    {
        db.clearAll();
    }

    @Override
    public void renameWithPostfix(String postfix)
    {
        if (primaryIndex instanceof BerkeleyDBIndexer)
        {
            BerkeleyDBIndexer indexer = (BerkeleyDBIndexer) primaryIndex;
            indexer.rename(indexer.getDatabase().getDatabaseName() + postfix);
        }
    }

    @Override
    public void delPostfix(String postfix)
    {
        if (primaryIndex instanceof BerkeleyDBIndexer)
        {
            BerkeleyDBIndexer indexer = (BerkeleyDBIndexer) primaryIndex;
            int index = indexer.getDatabase().getDatabaseName().indexOf(postfix);
            if (index != -1)
                indexer.rename(indexer.getDatabase().getDatabaseName().substring(0, index));
        }
    }
}
