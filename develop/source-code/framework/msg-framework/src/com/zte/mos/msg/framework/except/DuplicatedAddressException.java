package com.zte.mos.msg.framework.except;

/**
 * Created by luoqingkai on 15-5-8.
 */
public class DuplicatedAddressException extends MsgFrameException {
    public DuplicatedAddressException() {
        setErrorCode(958001);
    }

    public DuplicatedAddressException(String message) {
        super(message);
        setErrorCode(958001);
    }
}
