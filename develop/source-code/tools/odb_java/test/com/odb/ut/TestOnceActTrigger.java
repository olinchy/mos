package com.odb.ut;

import com.odb.database.Odb;
import com.odb.database.trigger.*;
import com.odb.exception.OperationMergeFailure;
import com.odb.ut.stub.*;
import com.zte.mos.exception.MOSException;
import org.junit.*;

import java.util.HashMap;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by olinchy on 5/22/14 for MO_JAVA.
 */
public class TestOnceActTrigger
{
    private HashMap<TriggerTiming, SpyTrigger> spy = new HashMap<TriggerTiming, SpyTrigger>();
    private Mo b;
    private Mo a;
    private Odb<Mo> odb;
    private Odb<Mo> tran_level_1;
    private Odb<Mo> tran_level_2;

    @Before
    public void setUp() throws Exception
    {
        spy.clear();

        a = new Mo("a");
        b = new Mo("b");
        odb = MoDB.instance();
        tran_level_1 = odb.startTransaction();
        tran_level_2 = tran_level_1.startTransaction();
        for (TriggerTiming timming : TriggerTiming.values())
        {
            odb.regObjTrigger(Mo.class, timming, new SpyTrigger(timming));
        }
    }

    @After
    public void tearDown() throws Exception
    {
        odb.clearTrigger();

    }

    @Test
    public void testAdd() throws MOSException
    {
        tran_level_2.add(a);
        tran_level_1.add(b);
        SpyTrigger beforeAdd = spy.get(TriggerTiming.beforeAdd);
        SpyTrigger afterAdd = spy.get(TriggerTiming.afterAdd);

        assertThat(beforeAdd.getCount(a), is(1));
        assertThat(beforeAdd.getCount(b), is(1));
        assertThat(afterAdd.getCount(a), is(1));
        assertThat(afterAdd.getCount(b), is(1));

        tran_level_2.commit();

        assertThat(beforeAdd.getCount(a), is(1));
        assertThat(beforeAdd.getCount(b), is(1));
        assertThat(afterAdd.getCount(a), is(1));
        assertThat(afterAdd.getCount(b), is(1));

        SpyTrigger preCom_add = spy.get(TriggerTiming.beforeCommit_add);
        SpyTrigger postCom_add = spy.get(TriggerTiming.afterCommit_add);

        assertThat(preCom_add.getCount(a), is(0));
        assertThat(preCom_add.getCount(b), is(0));
        assertThat(postCom_add.getCount(a), is(0));
        assertThat(postCom_add.getCount(b), is(0));

        tran_level_1.commit();

        assertThat(preCom_add.getCount(a), is(1));
        assertThat(preCom_add.getCount(b), is(1));
        assertThat(postCom_add.getCount(a), is(1));
        assertThat(postCom_add.getCount(b), is(1));

    }

    @Test(expected = OperationMergeFailure.class)
    public void testSetFailure() throws MOSException
    {
        tran_level_1.add(a);
        a.name = "x";
        tran_level_2.set(b);
    }

    @Test
    public void testSet() throws MOSException
    {
        odb.add(a);
        a.name = "x";
        tran_level_2.set(a);

        SpyTrigger beforeSet = spy.get(TriggerTiming.beforeSet);
        SpyTrigger afterSet = spy.get(TriggerTiming.afterSet);

        assertThat(beforeSet.getCount(a), is(1));
        assertThat(afterSet.getCount(a), is(1));

        tran_level_1.commit();
        SpyTrigger preCom_set = spy.get(TriggerTiming.beforeCommit_set);
        SpyTrigger postCom_set = spy.get(TriggerTiming.afterCommit_set);

        assertThat(preCom_set.getCount(a), is(1));
        assertThat(postCom_set.getCount(a), is(1));
    }

    @Test
    public void testRemove() throws MOSException
    {
        tran_level_1.add(a);
        tran_level_2.remove(a);

        SpyTrigger beforeDel = spy.get(TriggerTiming.beforeDelete);
        SpyTrigger afterDel = spy.get(TriggerTiming.afterDelete);

        assertThat(beforeDel.getCount(a), is(1));
        assertThat(afterDel.getCount(a), is(1));

        SpyTrigger preCom_del = spy.get(TriggerTiming.beforeCommit_remove);
        SpyTrigger postCom_del = spy.get(TriggerTiming.afterCommit_remove);

        tran_level_1.commit();
        assertThat(preCom_del.getCount(a), is(0));
        assertThat(postCom_del.getCount(a), is(0));

        odb.add(a);
        tran_level_1 = odb.startTransaction();
        tran_level_1.remove(a);
        tran_level_1.commit();
        assertThat(preCom_del.getCount(a), is(1));
        assertThat(postCom_del.getCount(a), is(1));
    }

    private class SpyTrigger implements Trigger<Mo>
    {
        private HashMap<Object, Integer> map = new HashMap<Object, Integer>();

        public SpyTrigger(TriggerTiming timming)
        {
            spy.put(timming, this);
        }

        public <T> int getCount(T t)
        {
            return map.get(t) == null ? 0 : map.get(t);
        }

        @Override
        public void activate(Mo t, Odb<Mo> odb) throws MOSException
        {
            Integer count = map.get(t);
            if (count == null)
            {
                count = 0;
            }
            count++;
            map.put(t, count);
        }

        @Override
        public void register(Odb<Mo> odb)
        {

        }
    }
}
