package com.zte.mos.msg.framework.except;

/**
 * Created by luoqingkai on 15-9-24.
 * Exception happend when send msg.
 */
public class LinkException extends MsgFrameException {
    public LinkException(String message, Throwable cause) {
        super(message, cause);
        setErrorCode(958003);
    }

    public LinkException(Throwable cause) {
        super(cause);
        setErrorCode(958003);
    }
}
