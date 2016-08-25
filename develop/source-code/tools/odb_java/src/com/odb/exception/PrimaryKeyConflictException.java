package com.odb.exception;

import com.zte.mos.exception.*;

/**
 * Created by root on 15-4-20.
 */
public class PrimaryKeyConflictException extends MOSException
{

    public PrimaryKeyConflictException(String msg)
    {
        super(new ErrorCode(ErrorCodeGetter.getErrorCode("PRIMARY_KEY_CONFLICT_ERROR")), msg);
    }

}
