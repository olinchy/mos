package com.zte.mos.exception;

/**
 * Created by luoqingkai on 15-10-9.
 */
public class ModelNotSpecException extends MOSException {
    public ModelNotSpecException(String msg) {
        super(new ErrorCode(ErrorCodeGetter.MODEL_NOT_SPEC_ERROR), msg);
    }

    public ModelNotSpecException(String message, Throwable cause) {
        super(message, cause);
    }
}
