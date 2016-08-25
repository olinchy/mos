package com.zte.mos.msg.framework.except;

/**
 * Created by luoqingkai on 15-5-12.
 */
public class UnregisteredException extends MsgFrameException {

    public UnregisteredException(String message) {
        super(message);
        setErrorCode(958009);
    }
}
