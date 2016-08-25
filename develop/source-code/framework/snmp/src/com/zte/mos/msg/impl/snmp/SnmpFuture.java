package com.zte.mos.msg.impl.snmp;

import com.zte.mos.msg.framework.operation.IOperation;
import com.zte.mos.msg.framework.operation.IOperationFuture;
import com.zte.mos.msg.framework.operation.IResponse;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by luoqingkai on 15-5-21.
 */
public class SnmpFuture<T extends IOperation> implements IOperationFuture {
    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public IResponse get() throws InterruptedException, ExecutionException {
        return null;
    }

    @Override
    public IResponse get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }
}
