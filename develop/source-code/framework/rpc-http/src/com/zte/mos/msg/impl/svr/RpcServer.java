package com.zte.mos.msg.impl.svr;

/**
 * Created by love on 16-3-22.
 */
public class RpcServer {
    private static IServer starter;
    public static void start(){
        if (starter == null){
            try {
                starter = new Starter();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void setServer(IServer svr){
        starter = svr;
    }
}
