package com.odb.database;

import com.sleepycat.je.CursorConfig;
import com.sleepycat.persist.EntityCursor;
import com.zte.mos.exception.MOSException;

import java.util.ArrayList;

/**
 * Created by luoqingkai on 15-8-6.
 */
public class DefaultDPLWalker<PK, T> implements Walker<PK, T>
{

    protected ArrayList<T> result = new ArrayList<T>();

    public void walk(BerkeleyDBIndexer<PK, T> indexer) throws MOSException
    {
        BerkeleyDBDPLIndexer<PK, T> dplIndexer = (BerkeleyDBDPLIndexer<PK, T>) indexer;
        CursorConfig config = new CursorConfig();
        config.setReadUncommitted(true);

        EntityCursor<T> cursor = dplIndexer.entities();

        for (T t : cursor)
        {
            result.add(t);
        }
        cursor.close();
    }

    public ArrayList<T> getResult()
    {
        return result;
    }
}
