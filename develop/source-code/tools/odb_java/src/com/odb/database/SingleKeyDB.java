package com.odb.database;

import com.sleepycat.je.Environment;
import com.zte.mos.exception.BerkeleyDBException;
import com.zte.mos.exception.MOSException;

import java.util.List;

/**
 * Created by luoqingkai on 15-8-6.
 */
public class SingleKeyDB<K, T> implements SimpleDB<K,T>
{
    private BerkeleyDBDPLIndexer<K, T> indexer;
    private BerkeleyDB<K, T> db;

    public SingleKeyDB(BerkeleyDBDPLIndexer<K, T> indexer, Environment env)
    {
        this.indexer = indexer;
        db = new BerkeleyDB<K, T>(indexer, env);
    }

    public SingleKeyDB(BerkeleyDBDPLIndexer<K, T> indexer)
    {
        this.indexer = indexer;
        db = new BerkeleyDB<K, T>(indexer);
    }

    public void put(T t) throws MOSException
    {
        db.put(t);
        db.sync();
    }

    public T get(K key) throws BerkeleyDBException
    {
        return db.get(key);
    }

    public void delete(K key) throws BerkeleyDBException
    {
        db.remove(key);
        db.sync();
    }

    public List<T> getAll() throws MOSException
    {
        DefaultDPLWalker<K, T> walker = new DefaultDPLWalker<K, T>();
        indexer.all(walker);
        return walker.getResult();
    }
}
