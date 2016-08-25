package com.zte.mos.annotation;

import com.zte.mos.exception.ErrorCode;
import com.zte.mos.exception.ErrorCodeGetter;
import com.zte.mos.exception.MOSException;

/**
 * Created by olinchy on 2/16/15 for mosjava.
 */
public class ValidateException extends MOSException
{
    public ValidateException(String msg)
    {
        super(new ErrorCode(ErrorCodeGetter.getErrorCode("VALIDATE_ERROR")), msg);
    }

    public ValidateException(String s, MOSException e)
    {
        super(s, e);
    }
}
