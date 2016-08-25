package com.zte.mos.exception;
import static com.zte.mos.exception.ErrorCodeGetter.COMMUNICATION_ERROR;
/**
 * Created by olinchy on 15-5-20.
 */
public class SendToNEException extends MOSException
{
    public SendToNEException(Throwable cause)
    {
        super(new ErrorCode(COMMUNICATION_ERROR), "", cause);
    }
}
