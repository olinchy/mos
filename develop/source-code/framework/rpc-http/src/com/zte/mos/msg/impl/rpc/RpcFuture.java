package com.zte.mos.msg.impl.rpc;

import com.fasterxml.jackson.databind.JsonNode;
import com.zte.mos.msg.framework.operation.IOperation;
import com.zte.mos.msg.framework.operation.IOperationFuture;
import com.zte.mos.msg.framework.operation.IResponse;
import com.zte.mos.msg.impl.rpc.decode.NoDecoderException;
import com.zte.mos.msg.impl.rpc.decode.ResponseDecoder;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by luoqingkai on 15-5-18.
 */
public class RpcFuture<T extends IOperation> implements IOperationFuture {
    private final Future<JsonNode> rawFuture;
    private final T operation;

    public RpcFuture(Future<JsonNode> rawFuture, T operation) {
        this.rawFuture = rawFuture;
        this.operation = operation;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return this.rawFuture.cancel(mayInterruptIfRunning);
    }

    @Override
    public boolean isCancelled() {
        return this.rawFuture.isCancelled();
    }

    @Override
    public boolean isDone() {
        return this.rawFuture.isDone();
    }

    @Override
    public IResponse get() throws InterruptedException, ExecutionException {
        JsonNode responseNode = this.rawFuture.get();
        IResponse response;
        try {
            response = ResponseDecoder.decode(operation.getClass(), responseNode);
            return response;
        } catch (NoDecoderException e) {
            throw new ExecutionException(e);
        }
    }

    @Override
    public IResponse get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }
}
