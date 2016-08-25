package com.zte.mos.msg.framework.except;

/**
 * Created by luoqingkai on 15-9-24.
 * remote is alive, but the result is abnormal.
 */
public class RemoteException extends MsgFrameException {

    public RemoteException(int errorCode) {
        this.errorCode = errorCode;
        setErrorCode(958006);
    }
}
