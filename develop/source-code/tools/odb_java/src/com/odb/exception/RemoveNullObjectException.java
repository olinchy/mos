package com.odb.exception;

import com.zte.mos.exception.*;

/**
 * Created by olinchy on 5/22/14 for MO_JAVA.
 */
public class RemoveNullObjectException extends MOSException
{
    public RemoveNullObjectException(String keyword)
    {
        super(new ErrorCode(ErrorCodeGetter.getErrorCode("REMOVE_NULL_IN_ODB"), keyword));
    }

    public RemoveNullObjectException()
    {
    }
}
