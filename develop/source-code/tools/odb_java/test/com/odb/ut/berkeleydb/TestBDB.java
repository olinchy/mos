package com.odb.ut.berkeleydb;

import com.odb.database.BerkeleyDB;
import com.odb.database.BerkeleyDBIndexer;
import com.zte.mos.util.tools.Prop;

import java.util.Properties;

/**
 * Created by olinchy on 15-10-10.
 */
public class TestBDB
{
    static BerkeleyDB<Integer, Person> db;
    static BerkeleyDBIndexer<Integer, Person> index;

    public static void initEnv()
    {
        Properties prop;
        Prop.set(prop = new Properties());

        prop.put("storage_path", "./persondb/");
        db = new BerkeleyDB<Integer, Person>(index = Person.createIndex());
    }
}
