package com.zte.mos.exception;

/**
 * Created by olinchy on 16-2-14.
 */
public class NoTransactionIsNotAllowedInReferenceDB extends MOSException
{
    public NoTransactionIsNotAllowedInReferenceDB() {
        super(new ErrorCode(ErrorCodeGetter.NO_TRANSACTION_ALLOWED_IN_REFERNCE_DB_ERROR));
    }
}
