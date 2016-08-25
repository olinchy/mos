package com.zte.mos.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

/**
 * Created by olinchy on 4/20/15 for mosjava.
 */
public class MOSException extends Exception
{

    protected String message;
    protected ErrorCode errorCode;
    private String className;

    public MOSException()
    {
        this("");
    }

    public MOSException(Throwable cause)
    {
        this(new ErrorCode(ErrorCodeGetter.getErrorCode("GENERAL_ERROR")), "", cause);
    }

    public MOSException(String message)
    {
        this(new ErrorCode(ErrorCodeGetter.getErrorCode("GENERAL_ERROR")), message);
    }

    public MOSException(ErrorCode errorCode)
    {
        this(errorCode, "");
    }

    public MOSException(ErrorCode errorCode, String message)
    {
        this(errorCode, message, null);
    }

    public MOSException(ErrorCode errorCode, String message, Throwable cause)
    {
        this.errorCode = errorCode;
        this.message = message;
        this.className = this.getClass().getName();
        if (cause != null)
            this.initCause(cause);
    }

    public MOSException(String message, Throwable cause)
    {
        this(new ErrorCode(ErrorCodeGetter.getErrorCode("GENERAL_ERROR")), message, cause);
    }

    @Override
    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public ErrorCode getErrorCode()
    {
        return this.errorCode;
    }

    public void setErrorCode(ErrorCode errorCode)
    {
        this.errorCode = errorCode;
    }

    @Override
    @JsonIgnore
    public StackTraceElement[] getStackTrace()
    {
        return super.getStackTrace();
    }

    @Override
    @JsonIgnore
    public String getLocalizedMessage()
    {
        return super.getLocalizedMessage();
    }

    @Override
    @JsonInclude(NON_EMPTY)
    public synchronized Throwable getCause()
    {
        return super.getCause();
    }

    public String getClassName()
    {
        return this.className;
    }

    public void setClassName(String className)
    {
        this.className = className;
    }

    @Override
    public String toString()
    {
        return "MOSException{" +
                "message='" + message + '\'' +
                ", className='" + className + '\'' +
                '}';
    }
}
