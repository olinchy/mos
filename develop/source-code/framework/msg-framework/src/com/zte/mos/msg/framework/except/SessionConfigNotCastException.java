package com.zte.mos.msg.framework.except;

/**
 * Created by luoqingkai on 15-5-20.
 */
public class SessionConfigNotCastException extends MsgFrameException{
    public SessionConfigNotCastException() {
        setErrorCode(958007);
    }
}
