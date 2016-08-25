package com.zte.container.ext.diff;

import com.fasterxml.jackson.databind.JsonNode;
import com.zte.container.ext.total.ReverseTaskFactory;
import com.zte.mos.msg.framework.CommServiceFactory;
import com.zte.mos.msg.framework.ISouthService;
import com.zte.mos.msg.framework.except.UnregisteredException;
import com.zte.mos.msg.framework.operation.PingResponse;
import com.zte.mos.task.TaskScheduler;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;

import static com.zte.mos.domain.ConnectionState.OFFLINE;
import static com.zte.mos.domain.ConnectionState.ONLINE;
import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by luoqingkai on 15-11-12.
 *
 */
public class DiffPollingFutureProcessor {

    private static ConcurrentLinkedQueue<PingResponse> queue
            = new ConcurrentLinkedQueue<PingResponse>();


    static void add(PingResponse response){
        queue.add(response);
    }

    private static DiffPollingFutureProcessor instance;

    public static DiffPollingFutureProcessor getInstance(){
        if (instance == null){
            instance = new DiffPollingFutureProcessor();
        }
        return instance;
    }

    private DiffPollingFutureProcessor() {
        new PollThread().start();
    }

    private void check() throws ExecutionException, InterruptedException {
        int len = queue.size();
        int i = 0;
        while (i < len && !queue.isEmpty()){
            PingResponse response = queue.poll();
            i++;
            if (response.getFuture().isDone()){
                JsonNode node = response.getFuture().get();
                int result = node.get("result").asInt();

                if(result == 217100){
                    ISouthService sv = CommServiceFactory.getService();
                    String targetId = response.getAddress().getTargetID();
                    try {
                        sv.setConnectSwitch(targetId, OFFLINE);
                        sv.setConnectSwitch(targetId, ONLINE);
                    } catch (UnregisteredException e) {
                        e.printStackTrace();
                    }
                }

                if (result != 0 )
                {
                    Runnable task = ReverseTaskFactory.createSystemTask(
                            response.getAddress().getTargetID(),
                            response.getAddress().getSystem()
                    );
                    TaskScheduler.addTask(task);
                }
                else
                {
                    DefaultSyncDiffTask incTask = new DefaultSyncDiffTask(response.getAddress(), node);
                    TaskScheduler.addTask(incTask);
                }
            }
            else
            {
                if (response.getLiveTime() < 5000)
                {
                    queue.add(response);
                }
                else
                {
                    logger(DiffPollingFutureProcessor.class).info("diff polling time out, dn = " + response.getAddress().getTargetID() + "ip = " + response.getAddress().getIpAddress());
                }
            }
        }
    }

    private class PollThread extends Thread{

        public void run(){

            while(true){

                try
                {
                    check();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }


        }
    }

}
