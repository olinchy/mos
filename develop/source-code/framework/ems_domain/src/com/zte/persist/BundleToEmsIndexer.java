package com.zte.persist;

import com.odb.database.BerkeleyDBDPLIndexer;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.SecondaryIndex;

import java.util.HashMap;

/**
 * Created by luoqingkai on 15-7-29.
 */
public class BundleToEmsIndexer extends BerkeleyDBDPLIndexer<String, BundleToEmsBind>
{
    public BundleToEmsIndexer(boolean autoSync){
        super(autoSync);
    }

    public BundleToEmsIndexer(){}

    @Override
    protected String dbName()
    {
        return "BundleToEmsBind";
    }

    @Override
    protected PrimaryIndex<String, BundleToEmsBind> createPrimaryIndex()
    {
        primaryIndex = store.getPrimaryIndex(String.class, BundleToEmsBind.class);
        return primaryIndex;
    }

    @Override
    protected HashMap<String, SecondaryIndex> createSecondIndexes()
    {
        return null;
    }
}
