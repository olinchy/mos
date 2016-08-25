package com.zte.mos.msg.framework.operation;

/**
 * Created by luoqingkai on 15-5-6.
 */
public interface IResponse {
    boolean isSuccess();
    int getResult();
    void setResult(int result);
    Throwable getThrowable();
    void setThrowable(Throwable e);
}
