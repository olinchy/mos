package com.odb.database.operstate;

import com.odb.database.Key;
import com.odb.database.Odb;
import com.odb.database.extended_odb.ExtendedODB;
import com.odb.database.extended_odb.OperatingSet;
import com.odb.database.extended_odb.PostProcess;
import com.odb.database.operation.OperTypes;
import com.odb.exception.OperationMergeFailure;
import com.zte.mos.domain.Group;
import com.zte.mos.exception.MOSException;
import com.zte.mos.persistent.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by olinchy on 10/16/14 for MO_JAVA.
 */
public class OperationStatePack<T extends Group> implements OperationState<T>
{
    private DataState<T> dataState;
    private ArrayList<ChildrenState<T>> childrenStates = new ArrayList<ChildrenState<T>>();
    private Decision decision;

    public OperationStatePack(DataState<T> state)
    {
        dataState = state;
    }

    public DataState getState()
    {
        return dataState;
    }

    @Override
    public void post(PostProcess postProcess) throws MOSException
    {
        dataState.post(postProcess);
    }

    @Override
    public T data()
    {
        T data = dataState.data();
        for (ChildrenState state : childrenStates)
        {
            state.merge(data);
        }

        return data;
    }

    @Override
    public void commitTo(Odb odb) throws MOSException
    {
        dataState.commitTo(odb);
        if (!dataState.isDataDestroyed())
        {
            for (ChildrenState state : childrenStates)
            {
                state.commitTo(odb);
            }
        }
    }

    @Override
    public boolean isDataDestroyed()
    {
        return dataState.isDataDestroyed();
    }

    @Override
    public void logTo(List<Log<T>> logs, ExtendedODB<T> parent) throws MOSException
    {
        this.dataState.logTo(logs, parent);
    }

    @Override
    public OperationState<T> merge(OperTypes oper, T t) throws OperationMergeFailure
    {
        this.decision = OperationDecision.merge(oper, t, dataState);
        switch (oper)
        {
            case addChild:
                if (decision.isSuccess())
                {
                    childrenStates.add(new AddChild(dataState.data(), t));
                }
                break;
            case rmChild:
                if (!isDataDestroyed())
                {
                    childrenStates.add(new RemoveChild(dataState.data(), t));
                }
                break;
            default:
                dataState = decision.getState().instance(t);
                break;
        }
        return this;
    }

    @Override
    public void decideExistence(Map<Key, OperationState<T>> store, Key key)
    {
        if (decision != null)
        {
            decision.existence().decide(store, key, this);
        }
    }

    @Override
    public void decideExistence(OperatingSet set, Key key, Odb<T> odb)
    {
        if (decision != null)
        {
            decision.existence().decide(set, key, odb);
        }
    }

    @Override
    public String toString()
    {
        return "OperationStatePack{" +
                "dataState=" + dataState +
                ", childrenStates=" + childrenStates +
                ", decision=" + decision +
                '}';
    }
}
