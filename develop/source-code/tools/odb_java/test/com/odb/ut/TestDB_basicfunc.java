package com.odb.ut;

import com.odb.database.Odb;
import com.odb.ut.stub.*;
import com.zte.mos.exception.MOSException;
import org.junit.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by l10121897 on 5/14/2014.
 */
public class TestDB_basicfunc
{
    private Odb<Mo> odb;
    private Mo a;
    private Mo b;
    private Mo c;

    @Before
    public void before()
    {
        a = new Mo("a");
        b = new Mo("b");
        c = new Mo("c");
        odb = MoDB.instance();
    }

    @Test
    public void testTrans_level_1() throws MOSException
    {
        Odb<Mo> odbTrans = odb.startTransaction();

        // add
        odbTrans.add(a);
        odbTrans.add(b);
        odb.add(c);

        Mo a_ref = odbTrans.get(new MoIndexer().key(a));
        assertThat(a, is(a_ref));
        assertTrue(a != a_ref);

        assertTrue(odbTrans.get(new MoIndexer().key(b)) != null);
        assertTrue(odbTrans.get(new MoIndexer().key(c)) != null);

        // set
        b.name = "x";
        odbTrans.set(b);
        Mo b_ref = odbTrans.get(new MoIndexer().key(b));
        assertTrue(b_ref != null);
        assertThat(b_ref.name, is("x"));

        // remove
        odbTrans.remove(c);
        odbTrans.remove(a);
        assertTrue(odb.get(new MoIndexer().key(c)) != null);

        odbTrans.commit();
        assertTrue(odb.get(new MoIndexer().key(c)) == null);
        assertTrue(odb.get(new MoIndexer().key(a)) == null);

        Mo b_commited = odb.get(new MoIndexer().key(b));
        assertThat(b_commited.name, is("x"));

    }

}
