package com.zte.mos.msg.impl.exception;

/**
 * Created by ccy on 3/4/16.
 */
public class ReverseException extends Exception
{
    private int errorCode = 0;

    public ReverseException(int errorCode, String message)
    {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode()
    {
        return errorCode;
    }
}

