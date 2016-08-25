package com.zte.container.ext.total;

/**
 * Created by love on 16-1-28.
 */
public class SyncFailedException extends Exception {
    private int code;
    private Throwable e;

    public SyncFailedException(int code) {
        this.code = code;
    }

    public SyncFailedException(int code, Throwable e) {
        this.code = code;
        this.e = e;
    }

    public int getCode() {
        return code;
    }

    public Throwable getThrowable() {
        return e;
    }
}
