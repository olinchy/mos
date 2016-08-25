package com.zte.mos.msg.framework.except;

/**
 * Created by luoqingkai on 15-11-3.
 */
public class NotConnectException extends MsgFrameException{
    public NotConnectException(String message) {
        super(message);
        setErrorCode(958004);
    }
}
