package com.zte.mos.exception;

/**
 * Created by olinchy on 15-5-20.
 */
public class PersistentException extends MOSException
{
    public PersistentException()
    {
        super(new ErrorCode(ErrorCodeGetter.PERSISTENT_ERROR));
    }

    public PersistentException(ErrorCode errorCode, String message, Throwable cause)
    {
        super(errorCode, message, cause);
    }
}
