package com.odb.ut.berkeleydb;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by olinchy on 15-10-10.
 */
public class TestNoEntityClassInEntity extends TestBDB
{
    @BeforeClass
    public static void setup_beforeClass()
    {
        initEnv();
    }

    @Test
    public void test() throws Exception
    {
        Person person = new Person(1, "liuwei", "tianjin", "zte");

        person.states.add(new State("inRelation", "wed"));
        person.states.add(new State("work", "in"));

        db.put(person);

        Person person_getting_out = db.get(1);

        assertThat(person.states, is(person_getting_out.states));
    }
}
