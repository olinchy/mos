package com.zte.mos.storage.temp;

import com.odb.database.Indexer;
import com.odb.database.Key;
import com.odb.database.PersistDB;
import com.odb.database.Walker;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.BerkeleyDBException;
import com.zte.mos.exception.MOSException;

import java.util.HashMap;

/**
 * Created by luoqingkai on 15-9-15.
 */
public class HashMapDB implements PersistDB<Key, ManagementObject> {

    private HashMap<String, ManagementObject> data
            = new HashMap<String, ManagementObject>();

    private Indexer<ManagementObject> indexer;

    public HashMapDB(Indexer<ManagementObject> indexer) {
        this.indexer = indexer;
    }

    @Override
    public void all(Walker<Key, ManagementObject> walker) throws MOSException {

    }

    @Override
    public void remove(Key key) throws BerkeleyDBException {
        data.remove(key.toString());
    }

    @Override
    public ManagementObject get(Key key) throws BerkeleyDBException {
        return data.get(key.toString());
    }

    @Override
    public void put(ManagementObject mo) throws MOSException {
        data.put(mo.dn().toString(), mo);
    }

    @Override
    public void stop() {

    }

    @Override
    public void clearAll() {

    }

    @Override
    public void sync() {

    }

    @Override
    public Indexer<ManagementObject> getPrimaryIndex() {
        return indexer;
    }
}
