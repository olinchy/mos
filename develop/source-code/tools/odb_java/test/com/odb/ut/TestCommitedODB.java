package com.odb.ut;

import com.odb.database.Odb;
import com.odb.ut.stub.*;
import com.zte.mos.exception.MOSException;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by olinchy on 5/19/14.
 */
public class TestCommitedODB
{

    @Test
    public void testAdd() throws MOSException
    {
        Odb<Mo> db = MoDB.instance();
        Mo a = new Mo("a");

        db.add(a);

        Mo a_ref = db.get(new MoIndexer().key(a));

        assertThat(a_ref, is(a));

    }

    @Test
    public void testSet() throws MOSException
    {
        Odb<Mo> db = MoDB.instance();
        Mo a = new Mo("a");

        db.add(a);
        Mo a_modi = new Mo("a");
        a_modi.name = "mo_a";
        db.set(a_modi);

        Mo a_ref = db.get(new MoIndexer().key(a));

        assertThat(a_ref, is(a_modi));
    }

    @Test
    public void testDel() throws MOSException
    {
        Odb<Mo> db = MoDB.instance();
        Mo a = new Mo("a");
        Mo b = new Mo("b");

        db.add(a);
        db.add(b);

        Mo a_ref = db.get(new MoIndexer().key(a));
        Mo b_ref = db.get(new MoIndexer().key(b));

        assertThat(a, is(a_ref));
        assertThat(b, is(b_ref));

        db.remove(a);
        db.remove(new MoIndexer().key(b));

        a_ref = db.get(new MoIndexer().key(a));
        b_ref = db.get(new MoIndexer().key(b));

        assertTrue(a_ref == null);
        assertTrue(b_ref == null);

    }
}
