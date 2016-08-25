package com.odb.ut.stub;

import com.odb.database.*;
import com.odb.database.extended_odb.CommittedODB;
import com.odb.database.extended_odb.OperatingSet;
import com.zte.mos.domain.Group;
import com.zte.mos.exception.MOSException;
import com.zte.mos.persistent.Log;

import java.util.LinkedList;

/**
 * Created by olinchy on 5/19/14.
 */
public class SpyParentDB<T extends Group> extends CommittedODB<T>
{
    private StringBuffer buffer = new StringBuffer("");

    public SpyParentDB(Indexer<T> primaryIndex, OperatingSet<T> set)
    {
        super(primaryIndex, set);
    }

    @Override
    public String toString()
    {
        return buffer.toString().trim();
    }

    @Override
    public Odb<T> startTransaction() throws MOSException
    {
        return this;
    }

    @Override
    public void add(T t) throws MOSException
    {

    }

    @Override
    public void set(T t) throws MOSException
    {

    }

    @Override
    public void remove(Key key) throws MOSException
    {

    }

    @Override
    public T get(Key key) throws MOSException
    {
        return null;
    }

    @Override
    public void remove(T t) throws MOSException
    {

    }

    @Override
    public LinkedList<Log<T>> commit() throws MOSException
    {
        return null;
    }

    @Override
    public void rollback() throws MOSException
    {

    }

    @Override
    protected void doAdd(T t) throws MOSException
    {
        buffer.append("doAdd ");
    }

    @Override
    protected void doSet(T t) throws MOSException
    {
        buffer.append("doSet ");
    }

    @Override
    protected void afterDel(T t) throws MOSException
    {
        buffer.append("afterDel ");
    }

    @Override
    protected void doDel(T t) throws MOSException
    {
        buffer.append("doDel ");
    }

    @Override
    protected void beforeDel(T t) throws MOSException
    {
        buffer.append("beforeDel ");
    }

    @Override
    protected void afterSet(T t) throws MOSException
    {
        buffer.append("afterSet ");
    }

    @Override
    protected void beforeSet(T t) throws MOSException
    {
        buffer.append("beforeSet ");
    }

    @Override
    protected void afterAdd(T t) throws MOSException
    {
        buffer.append("afterAdd ");
    }

    @Override
    protected void beforeAdd(T t) throws MOSException
    {
        buffer.append("beforeAdd ");
    }
}
