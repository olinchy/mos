package com.odb.database.operstate;

/**
 * Created by olinchy on 5/20/14.
 */
public class Decision
{
    private final boolean canMerge;
    private IsSuccess isSuccess;
    private OperationStates state;
    private Existence existence;

    public Decision(boolean canMerge, OperationStates state, IsSuccess isSuccess,
            Existence existence)
    {
        this.state = state;
        this.existence = existence;
        this.isSuccess = isSuccess;
        this.canMerge = canMerge;
    }

    public static Decision de(OperationStates state, IsSuccess isSuccess, Existence existence)
    {
        return new Decision(true, state, isSuccess, existence);
    }

    public static Decision de(boolean canMerge, OperationStates state, IsSuccess isSuccess,
            Existence existence)
    {
        return new Decision(canMerge, state, isSuccess, existence);
    }

    public boolean isCanMerge()
    {
        return canMerge;
    }

    public Existence existence()
    {
        return this.existence;
    }

    public boolean isSuccess()
    {
        return this.isSuccess.equals(IsSuccess.success);
    }

    public OperationStates getState()
    {
        return state;
    }

    @Override
    public String toString()
    {
        return "Decision{" +
                "canMerge=" + canMerge +
                ", isSuccess=" + isSuccess +
                ", existence=" + existence +
                '}';
    }
}
