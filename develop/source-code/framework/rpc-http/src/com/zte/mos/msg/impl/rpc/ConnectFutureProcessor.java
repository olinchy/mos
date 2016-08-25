package com.zte.mos.msg.impl.rpc;

import com.zte.mos.domain.TargetAddress;
import com.zte.mos.exception.MOSException;
import com.zte.mos.message.Result;
import com.zte.mos.util.basic.Logger;
import com.zte.mos.util.msg.MoEvent;
import com.zte.smartlink.deliver.DeliverService;

import java.util.concurrent.ConcurrentLinkedQueue;

import static com.zte.mos.util.basic.Logger.logger;


/**
 * Created by luoqingkai on 15-11-3.
 *
 */
public class ConnectFutureProcessor{
    private static Logger log = logger(ConnectFutureProcessor.class);

    private static ConcurrentLinkedQueue<RpcConnectResponse> queue
            = new ConcurrentLinkedQueue<RpcConnectResponse>();
    private final int RPC_TIMEOUT = 30 * 1000;

    static void add(RpcConnectResponse response){
        queue.add(response);
    }

    private static ConnectFutureProcessor instance;

    static ConnectFutureProcessor getInstance(){
        if (instance == null){
            instance = new ConnectFutureProcessor();
        }
        return instance;
    }

    private ConnectFutureProcessor() {
        new PollThread().start();
    }

    private void check(){
        int len = queue.size();
        int i = 0;
        while (i < len && !queue.isEmpty()){
            RpcConnectResponse response = queue.poll();
            i++;
            if (response.getFuture().isDone()){
                response.getSession().setSessionId(response.getFuture());
            }else{
                if (response.getLiveTime() < RPC_TIMEOUT){
                    queue.add(response);
                }
            }
        }
    }

    private class PollThread extends Thread{

        public void run(){
            while(true){
                check();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

}
