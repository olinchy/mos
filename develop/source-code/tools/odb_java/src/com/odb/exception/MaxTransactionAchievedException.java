package com.odb.exception;

import com.zte.mos.exception.*;

/**
 * Created by olinchy on 6/5/14 for MO_JAVA.
 */
public class MaxTransactionAchievedException extends MOSException
{
    public MaxTransactionAchievedException()
    {
        super(new ErrorCode(ErrorCodeGetter.getErrorCode("MAX_TRANS_NUM_ACHIEVED")));
    }
}
