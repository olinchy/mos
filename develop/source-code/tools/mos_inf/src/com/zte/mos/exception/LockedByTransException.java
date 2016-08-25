package com.zte.mos.exception;
import static com.zte.mos.exception.ErrorCodeGetter.LOCKED_BY_TRANS_ERROR;
/**
 * Created by luoqingkai on 15-8-13.
 */
public class LockedByTransException extends MOSException {
    private static ErrorCode errorcode = new ErrorCode(LOCKED_BY_TRANS_ERROR);
    public LockedByTransException() {
        super(errorcode);
    }

    public LockedByTransException(Throwable cause) {
        super(errorcode, cause.getMessage(), cause);
    }

    public LockedByTransException(String message) {
        super(errorcode, message);
    }

    public LockedByTransException(ErrorCode errorCode) {
        super(errorCode);
    }

    public LockedByTransException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public LockedByTransException(ErrorCode errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }

    public LockedByTransException(String message, Throwable cause) {
        super(errorcode, message, cause);
    }
}
