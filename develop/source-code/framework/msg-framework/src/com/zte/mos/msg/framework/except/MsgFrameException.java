package com.zte.mos.msg.framework.except;


/**
 * Created by luoqingkai on 15-5-14.
 */
public abstract class MsgFrameException extends Exception {
    protected int errorCode;

    public MsgFrameException() {
    }
    public MsgFrameException(String message) {
        super(message);
    }

    public MsgFrameException(String message, Throwable cause) {
        super(message, cause);
    }

    public MsgFrameException(Throwable cause) {
        super(cause);
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

}
