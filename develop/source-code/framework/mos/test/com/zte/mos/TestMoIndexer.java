package com.zte.mos;

import com.odb.database.BerkeleyDB;
import com.odb.database.BerkeleyDBIndexer;
import com.odb.database.Key;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.modb.stub.Ems;
import com.zte.mos.storage.triggers.MoIndexer;
import com.zte.mos.util.tools.Prop;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Properties;

/**
 * Created by olinchy on 16-5-26.
 */
public class TestMoIndexer
{
    private static BerkeleyDB<Key, ManagementObject> db;

    @BeforeClass
    public static void setUp() throws Exception
    {
        Properties prop;
        Prop.set(prop = new Properties());

        prop.put("storage_path", "./");
        db = new BerkeleyDB<Key, ManagementObject>(new MoIndexer("/"));
    }

    @Test
    public void test() throws Exception
    {
        Ems ems = new Ems();

        db.put(ems);

        ((BerkeleyDBIndexer) db.getPrimaryIndex()).rename("/_bak");
    }

    @Test
    public void test_rename_and_stop() throws Exception
    {
        Ems ems = new Ems();

        db.put(ems);

        ((BerkeleyDBIndexer) db.getPrimaryIndex()).rename("/_bak");

        db.stop();

    }

    @Test
    public void test_stop() throws Exception
    {
        Ems ems = new Ems();

        db.put(ems);

        db.stop();

    }


}
