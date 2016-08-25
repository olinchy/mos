package com.zte.mos.msg.impl.rpc;

/**
 * Created by luoqingkai on 15-11-9.
 */
public class RpcConnectTask implements Runnable {

    private final RpcSession session;

    public RpcConnectTask(RpcSession session) {
        this.session = session;
    }

    @Override
    public void run() {
        if (session.isConnectSwitchOpen() && !session.isConnected()){
            RpcConnectResponse response = session.connect();
        }
    }
}
