package com.zte.mos.msg.framework.operation;

/**
 * Created by luoqingkai on 15-5-18.
 */
public abstract class AbstractResponse implements IResponse {
    private int result;
    private Throwable e = null;

    public AbstractResponse(int result) {
        this.result = result;
    }

    @Override
    public boolean isSuccess() {
        return result == 0;
    }

    @Override
    public final int getResult() {
        return result;
    }

    @Override
    public void setResult(int result) {
        this.result = result;
    }

    @Override
    public final Throwable getThrowable() {
        return e;
    }

    @Override
    public void setThrowable(Throwable e) {
        this.e = e;
    }
}
