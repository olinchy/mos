package com.odb.ut;

import com.odb.database.Odb;
import com.odb.ut.stub.*;
import com.zte.mos.exception.MOSException;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by olinchy on 5/22/14 for MO_JAVA.
 */
public class TestRecursionTrans
{
    @Test
    public void test() throws MOSException
    {
        Mo a = new Mo("a");
        Mo b = new Mo("b");
        Mo c = new Mo("c");
        Mo d = new Mo("d");
        Mo e = new Mo("e");

        Odb<Mo> odb = MoDB.instance();
        Odb<Mo> tran_Level_1 = odb.startTransaction();
        Odb<Mo> tran_Level_2 = tran_Level_1.startTransaction();
        Odb<Mo> tran_Level_3 = tran_Level_2.startTransaction();
        Odb<Mo> tran_Level_4 = tran_Level_3.startTransaction();

        odb.add(a);
        tran_Level_1.add(b);
        tran_Level_2.add(c);
        tran_Level_3.add(d);
        tran_Level_4.add(e);

        Mo a_ref = odb.get(new MoIndexer().key(a));
        Mo b_ref = tran_Level_1.get(new MoIndexer().key(b));
        Mo c_ref = tran_Level_2.get(new MoIndexer().key(c));
        Mo d_ref = tran_Level_3.get(new MoIndexer().key(d));
        Mo e_ref = tran_Level_4.get(new MoIndexer().key(e));

        // test get
        assertTrue(
                a_ref != null && b_ref != null && c_ref != null && d_ref != null && e_ref != null);

        // test clone
        assertTrue(a_ref != a);
        assertTrue(b_ref != b);
        assertTrue(c_ref != c);
        assertTrue(d_ref != d);
        assertTrue(e_ref != e);

        // test data not commited
        assertTrue(odb.get(new MoIndexer().key(b)) == null);
        assertTrue(tran_Level_1.get(new MoIndexer().key(c)) == null);
        assertTrue(tran_Level_2.get(new MoIndexer().key(d)) == null);
        assertTrue(tran_Level_3.get(new MoIndexer().key(e)) == null);

        // test commit level 4
        tran_Level_4.commit();
        e_ref = tran_Level_3.get(new MoIndexer().key(e));
        assertTrue(e_ref != null);
        assertTrue(e_ref != e);
        assertThat(e, is(e_ref));

        e_ref = tran_Level_2.get(new MoIndexer().key(e));
        assertTrue(e_ref == null);

        // test commit level 3
        tran_Level_3.commit();
        e_ref = tran_Level_2.get(new MoIndexer().key(e));
        d_ref = tran_Level_2.get(new MoIndexer().key(d));
        assertTrue(e_ref != null);
        assertTrue(e_ref != e);
        assertThat(e, is(e_ref));

        assertTrue(d_ref != null);
        assertTrue(d_ref != d);
        assertThat(d, is(d_ref));

        d_ref = tran_Level_1.get(new MoIndexer().key(d));
        assertTrue(d_ref == null);

        // test commit level 1
        tran_Level_1.commit();
        a_ref = odb.get(new MoIndexer().key(a));
        b_ref = odb.get(new MoIndexer().key(b));
        c_ref = odb.get(new MoIndexer().key(c));
        d_ref = odb.get(new MoIndexer().key(d));
        e_ref = odb.get(new MoIndexer().key(e));

        // test get
        assertTrue(
                a_ref != null && b_ref != null && c_ref != null && d_ref != null && e_ref != null);

        // test clone
        assertTrue(a_ref != a);
        assertTrue(b_ref != b);
        assertTrue(c_ref != c);
        assertTrue(d_ref != d);
        assertTrue(e_ref != e);

        // test equals
        assertThat(a, is(a_ref));
        assertThat(b, is(b_ref));
        assertThat(c, is(c_ref));
        assertThat(d, is(d_ref));
        assertThat(e, is(e_ref));

    }
}
