package com.zte.mos.msg.impl.rpc;

import com.zte.mos.util.basic.Logger;
import com.zte.mos.util.tools.Prop;

/**
 * Created by luoqingkai on 15-5-15.
 */
public class RpcLocalSvrConfigs {
    private static int rpcPort = 55555;
    private static String localURL;


    static {
        initPort();
        String localIP = initLocalIP();
        localURL = "http://" + localIP + ":" + rpcPort;
    }

    private static void initPort(){
        String port = Prop.get("RpcPort");
        if (port != null){
            rpcPort = Integer.parseInt(port.toString());
        }else{
            Logger.logger(RpcLocalSvrConfigs.class).error(
                    "Read RPC port failed, use default " + rpcPort + ":", new Exception("error!"));
        }
    }

    private static String initLocalIP(){
        String localIP = System.getProperty("com.zte.ums.console.mainconroller.ipaddress");
        if (localIP == null || localIP.isEmpty()){
            localIP = Prop.get("bundleip");
        }
        if (localIP == null || localIP.isEmpty()){
            localIP = "localhost";
            Logger.logger(RpcLocalSvrConfigs.class).error(
                    "Read RPC server IP failed, use default localhost:", new Exception("error!"));
        }
        return localIP;
    }

    public static int getRpcPort(){
        return rpcPort;
    }

    public static String getLocalURL(){
        return localURL;
    }
}
