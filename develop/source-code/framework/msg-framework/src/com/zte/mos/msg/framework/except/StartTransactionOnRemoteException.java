package com.zte.mos.msg.framework.except;

/**
 * Created by olinchy on 16-4-19.
 */
public class StartTransactionOnRemoteException extends MsgFrameException
{
    public StartTransactionOnRemoteException(String message)
    {
        super(message);
        setErrorCode(958008);
    }
}
