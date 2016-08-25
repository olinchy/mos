package com.zte.mos.storage;

import com.odb.database.BerkeleyDBDPLIndexer;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.SecondaryIndex;

import java.util.HashMap;


public class MapDBIndexer extends BerkeleyDBDPLIndexer<String, DataUnit> {
    private final String dbName;

    public MapDBIndexer(String dbName, boolean autoSync) {
        super(autoSync);
        this.dbName = dbName;
    }

    public MapDBIndexer(String dbName) {
        this(dbName, true);
    }

    @Override
    protected String dbName() {
        return dbName;
    }

    @Override
    protected PrimaryIndex<String, DataUnit> createPrimaryIndex() {
        this.primaryIndex = this.store.getPrimaryIndex(String.class, DataUnit.class);
        return primaryIndex;
    }

    @Override
    protected HashMap<String, SecondaryIndex> createSecondIndexes() {
        return null;
    }
}
