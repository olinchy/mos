package com.zte.mos.exception;

/**
 * Created by olinchy on 15-5-20.
 */
public class ReadPersistentException extends PersistentException
{
    public ReadPersistentException(String entityName, Exception e)
    {
        super(new ErrorCode(ErrorCodeGetter.READ_PERSISTENT_ERROR, entityName), "",
                e);
    }
}
