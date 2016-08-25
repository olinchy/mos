package com.odb.exception;

import com.zte.mos.exception.*;

/**
 * Created by olinchy on 5/20/14.
 */
public class OperationMergeFailure extends MOSException
{
    public OperationMergeFailure(String message)
    {
        super(new ErrorCode(ErrorCodeGetter.getErrorCode("ODB_MERGE_ERROR")), message);
    }
}
