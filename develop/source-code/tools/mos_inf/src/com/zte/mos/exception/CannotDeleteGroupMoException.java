package com.zte.mos.exception;

/**
 * Created by olinchy on 15-6-10.
 */
public class CannotDeleteGroupMoException extends MOSException
{
    public CannotDeleteGroupMoException(String message)
    {
        super(new ErrorCode(ErrorCodeGetter.DELETE_GROUP_ERROR), message);
    }
}
