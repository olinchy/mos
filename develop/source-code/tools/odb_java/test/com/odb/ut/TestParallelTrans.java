package com.odb.ut;

import com.odb.database.Odb;
import com.odb.database.trigger.*;
import com.odb.exception.DataUnderOperatingException;
import com.odb.ut.stub.*;
import com.zte.mos.exception.MOSException;
import org.junit.*;

/**
 * Created by olinchy on 5/23/14 for MO_JAVA.
 */
public class TestParallelTrans
{
    @Test(expected = DataUnderOperatingException.class)
    public void testCommit() throws MOSException
    {
        Odb<Mo> odb = MoDB.instance();
        Odb<Mo> trans_1 = odb.startTransaction();
        Odb<Mo> trans_2 = odb.startTransaction();

        trans_1.add(new Mo("a"));
        trans_2.add(new Mo("a"));
    }

    @Test
    public void testRollBack() throws MOSException
    {
        Odb<Mo> odb = MoDB.instance();
        Odb<Mo> trans_1 = odb.startTransaction();
        Odb<Mo> trans_2 = odb.startTransaction();

        trans_1.add(new Mo("a"));
        trans_1.rollback();

        trans_2.add(new Mo("a"));
    }

    @Test
    @SuppressWarnings("ALL")
    public void testOperDBException() throws MOSException
    {

        Odb<Mo> odb = MoDB.instance();
        odb.regObjTrigger(Mo.class, TriggerTiming.beforeCommit_add, new StubTrigger());
        try
        {
            odb.add(new Mo("a"));
        }
        catch (MOSException e)
        {

        }

        odb.startTransaction().add(new Mo("a"));
    }

    @After
    public void tearDown() throws MOSException
    {
        MoDB.instance().clearTrigger();
    }

    private class StubTrigger implements Trigger<Mo>
    {
        @Override
        public void activate(Mo mo, Odb<Mo> odb) throws MOSException
        {
            throw new MOSException();
        }

        @Override
        public void register(Odb<Mo> odb)
        {

        }
    }
}
