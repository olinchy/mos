package com.zte.mos.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * Created by olinchy on 4/20/15 for mosjava.
 */
public class ErrorCode implements Serializable
{
    public ErrorCode()
    {
        this(0);
    }

    public ErrorCode(long i)
    {
        this(i, null);
    }

    public ErrorCode(long errorCode, String... keywords)
    {
        this.errorCode = errorCode;
        this.keywords = keywords;
    }

    private long errorCode;
    private String[] keywords;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public String[] getKeywords()
    {
        return keywords;
    }

    public void setKeywords(String[] keywords)
    {
        this.keywords = keywords;
    }

    public long getErrorCode()
    {
        return errorCode;
    }

    public void setErrorCode(long longValue)
    {
        this.errorCode = longValue;
    }
}
