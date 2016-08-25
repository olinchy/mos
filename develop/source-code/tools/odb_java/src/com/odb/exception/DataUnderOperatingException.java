package com.odb.exception;

import com.zte.mos.exception.*;

/**
 * Created by olinchy on 5/23/14 for MO_JAVA.
 */
public class DataUnderOperatingException extends MOSException
{
    public DataUnderOperatingException()
    {
        super();
    }

    public DataUnderOperatingException(String s)
    {
        super(new ErrorCode(ErrorCodeGetter.getErrorCode("DATA_UNDER_OPERATING")), s);
    }
}
