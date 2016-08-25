package com.odb.ut.berkeleydb;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertNull;

/**
 * Created by ccy on 5/27/16.
 */
public class TestBDBStop extends TestBDB
{
    @BeforeClass
    public static void setUp() throws Exception
    {
        TestBDB.initEnv();
    }

    @Test
    public void test() throws Exception
    {
        Person person = new Person(1, "liuwei", "tianjin", "zte");
        db.put(person);
        db.stop();
        TestBDB.initEnv();
        Person person_getting_out = db.get(1);
        assertNull(person_getting_out);
    }


}
