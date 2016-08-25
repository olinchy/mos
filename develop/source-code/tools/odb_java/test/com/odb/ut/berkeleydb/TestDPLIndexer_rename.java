package com.odb.ut.berkeleydb;

import com.odb.database.BerkeleyDBDPLIndexer;
import com.odb.database.BerkeleyDBIndexer;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by olinchy on 16-5-26.
 */
public class TestDPLIndexer_rename extends TestBDB
{
    @BeforeClass
    public static void setUp() throws Exception
    {
        TestBDB.initEnv();
        String[] cities = new String[]{"Tianjin", "Beijing", "Shanghai"};
        String[] company = new String[]{"ZTE", "Huawei"};
        for (int i = (int) 'A'; i < (int) 'Z'; i++)
        {
            Person person = new Person(i, String.valueOf((char) i), cities[i % 3], company[i % 2]);
            db.put(person);
        }
    }

    @Test
    public void test() throws Exception
    {
        assertNotNull(db.get((int) 'A'));
        ((BerkeleyDBIndexer<Integer, Person>) db.getPrimaryIndex()).rename("test");
        assertNotNull(
                ((BerkeleyDBDPLIndexer<Integer, Person>) db.getPrimaryIndex()).secondIndex("cityIndex").get("Tianjin"));
        db.stop();

        TestBDB.initEnv();
        ((BerkeleyDBIndexer<Integer, Person>) db.getPrimaryIndex()).rename("test");
        assertNull(db.get((int) 'A'));
    }
}
