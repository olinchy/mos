package com.odb.database.extended_odb;

import com.odb.database.operstate.DataState;

/**
 * Created by root on 14-11-17.
 */
public class OperationUnit<T>
{
    private T data;
    private DataState state;

    public final int transID;

    public OperationUnit(T data, DataState state, int transID) {
        this.transID = transID;
        this.data = data;
        this.state = state;
    }

    public T getData() {
        return data;
    }

    public DataState getState() {
        return state;
    }
}
