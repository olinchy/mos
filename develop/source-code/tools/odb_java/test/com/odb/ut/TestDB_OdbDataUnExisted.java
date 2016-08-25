package com.odb.ut;

import com.odb.database.Odb;
import com.odb.exception.DataUnderOperatingException;
import com.odb.exception.OperationMergeFailure;
import com.odb.exception.RemoveNullObjectException;
import com.odb.ut.stub.Mo;
import com.odb.ut.stub.MoDB;
import com.odb.ut.stub.MoIndexer;
import com.zte.mos.exception.MOSException;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by l10121897 on 5/14/2014.
 */
public class TestDB_OdbDataUnExisted
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
    public void add_two_diff_data_then_commit() throws MOSException
    {
        Odb<Mo> odbTrans = odb.startTransaction();

        odbTrans.add(a);
        odbTrans.add(b);

        Mo a_ref = odbTrans.get(new MoIndexer().key(a));
        assertThat(a, is(a_ref));
        assertTrue(a != a_ref);

        Mo b_ref = odbTrans.get(new MoIndexer().key(b));
        assertThat(b, is(b_ref));
        assertTrue(b != b_ref);

        assertTrue(odb.get(new MoIndexer().key(a)) == null);
        assertTrue(odb.get(new MoIndexer().key(b)) == null);

        odbTrans.commit();

        assertTrue(odb.get(new MoIndexer().key(a)) != null);
        assertTrue(odb.get(new MoIndexer().key(b)) != null);
    }

    @Test
    public void add_two_diff_data_then_remove_one_then_commit() throws MOSException
    {
        Odb<Mo> odbTrans = odb.startTransaction();

        odbTrans.add(a);
        odbTrans.add(b);
        odbTrans.remove(b);

        Mo a_ref = odbTrans.get(new MoIndexer().key(a));
        assertThat(a, is(a_ref));
        assertTrue(a != a_ref);

        assertTrue(odbTrans.get(new MoIndexer().key(b)) == null);

        assertTrue(odb.get(new MoIndexer().key(a)) == null);
        assertTrue(odb.get(new MoIndexer().key(b)) == null);

        odbTrans.commit();

        assertTrue(odb.get(new MoIndexer().key(a)) != null);
        assertTrue(odb.get(new MoIndexer().key(b)) == null);
    }

    @Test
    public void add_unexisted_data_then_commit() throws MOSException
    {
        Odb<Mo> odbTrans = odb.startTransaction();

        odbTrans.add(a);

        Mo a_ref = odbTrans.get(new MoIndexer().key(a));
        assertThat(a, is(a_ref));
        assertTrue(a != a_ref);
        assertTrue(odb.get(new MoIndexer().key(a)) == null);

        odbTrans.commit();
        assertTrue(odb.get(new MoIndexer().key(a)) != null);

    }

    @Test(expected = OperationMergeFailure.class)
    public void first_add_then_add_same_key_data_then_commit() throws MOSException
    {
        Odb<Mo> odbTrans = odb.startTransaction();

        odbTrans.add(a);
        odbTrans.add(a);
    }

    @Test(expected = OperationMergeFailure.class)
    public void set_an_unexisted_data() throws MOSException
    {
        Odb<Mo> odbTrans = odb.startTransaction();
        odbTrans.set(a);
    }

    @Test
    public void set_and_get_existed_data_then_commit() throws MOSException
    {
        Odb<Mo> odbTrans = odb.startTransaction();
        odbTrans.add(a);

        a.name = "xxx";
        odbTrans.set(a);
        Mo a_ref = odbTrans.get(new MoIndexer().key(a));
        assertTrue(a_ref != null);
        assertThat(a_ref.name, is("xxx"));

        odbTrans.commit();

        a_ref = odb.get(new MoIndexer().key(a));
        assertThat(a_ref.name, is("xxx"));
    }

    @Test
    public void first_add_then_del_unexisted_data_then_commit() throws MOSException
    {
        Odb<Mo> odbTrans = odb.startTransaction();
        odbTrans.add(a);
        odbTrans.remove(a);

        assertTrue(odb.get(new MoIndexer().key(a)) == null);

        odbTrans.commit();

        assertTrue(odb.get(new MoIndexer().key(a)) == null);
    }

    @Test(expected = RemoveNullObjectException.class)
    public void del_unexisted_data() throws MOSException
    {
        Odb<Mo> odbTrans = odb.startTransaction();
        odbTrans.remove(a);
    }

    @Test
    public void add_del_add_unexisted_data_then_commit() throws MOSException
    {
        Odb<Mo> odbTrans = odb.startTransaction();
        odbTrans.add(a);
        odbTrans.remove(a);

        a.name = "xxx";
        odbTrans.add(a);

        Mo a_ref = odbTrans.get(new MoIndexer().key(a));
        assertThat(a_ref.name, is("xxx"));

        assertTrue(odb.get(new MoIndexer().key(a)) == null);

        odbTrans.commit();

        a_ref = odb.get(new MoIndexer().key(a));
        assertThat(a_ref.name, is("xxx"));
    }

    @Test(expected = OperationMergeFailure.class)
    public void add_del_set_unexisted_data() throws MOSException
    {
        Odb<Mo> odbTrans = odb.startTransaction();
        odbTrans.add(a);
        odbTrans.remove(a);
        odbTrans.set(a);
    }

    @Test(expected = RemoveNullObjectException.class)
    public void add_del_del_unexisted_data() throws MOSException
    {
        Odb<Mo> odbTrans = odb.startTransaction();
        odbTrans.add(a);
        odbTrans.remove(a);
        odbTrans.remove(a);
    }

    @Test
    public void add_set_set_unexisted_data() throws MOSException
    {
        Odb<Mo> odbTrans = odb.startTransaction();
        odbTrans.add(a);
        a.name = "xxx";
        odbTrans.set(a);
        a.name = "yyy";
        odbTrans.set(a);

        Mo a_ref = odbTrans.get(new MoIndexer().key(a));
        assertThat(a_ref.name, is("yyy"));

        assertTrue(odb.get(new MoIndexer().key(a)) == null);

        odbTrans.commit();

        a_ref = odb.get(new MoIndexer().key(a));
        assertThat(a_ref.name, is("yyy"));

    }

    @Test
    public void add_set_del_unexisted_data() throws MOSException
    {
        Odb<Mo> odbTrans = odb.startTransaction();
        odbTrans.add(a);
        a.name = "xxx";
        odbTrans.set(a);
        odbTrans.remove(a);

        assertTrue(odbTrans.get(new MoIndexer().key(a)) == null);
        assertTrue(odb.get(new MoIndexer().key(a)) == null);

        odbTrans.commit();
        assertTrue(odb.get(new MoIndexer().key(a)) == null);

    }

    @Test(expected = OperationMergeFailure.class)
    public void add_set_add_unexisted_data() throws MOSException
    {
        Odb<Mo> odbTrans = odb.startTransaction();

        odbTrans.add(a);
        a.name = "xxx";
        odbTrans.set(a);
        odbTrans.add(a);
    }

    @Test
    public void add_del_add_set_unexisted_data() throws MOSException
    {
        Odb<Mo> odbTrans = odb.startTransaction();

        odbTrans.add(a);
        odbTrans.remove(a);
        odbTrans.add(a);
        a.name = "xxx";
        odbTrans.set(a);

        Mo a_ref = odbTrans.get(new MoIndexer().key(a));
        assertThat(a_ref.name, is("xxx"));

        assertTrue(odb.get(new MoIndexer().key(a)) == null);

        odbTrans.commit();

        a_ref = odb.get(new MoIndexer().key(a));
        assertThat(a_ref.name, is("xxx"));

    }

    @Test
    public void add_del_add_del_unexisted_data() throws MOSException
    {
        Odb<Mo> odbTrans = odb.startTransaction();

        odbTrans.add(a);
        odbTrans.remove(a);
        odbTrans.add(a);
        odbTrans.remove(a);

        assertTrue(odbTrans.get(new MoIndexer().key(a)) == null);
        assertTrue(odb.get(new MoIndexer().key(a)) == null);

        odbTrans.commit();

        assertTrue(odbTrans.get(new MoIndexer().key(a)) == null);
        assertTrue(odb.get(new MoIndexer().key(a)) == null);

    }

    @Test(expected = OperationMergeFailure.class)
    public void add_del_add_add_unexisted_data() throws MOSException
    {
        Odb<Mo> odbTrans = odb.startTransaction();

        odbTrans.add(a);
        odbTrans.remove(a);
        odbTrans.add(a);
        odbTrans.add(a);
    }

    /**
     * *********************with rollback*********************
     */
    @Test
    public void cannot_get_any_added_data_after_rollback() throws MOSException
    {
        Odb<Mo> odbTrans = odb.startTransaction();

        odbTrans.add(a);
        odbTrans.add(b);

        assertTrue(odbTrans.get(new MoIndexer().key(a)) != null);
        assertTrue(odbTrans.get(new MoIndexer().key(b)) != null);

        assertTrue(odb.get(new MoIndexer().key(a)) == null);
        assertTrue(odb.get(new MoIndexer().key(b)) == null);

        odbTrans.rollback();

        assertTrue(odbTrans.get(new MoIndexer().key(a)) == null);
        assertTrue(odbTrans.get(new MoIndexer().key(b)) == null);

        assertTrue(odb.get(new MoIndexer().key(a)) == null);
        assertTrue(odb.get(new MoIndexer().key(b)) == null);
    }

    @Test
    public void cannot_get_any_set_data_after_rollback() throws MOSException
    {
        Odb<Mo> odbTrans = odb.startTransaction();

        odbTrans.add(a);
        a.name = "xxx";
        odbTrans.set(a);

        assertTrue(odbTrans.get(new MoIndexer().key(a)) != null);
        assertTrue(odb.get(new MoIndexer().key(a)) == null);

        odbTrans.rollback();

        assertTrue(odbTrans.get(new MoIndexer().key(a)) == null);
        assertTrue(odb.get(new MoIndexer().key(a)) == null);
    }

    @Test
    public void cannot_get_any_del_data_after_rollback() throws MOSException
    {
        Odb<Mo> odbTrans = odb.startTransaction();

        odbTrans.add(a);
        odbTrans.remove(a);

        assertTrue(odbTrans.get(new MoIndexer().key(a)) == null);
        assertTrue(odb.get(new MoIndexer().key(a)) == null);

        odbTrans.rollback();

        assertTrue(odbTrans.get(new MoIndexer().key(a)) == null);
        assertTrue(odb.get(new MoIndexer().key(a)) == null);
    }

    /**
     * *********************two diff transactionId*********************
     */
    @Test
    public void add_two_diff_data_with_diff_transactionId_both_commit() throws MOSException
    {
        Odb<Mo> odbTrans = odb.startTransaction();
        Odb<Mo> bakodbTrans = odb.startTransaction();

        odbTrans.add(a);
        bakodbTrans.add(b);

        assertTrue(odbTrans.get(new MoIndexer().key(a)) != null);
        assertTrue(bakodbTrans.get(new MoIndexer().key(b)) != null);
        assertTrue(odb.get(new MoIndexer().key(a)) == null);
        assertTrue(odb.get(new MoIndexer().key(b)) == null);

        odbTrans.commit();

        assertTrue(odbTrans.get(new MoIndexer().key(a)) != null);
        assertTrue(bakodbTrans.get(new MoIndexer().key(b)) != null);
        assertTrue(odb.get(new MoIndexer().key(a)) != null);
        assertTrue(odb.get(new MoIndexer().key(b)) == null);

        bakodbTrans.commit();

        assertTrue(odbTrans.get(new MoIndexer().key(a)) != null);
        assertTrue(bakodbTrans.get(new MoIndexer().key(b)) != null);
        assertTrue(odb.get(new MoIndexer().key(a)) != null);
        assertTrue(odb.get(new MoIndexer().key(b)) != null);

    }

    @Test(expected = DataUnderOperatingException.class)
    public void failed_add_same_key_data_with_diff_transactionId() throws MOSException
    {
        Odb<Mo> odbTrans = odb.startTransaction();
        Odb<Mo> bakodbTrans = odb.startTransaction();

        odbTrans.add(a);
        bakodbTrans.add(a);
    }

    @Test(expected = DataUnderOperatingException.class)
    public void failed_add_set_same_key_data_with_diff_transactionId() throws MOSException
    {
        Odb<Mo> odbTrans = odb.startTransaction();
        Odb<Mo> bakodbTrans = odb.startTransaction();

        odbTrans.add(a);
        a.name = "xxx";
        bakodbTrans.set(a);
    }

    @Test
    public void add_two_diff_data_with_diff_transactionId_one_commit_one_rollback() throws MOSException
    {
        Odb<Mo> odbTrans = odb.startTransaction();
        Odb<Mo> bakodbTrans = odb.startTransaction();

        odbTrans.add(a);
        bakodbTrans.add(b);

        odbTrans.commit();
        bakodbTrans.rollback();

        assertTrue(odb.get(new MoIndexer().key(a)) != null);
        assertTrue(odb.get(new MoIndexer().key(b)) == null);

    }

    @Test
    public void add_two_diff_data_with_diff_transactionId_one_commit_one_rollback_then_commit()
            throws MOSException
    {
        Odb<Mo> odbTrans = odb.startTransaction();
        Odb<Mo> bakodbTrans = odb.startTransaction();

        odbTrans.add(a);
        odbTrans.rollback();

        a.name = "xxx";
        bakodbTrans.add(a);
        bakodbTrans.commit();

        Mo a_ref = odbTrans.get(new MoIndexer().key(a));
        assertThat(a_ref.name, is("xxx"));

    }

    @Test
    public void add_three_diff_data_with_diff_transactionId_both_commit() throws MOSException
    {
        Odb<Mo> odbTrans = odb.startTransaction();
        Odb<Mo> sndodbTrans = odb.startTransaction();
        Odb<Mo> trdodbTrans = odb.startTransaction();

        odbTrans.add(a);
        sndodbTrans.add(b);
        trdodbTrans.add(c);

        odbTrans.commit();
        sndodbTrans.commit();
        trdodbTrans.commit();

        assertTrue(odb.get(new MoIndexer().key(a)) != null);
        assertTrue(odb.get(new MoIndexer().key(b)) != null);
        assertTrue(odb.get(new MoIndexer().key(c)) != null);
    }

    @Test
    public void add_two_data_with_two_different_transacion_then_set_one_with_another_transationId()
            throws MOSException
    {
        Odb<Mo> odbTrans = odb.startTransaction();
        Odb<Mo> sndodbTrans = odb.startTransaction();
        Odb<Mo> trdodbTrans = odb.startTransaction();

        odbTrans.add(a);
        sndodbTrans.add(b);

        odbTrans.commit();
        sndodbTrans.commit();

        a.name = "xxx";
        trdodbTrans.set(a);
        trdodbTrans.commit();

        Mo a_ref = odb.get(new MoIndexer().key(a));
        assertThat(a_ref.name, is("xxx"));
        assertTrue(odb.get(new MoIndexer().key(b)) != null);
    }
}
