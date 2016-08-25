package com.zte.mos.msg.impl.restful.southservice.mocks;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by dongyue on 16-7-15.
 */
public class MockUR2000 implements Runnable {
    private static ThreadPoolExecutor executor =
            new ThreadPoolExecutor(2, 2, 2, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
    private static Process process;
    @Override
    public void run() {
        String path = "/home/dongyue/workspace/ems-mos/develop/source-code/framework/restful-http/test/com/zte/mos/msg/impl/restful/mock-ur2000/get/restful_get.py";
        try {
            process =  Runtime.getRuntime().exec("python " + path);
            process.waitFor();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void start(){
        executor.execute(new MockUR2000());
    }

    public static void stop(){
        process.destroy();////// TODO: 16-7-15  can not stop the process successfully
    }
}
