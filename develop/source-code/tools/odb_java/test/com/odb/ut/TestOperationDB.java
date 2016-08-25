package com.odb.ut;

import com.odb.database.extended_odb.*;
import com.odb.exception.NotSupportedOperation;
import com.odb.ut.stub.*;
import com.zte.mos.exception.MOSException;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by olinchy on 5/19/14.
 */
public class TestOperationDB
{

    @Test
    public void testAdd() throws MOSException
    {
        OperatingSet<Mo> set = new OperatingSet<Mo>(new MoIndexer());
        SpyParentDB<Mo> sodb = new SpyParentDB<Mo>(new MoIndexer(), set);
        OperationODB<Mo> op = new OperationODB<Mo>(sodb);
        op.add(new Mo("a"));
        assertThat(sodb.toString(), is("beforeAdd doAdd afterAdd"));
    }

    @Test
    public void testDel() throws MOSException
    {
        OperatingSet<Mo> set = new OperatingSet<Mo>(new MoIndexer());
        SpyParentDB<Mo> sodb = new SpyParentDB<Mo>(new MoIndexer(), set);
        OperationODB<Mo> op = new OperationODB<Mo>(sodb);
        op.remove(new Mo("a"));
        assertThat(sodb.toString(), is("beforeDel doDel afterDel"));
    }

    @Test
    public void testSet() throws MOSException
    {
        OperatingSet<Mo> set = new OperatingSet<Mo>(new MoIndexer());
        SpyParentDB<Mo> sodb = new SpyParentDB<Mo>(new MoIndexer(), set);
        OperationODB<Mo> op = new OperationODB<Mo>(sodb);
        op.set(new Mo("a"));
        assertThat(sodb.toString(), is("beforeSet doSet afterSet"));
    }

    @Test(expected = NotSupportedOperation.class)
    public void testStartTransaction() throws Exception
    {
        OperatingSet<Mo> set = new OperatingSet<Mo>(new MoIndexer());
        SpyParentDB<Mo> sodb = new SpyParentDB<Mo>(new MoIndexer(), set);
        OperationODB<Mo> op = new OperationODB<Mo>(sodb);

        op.startTransaction();
    }
}
