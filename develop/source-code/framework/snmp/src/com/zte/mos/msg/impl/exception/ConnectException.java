package com.zte.mos.msg.impl.exception;

/**
 * Created by zhangbin10086509 on 15-6-6.
 */
public class ConnectException extends Exception {
    private static final long serialVersionUID = -7336697555560914623L;

    private int errorCode = 0;

    public ConnectException(int errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
