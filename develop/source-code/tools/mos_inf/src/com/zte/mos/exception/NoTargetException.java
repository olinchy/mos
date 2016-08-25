package com.zte.mos.exception;

/**
 * Created by luoqingkai on 15-9-30.
 */
public class NoTargetException extends MOSException {
    public NoTargetException(String message) {
        super(new ErrorCode(ErrorCodeGetter.NO_TARGET_ERROR), message);
    }
}
