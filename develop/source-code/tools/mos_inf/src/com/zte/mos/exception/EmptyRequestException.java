package com.zte.mos.exception;

/**
 * Created by qiuhua on 1/6/16.
 */
public class EmptyRequestException extends MOSException
{
    public EmptyRequestException() {
        super(new ErrorCode(ErrorCodeGetter.EMPTY_REQUEST_ERROR));
    }
}
