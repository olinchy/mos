package com.zte.mos.msg.framework.except;

/**
 * Created by luoqingkai on 15-5-7.
 */
public class UnsupportedProtocolException extends MsgFrameException{

    public UnsupportedProtocolException(String message) {
        super(message);
        setErrorCode(958010);
    }
}
