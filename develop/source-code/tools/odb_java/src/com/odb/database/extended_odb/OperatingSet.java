package com.odb.database.extended_odb;

import com.odb.database.Indexer;
import com.odb.database.Key;
import com.odb.database.Odb;
import com.odb.exception.DataUnderOperatingException;
import com.zte.mos.domain.Group;
import com.zte.mos.exception.MOSException;
import com.zte.mos.type.Pair;

import java.util.*;

/**
 * Created by olinchy on 5/23/14 for MO_JAVA.
 */
public class OperatingSet<T extends Group>
{

    private final Indexer<T> indexer;
    private TreeMap<Key, List<Pair<OperationSection, Odb<T>>>> map = new TreeMap<Key, List<Pair<OperationSection, Odb<T>>>>();
    public OperatingSet(Indexer<T> indexer)
    {
        this.indexer = indexer;
    }

    public synchronized void acquire(Key key, Odb<T> operator, OperationSection oper)
            throws MOSException
    {
        List<Pair<OperationSection, Odb<T>>> pairs = map.get(key);
        if (pairs == null)
        {
            pairs = new LinkedList<Pair<OperationSection, Odb<T>>>();
            map.put(key, pairs);
        }
        for (Pair<OperationSection, Odb<T>> pair : pairs)
        {
            if (pair.first().willBlock(oper))
            {
                if (isOrParentOf(pair.second(), operator))
                {
                    throw new DataUnderOperatingException(
                            "key : " + key.toString() + "is editing " + pair.first().name() + " by "
                                    + pair.second().toString() + " when " + operator.toString()
                                    + " is acquiring " + oper.name() + "!");
                }
            }
        }

        pairs.add(new Pair<OperationSection, Odb<T>>(oper, operator));
    }

    public synchronized void acquire(T t, Odb<T> operator, OperationSection oper)
            throws MOSException
    {
        this.acquire(indexer.key(t), operator, oper);
    }

    public synchronized void handover(Odb<T> from, Odb<T> to) throws MOSException
    {
        TreeMap<Key, List<Pair<OperationSection, Odb<T>>>> tempMap = this.getOpers(from);

        release(tempMap);

        acquire(tempMap, to);
    }

    public synchronized void release(Odb<T> from)
    {
        TreeMap<Key, List<Pair<OperationSection, Odb<T>>>> tempMap = this.getOpers(from);

        release(tempMap);
    }

    public synchronized void release(Key key, Odb<T> odb)
    {
        getOperNotReleased(key, odb);
    }

    private void acquire(TreeMap<Key, List<Pair<OperationSection, Odb<T>>>> toAcquire, Odb<T> to)
            throws MOSException
    {
        for (Map.Entry<Key, List<Pair<OperationSection, Odb<T>>>> entry : toAcquire.entrySet())
        {
            for (Pair<OperationSection, Odb<T>> pair : entry.getValue())
            {
                this.acquire(entry.getKey(), to, pair.first());
            }
        }
    }

    private void release(TreeMap<Key, List<Pair<OperationSection, Odb<T>>>> toAcquire)
    {
        for (Map.Entry<Key, List<Pair<OperationSection, Odb<T>>>> entry : toAcquire.entrySet())
        {
            for (Pair<OperationSection, Odb<T>> pair : entry.getValue())
            {
                this.release(entry.getKey(), pair.second());
            }
        }
    }

    private TreeMap<Key, List<Pair<OperationSection, Odb<T>>>> getOpers(Odb<T> from)
    {
        TreeMap<Key, List<Pair<OperationSection, Odb<T>>>> tempMap = new TreeMap<Key, List<Pair<OperationSection, Odb<T>>>>();
        for (Map.Entry<Key, List<Pair<OperationSection, Odb<T>>>> entry : map.entrySet())
        {
            for (Pair<OperationSection, Odb<T>> pair : entry.getValue())
            {
                List<Pair<OperationSection, Odb<T>>> tempPairs = tempMap.get(entry.getKey());
                if (tempPairs == null)
                {
                    tempPairs = new LinkedList<Pair<OperationSection, Odb<T>>>();
                    tempMap.put(entry.getKey(), tempPairs);
                }
                if (pair.second().equals(from))
                {
                    tempPairs.add(pair);
                }
            }
        }
        return tempMap;
    }

    private boolean isOrParentOf(Odb<T> maybeParent, Odb<T> maybeChild)
    {
        return maybeParent != null && !maybeParent.equals(maybeChild) && !maybeParent.parentOf(
                maybeChild);
    }

    private List<Pair<OperationSection, Odb<T>>> getOperNotReleased(Key key, Odb<T> odb)
    {
        LinkedList<Pair<OperationSection, Odb<T>>> temp = new LinkedList<Pair<OperationSection, Odb<T>>>();
        LinkedList<Pair<OperationSection, Odb<T>>> released = new LinkedList<Pair<OperationSection, Odb<T>>>();
        List<Pair<OperationSection, Odb<T>>> lst = map.get(key);
        if (lst != null)
        {
            temp.addAll(map.get(key));
            for (Pair<OperationSection, Odb<T>> pair : temp)
            {
                if (pair.second().equals(odb))
                {
                    released.add(pair);
                    lst.remove(pair);
                }
            }
            if (lst.size() > 0)
            {
                map.put(key, lst);
            }
            else
            {
                map.remove(key);
            }
        }
        return released;
    }

    public enum OperationSection
    {
        VALUE(1, 1, 3),
        RELATION(2, 3),
        EXISTENCE(3, 1, 2, 3);
        private final Integer[] willBlock;
        private int code;

        OperationSection(int code, Integer... willBlock)
        {
            this.code = code;
            this.willBlock = willBlock;
        }

        public boolean willBlock(OperationSection that)
        {
            return Arrays.asList(willBlock).contains(that.code);
        }
    }
}
