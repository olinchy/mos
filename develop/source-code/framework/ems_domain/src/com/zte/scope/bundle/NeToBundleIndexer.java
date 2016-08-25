package com.zte.scope.bundle;

import com.odb.database.BerkeleyDBDPLIndexer;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.SecondaryIndex;

import java.util.HashMap;

/**
 * Created by luoqingkai on 15-7-29.
 */
public class NeToBundleIndexer extends BerkeleyDBDPLIndexer<String, NeToBundleBind>
{
    public NeToBundleIndexer(String dbName, boolean autoSync)
    {
        super(autoSync);
        this.dbName = dbName;
    }

    public NeToBundleIndexer(String dbName)
    {
        this.dbName = dbName;
    }

    private final String dbName;

    @Override
    protected String dbName()
    {
        return dbName;//"NeToBundleBind";
    }

    @Override
    protected PrimaryIndex<String, NeToBundleBind> createPrimaryIndex()
    {
        primaryIndex = store.getPrimaryIndex(String.class, NeToBundleBind.class);
        return primaryIndex;
    }

    @Override
    protected HashMap<String, SecondaryIndex> createSecondIndexes()
    {
        return null;
    }
}
