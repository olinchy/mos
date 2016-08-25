package com.zte.mos.msg.framework.except;

/**
 * Created by luoqingkai on 15-5-20.
 */
public class InvalidUrlException extends MsgFrameException {
    public InvalidUrlException() {
        setErrorCode(958002);
    }
}
