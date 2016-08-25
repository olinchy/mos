package com.zte.mos.msg.framework.svr;


public class RpcSvrRegister {

    private static RpcListener listener;

    private static ReverseListener reverseListener;

    public static void register(RpcListener lsnr) {
        listener = lsnr;
    }

    public static void register(ReverseListener lsnr){
        reverseListener = lsnr;
    }

    public static RpcListener getListener() {
        return listener;
    }

    public static ReverseListener getReverseListener(){
        return reverseListener;
    }
}
