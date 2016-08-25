package com.zte.mos.storage;

import com.zte.mos.exception.ErrorCode;
import com.zte.mos.exception.ErrorCodeGetter;
import com.zte.mos.exception.MOSException;

/**
 * Created by olinchy on 7/1/14 for MO_JAVA.
 */
public class NoParentExistException extends MOSException
{
    public NoParentExistException(String message)
    {
        super(new ErrorCode(ErrorCodeGetter.getErrorCode("NO_PARENT_ERROR")), message);
    }
}
