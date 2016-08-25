package com.zte.mos.exception;
import static com.zte.mos.exception.ErrorCodeGetter.*;
/**
 * Created by olinchy on 15-7-7.
 */
public class BerkeleyDBException extends MOSException
{
    public BerkeleyDBException(String s)
    {
        super(new ErrorCode(SAVING_TO_DATABASE_ERROR), s);
    }

    public BerkeleyDBException(Throwable s)
    {
        super(new ErrorCode(SAVING_TO_DATABASE_ERROR), s.getMessage(), s);
    }
}
