package com.zte.mos.msg.framework.except;

/**
 * Created by luoqingkai on 15-9-28.
 */
public class UserDefineException extends MsgFrameException {
    public UserDefineException(Throwable cause) {
        super(cause);
        setErrorCode(958011);
    }
}
