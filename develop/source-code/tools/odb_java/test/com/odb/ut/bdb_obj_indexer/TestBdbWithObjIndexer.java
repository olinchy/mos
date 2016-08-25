package com.odb.ut.bdb_obj_indexer;

import com.odb.database.BerkeleyDB;
import com.odb.database.BerkeleyDBIndexer;
import com.zte.mos.util.tools.Prop;

import java.util.Properties;

/**
 * Created by olinchy on 16-5-18.
 */
public class TestBdbWithObjIndexer
{
    static BerkeleyDB<String, Mobj> db;
    static BerkeleyDBIndexer<String, Mobj> index;

    public static void initEnv()
    {
        Properties prop;
        Prop.set(prop = new Properties());

        prop.put("storage_path", "./");
        db = new BerkeleyDB<String, Mobj>(index = Mobj.createIndex());
    }
}
