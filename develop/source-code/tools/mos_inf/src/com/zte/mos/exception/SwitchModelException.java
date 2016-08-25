package com.zte.mos.exception;

/**
 * Created by ccy on 5/9/16.
 */
public class SwitchModelException extends MOSException
{
    private static final ErrorCode errorCode = new ErrorCode(ErrorCodeGetter.SWITCH_MODEL_ERROR);
    public SwitchModelException()
    {
        super(errorCode);
    }

    public SwitchModelException(Throwable cause)
    {
        super(errorCode, cause.getMessage(), cause);
    }

    public SwitchModelException(String message)
    {
        super(errorCode, message);
    }

    public SwitchModelException(ErrorCode errorCode)
    {
        super(errorCode);
    }

    public SwitchModelException(ErrorCode errorCode, String message)
    {
        super(errorCode, message);
    }

    public SwitchModelException(ErrorCode errorCode, String message, Throwable cause)
    {
        super(errorCode, message, cause);
    }

    public SwitchModelException(String message, Throwable cause)
    {
        super(errorCode, message, cause);
    }

}
