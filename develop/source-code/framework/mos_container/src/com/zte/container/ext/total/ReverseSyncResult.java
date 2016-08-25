package com.zte.container.ext.total;

/**
 * Created by love on 16-3-31.
 */
public class ReverseSyncResult
{
    public static ReverseSyncResult success = new ReverseSyncResult(944003);
    public static ReverseSyncResult failed = new ReverseSyncResult(944004);
    public static ReverseSyncResult timeout = new ReverseSyncResult(944009);
    public static ReverseSyncResult syncConflict = new ReverseSyncResult(944010);

    private int errorCode;

    ReverseSyncResult(int errorCode)
    {
        this.errorCode = errorCode;
    }

    public int getErrorCode()
    {
        return errorCode;
    }
}
