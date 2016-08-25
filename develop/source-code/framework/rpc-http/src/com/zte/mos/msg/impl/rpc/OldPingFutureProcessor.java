package com.zte.mos.msg.impl.rpc;

import com.fasterxml.jackson.databind.JsonNode;
import com.zte.mos.msg.framework.operation.PingResponse;
import com.zte.mos.util.basic.Logger;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Future;

import static com.zte.mos.util.basic.Logger.logger;


public class OldPingFutureProcessor {

    private static ConcurrentLinkedQueue<PingResponse> queue
            = new ConcurrentLinkedQueue<PingResponse>();

    static void add(PingResponse response){
        queue.add(response);
    }
    private static Logger log = logger(OldPingFutureProcessor.class);
    public OldPingFutureProcessor() {
        new PollThread().start();
    }

    private void reLogin(RpcPingResponse response){
        log.info("Relogin!" + response);
        response.session.setConnectSwitch(false);
        response.session.setConnectSwitch(true);
    }

    private void check(){
        int len = queue.size();
        int i = 0;
        while (i < len && !queue.isEmpty()){
            RpcPingResponse response = (RpcPingResponse)queue.poll();
            i++;
            Future<JsonNode> future = response.getFuture();
            JsonNode node;
            if (future.isDone()){
                try {
                    node = future.get();
                    int result = node.get("result").asInt();
                    if (result != 0){
                        reLogin(response);
                    }
                } catch (Exception e) {
                    reLogin(response);
                }


            }else{
                if (response.getLiveTime() < 5000){
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
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
