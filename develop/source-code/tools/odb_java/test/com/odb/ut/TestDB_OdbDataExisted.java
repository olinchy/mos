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
public class TestDB_OdbDataExisted
{
    private Odb<Mo> odb;
    private Mo a;

    @Before
    public void before() throws MOSException
    {
        a = new Mo("a");
        odb = MoDB.instance();

        Odb<Mo> odbTrans = odb.startTransaction();

        odbTrans.add(a);
        odbTrans.commit();
    }

    @Test(expected = OperationMergeFailure.class)
    public void add_an_exited_data() throws MOSException
    {
        Odb<Mo> odbTrans = odb.startTransaction();
        odbTrans.add(a);
    }

    @Test
    public void set_an_existed_data() throws MOSException
    {
        Odb<Mo> odbTrans = odb.startTransaction();

        a.name = "xxx";
        odbTrans.set(a);
        odbTrans.commit();

        Mo a_ref = odb.get(new MoIndexer().key(a));
        assertThat(a_ref.name, is("xxx"));
    }

    @Test
    public void del_an_existed_data() throws MOSException
    {
        Odb<Mo> odbTrans = odb.startTransaction();

        odbTrans.remove(a);
        odbTrans.commit();

        assertTrue(odb.get(new MoIndexer().key(a)) == null);
    }

    @Test
    public void del_then_add_an_existed_data() throws MOSException
    {
        Odb<Mo> odbTrans = odb.startTransaction();

        odbTrans.remove(a);
        a.name = "xxx";
        odbTrans.add(a);
        odbTrans.commit();

        Mo a_ref = odb.get(new MoIndexer().key(a));
        assertThat(a_ref.name, is("xxx"));
    }

    @Test(expected = OperationMergeFailure.class)
    public void del_then_set_an_existed_data() throws MOSException
    {
        Odb<Mo> odbTrans = odb.startTransaction();

        odbTrans.remove(a);
        a.name = "xxx";
        odbTrans.set(a);
    }

    @Test(expected = RemoveNullObjectException.class)
    public void del_then_del_an_existed_data() throws MOSException
    {
        Odb<Mo> odbTrans = odb.startTransaction();

        odbTrans.remove(a);
        odbTrans.remove(a);
    }

    @Test(expected = OperationMergeFailure.class)
    public void set_then_add_an_existed_data() throws MOSException
    {
        Odb<Mo> odbTrans = odb.startTransaction();

        a.name = "xxx";
        odbTrans.set(a);
        odbTrans.add(a);
    }

    @Test
    public void set_then_set_an_existed_data() throws MOSException
    {
        Odb<Mo> odbTrans = odb.startTransaction();

        a.name = "xxx";
        odbTrans.set(a);
        a.name = "yyy";
        odbTrans.set(a);

        odbTrans.commit();

        Mo a_ref = odb.get(new MoIndexer().key(a));
        assertThat(a_ref.name, is("yyy"));
    }

    @Test
    public void set_then_del_an_existed_data() throws MOSException
    {
        Odb<Mo> odbTrans = odb.startTransaction();

        a.name = "xxx";
        odbTrans.set(a);
        odbTrans.remove(a);

        odbTrans.commit();

        assertTrue(odb.get(new MoIndexer().key(a)) == null);
    }

    /**
     * *********************with rollback*********************
     */
    @Test
    public void get_the_old_data_after_rollback_set() throws MOSException
    {
        Odb<Mo> odbTrans = odb.startTransaction();

        a.name = "xxx";
        odbTrans.set(a);

        odbTrans.rollback();

        Mo a_ref = odb.get(new MoIndexer().key(a));
        assertThat(a_ref.name, is("a"));
    }

    @Test
    public void get_the_old_data_after_rollback_del() throws MOSException
    {
        Odb<Mo> odbTrans = odb.startTransaction();

        odbTrans.remove(a);

        odbTrans.rollback();

        Mo a_ref = odb.get(new MoIndexer().key(a));
        assertThat(a_ref.name, is("a"));
    }

    @Test
    public void get_the_old_data_after_rollback_del_add() throws MOSException
    {
        Odb<Mo> odbTrans = odb.startTransaction();

        odbTrans.remove(a);
        a.name = "xxx";
        odbTrans.add(a);
        odbTrans.rollback();

        Mo a_ref = odb.get(new MoIndexer().key(a));
        assertThat(a_ref.name, is("a"));
    }

    @Test(expected = DataUnderOperatingException.class)
    public void failed_set_del_same_key_data_with_diff_transactionId() throws MOSException
    {
        Odb<Mo> odbTrans = odb.startTransaction();
        Odb<Mo> bakodbTrans = odb.startTransaction();

        a.name = "xxx";
        odbTrans.set(a);
        Mo a_ref = odb.get(new MoIndexer().key(a));
        assertThat(a_ref.name, is("a"));
        bakodbTrans.remove(a);
    }

}
