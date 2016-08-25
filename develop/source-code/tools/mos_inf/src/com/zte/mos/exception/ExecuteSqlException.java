package com.zte.mos.exception;

import static com.zte.mos.exception.ErrorCodeGetter.SAVING_TO_DATABASE_ERROR;

/**
 * Created by olinchy on 15-5-20.
 */
public class ExecuteSqlException extends PersistentException
{
    public ExecuteSqlException(String sql, Exception e)
    {
        super(new ErrorCode(SAVING_TO_DATABASE_ERROR, sql), "", e);
    }
}
