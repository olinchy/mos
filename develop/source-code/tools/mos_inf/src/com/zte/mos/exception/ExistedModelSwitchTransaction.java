package com.zte.mos.exception;

import static com.zte.mos.exception.ErrorCodeGetter.EXISTED_MODEL_SWiTCH_TRANSACTION_ERROR;
/**
 * Created by ccy on 5/21/16.
 */
public class ExistedModelSwitchTransaction extends MOSException
{
    public ExistedModelSwitchTransaction()
    {
        super(new ErrorCode(EXISTED_MODEL_SWiTCH_TRANSACTION_ERROR));
    }

    public ExistedModelSwitchTransaction(Throwable cause)
    {
        super(new ErrorCode(EXISTED_MODEL_SWiTCH_TRANSACTION_ERROR), cause.getMessage(), cause);
    }

    public ExistedModelSwitchTransaction(String message)
    {
        super(new ErrorCode(EXISTED_MODEL_SWiTCH_TRANSACTION_ERROR), message);
    }

    public ExistedModelSwitchTransaction(ErrorCode errorCode)
    {
        super(errorCode);
    }

    public ExistedModelSwitchTransaction(ErrorCode errorCode, String message)
    {
        super(errorCode, message);
    }

    public ExistedModelSwitchTransaction(ErrorCode errorCode, String message, Throwable cause)
    {
        super(errorCode, message, cause);
    }

    public ExistedModelSwitchTransaction(String message, Throwable cause)
    {
        super(new ErrorCode(EXISTED_MODEL_SWiTCH_TRANSACTION_ERROR), message, cause);
    }

}
