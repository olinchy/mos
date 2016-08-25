package com.odb.ut.bdb_obj_indexer;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by olinchy on 16-5-18.
 */
public class TestCases extends TestBdbWithObjIndexer
{
    @BeforeClass
    public static void setUp() throws Exception
    {
        TestBdbWithObjIndexer.initEnv();
        Mobj obj = new Mobj();
        obj.name = "test";
        obj.value = "test_value";
        db.put(obj);
    }

    @Test
    public void test_rename() throws Exception
    {
        assertThat(index.getDatabase().getDatabaseName(), is("test"));
        index.rename("test_name");

        Mobj obj = index.get("test");
        assertThat(obj.value, is("test_value"));

        assertThat(index.getDatabase().getDatabaseName(), is("test_name"));
    }
}
