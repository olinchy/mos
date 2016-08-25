package com.zte.mos.exception;

/**
 * Created by olinchy on 15-5-20.
 */
public class LoginFailedException extends MOSException
{
    public LoginFailedException(Throwable cause, String rpcServer)
    {
        super(new ErrorCode(ErrorCodeGetter.LOGIN_ERROR, rpcServer),
                "login to rpc server failed", cause);
    }
}
