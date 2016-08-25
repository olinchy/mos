package com.zte.mos.msg.impl.rpc;

import com.zte.mos.msg.framework.except.NotConnectException;
import com.zte.mos.msg.framework.operation.PingResponse;

/**
 * Created by luoqingkai on 15-12-8.
 */
public class OldRpcPingTask implements Runnable {

    private static final OldPingFutureProcessor processor = new OldPingFutureProcessor();

    private final RpcSession session;

    public OldRpcPingTask(RpcSession session)
    {
        this.session = session;
    }

    @Override
    public void run() {
        try
        {
            PingResponse res = session.ping();
            processor.add(res);
        }
        catch (NotConnectException e)
        {
            e.printStackTrace();
        }
    }
}
