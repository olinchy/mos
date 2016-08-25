package com.odb.database.operstate;

import com.odb.database.extended_odb.*;
import com.zte.mos.domain.Group;

/**
 * Created by olinchy on 5/20/14.
 */
@SuppressWarnings("unchecked")
public enum OperationStates
{
    StNull(new NullMaker()), StOrigin(new OriginMaker()), StAdd(new AddMaker()), StSet(
        new SetMaker()), StRemove(
        new RemoveMaker()), StReplace(new ReplaceMaker());
    private StateMaker maker;

    OperationStates(StateMaker maker)
    {
        this.maker = maker;
    }
    public static <T> OperationStates valueOf(DataState state)
    {
        return valueOf(state.getClass().getSimpleName());
    }

    public <T extends Group> DataState<T> instance(T t)
    {
        return maker.make(t);
    }

    private static class NullMaker implements StateMaker
    {
        @Override public <T extends Group> DataState<T> make(T t)
        {
            return new StNull(t);
        }
    }

    private static class AddMaker implements StateMaker
    {

        @Override public <T extends Group> DataState<T> make(T t)
        {
            return new StAdd(t);
        }
    }

    private static class SetMaker implements StateMaker
    {
        @Override public <T extends Group> DataState<T> make(T t)
        {
            return new StSet(t);
        }
    }

    private static class RemoveMaker implements StateMaker
    {
        @Override public <T extends Group> DataState<T> make(T t)
        {
            return new StRemove(t);
        }
    }

    private static class ReplaceMaker implements StateMaker
    {
        @Override public <T extends Group> DataState<T> make(T t)
        {
            return new StReplace(t);
        }
    }

    private static class OriginMaker implements StateMaker
    {
        @Override public <T extends Group> DataState<T> make(T t)
        {
            return new StOrigin(t);
        }
    }
}
