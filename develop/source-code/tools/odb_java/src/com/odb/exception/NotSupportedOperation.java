package com.odb.exception;

import com.zte.mos.exception.*;

/**
 * Created by olinchy on 5/16/14.
 */
public class NotSupportedOperation extends MOSException
{
    public NotSupportedOperation()
    {
        this("");
    }

    public NotSupportedOperation(String message)
    {
        super(new ErrorCode(ErrorCodeGetter.getErrorCode("OPERATION_NOT_SUPPORTED")), message);
    }
}
