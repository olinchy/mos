package com.zte.mos.exception;

/**
 * Created by luoqingkai on 15-4-3.
 */
public class RemoteOperException extends MOSException
{
    public RemoteOperException(int errorCode, String errMsg)
    {
        super(new ErrorCode(errorCode), errMsg);
    }
}
