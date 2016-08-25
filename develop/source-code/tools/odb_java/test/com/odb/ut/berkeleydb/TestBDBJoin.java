package com.odb.ut.berkeleydb;

import com.odb.database.BerkeleyDBDPLIndexer;
import com.odb.database.BerkeleyDBIndexer;
import com.odb.database.Walker;
import com.sleepycat.je.CursorConfig;
import com.sleepycat.persist.EntityCursor;
import com.sleepycat.persist.EntityJoin;
import com.sleepycat.persist.ForwardCursor;
import com.zte.mos.exception.MOSException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by olinchy on 15-9-30.
 */
public class TestBDBJoin extends TestBDB
{
    static String[] cities = new String[]{"Tianjin", "Beijing", "Shanghai"};
    static String[] company = new String[]{"ZTE", "Huawei"};

    @BeforeClass
    public static void insertData() throws MOSException
    {
        initEnv();

        for (int i = (int) 'A'; i < (int) 'Z'; i++)
        {
            Person person = new Person(i, String.valueOf((char) i), cities[i % 3], company[i % 2]);
            db.put(person);
        }
    }

    @AfterClass
    public static void destroyAll()
    {
//        db.stop();
    }

    @Test
    public void test_secondary_index_search_by_city() throws Exception
    {
        TESTWalker walker = new TESTWalker()
        {
            ArrayList<Person> persons = new ArrayList<Person>();

            @Override
            public Iterator<Person> iterator()
            {
                return persons.iterator();
            }

            @Override
            public void walk(BerkeleyDBIndexer<Integer, Person> indexer) throws MOSException
            {
                BerkeleyDBDPLIndexer<Integer, Person> dplIndexer = (BerkeleyDBDPLIndexer<Integer, Person>) indexer;
                CursorConfig config = new CursorConfig();
                config.setReadUncommitted(true);
                EntityCursor<Person> cursor = null;
                try
                {
                    cursor = dplIndexer.secondIndex("cityIndex").subIndex("Tianjin").entities(null, config);
                    for (Person person : cursor)
                    {
                        persons.add(person);
                    }
                }
                finally
                {
                    cursor.close();
                }
            }
        };

        walk(walker);
    }

    private void walk(TESTWalker walker) throws MOSException
    {
        index.all(walker);

        out(walker);
    }

    private void out(TESTWalker walker)
    {
        for (Person person : walker)
        {
            System.out.println(person);
        }
    }

    @Test
    public void test_join() throws Exception
    {
        final TESTWalker walker = new TESTWalker()
        {
            @Override
            public void walk(BerkeleyDBIndexer<Integer, Person> indexer) throws MOSException
            {

                BerkeleyDBDPLIndexer<Integer, Person> dplIndexer = (BerkeleyDBDPLIndexer<Integer, Person>) indexer;
                EntityJoin<Integer, Person> entityJoin = new EntityJoin<Integer, Person>(index.getPrimaryIndex());
                entityJoin.addCondition(dplIndexer.secondIndex("cityIndex"), "Beijing");
                entityJoin.addCondition(dplIndexer.secondIndex("companyIndex"), "ZTE");

                ForwardCursor<Person> cursor = entityJoin.entities();
                for (Person person : cursor)
                {
                    persons.add(person);
                }
            }
        };

        walk(walker);
    }

    abstract class TESTWalker implements Walker<Integer, Person>, Iterable<Person>
    {
        ArrayList<Person> persons = new ArrayList<Person>();

        @Override
        public Iterator<Person> iterator()
        {
            return persons.iterator();
        }
    }

    @Test
    public void test_rename() throws Exception
    {
        Person person = db.get(1);

        db.stop();



    }
}
