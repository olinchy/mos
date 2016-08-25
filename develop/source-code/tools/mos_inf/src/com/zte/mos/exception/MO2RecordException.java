package com.zte.mos.exception;

/**
 * Created by olinchy on 15-5-20.
 */
public class MO2RecordException extends PersistentException
{
    public MO2RecordException(Exception cause)
    {
        super(new ErrorCode(ErrorCodeGetter.MO_TO_RECORD_ERROR), "", cause);
    }
}
