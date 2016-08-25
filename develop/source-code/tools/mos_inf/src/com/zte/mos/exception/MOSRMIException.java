package com.zte.mos.exception;

/**
 * Created by olinchy on 15-5-20.
 */
public class MOSRMIException extends MOSException
{
    public MOSRMIException(Throwable cause, String remoteAddress)
    {
        super(new ErrorCode(ErrorCodeGetter.RMI_ERROR, remoteAddress), "",
                cause);
    }
}
