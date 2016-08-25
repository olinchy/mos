package com.zte.mos.msg.impl.rpc;

import com.zte.mos.msg.framework.session.ISessionConfiguration;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by luoqingkai on 15-5-15.
 *
 */
public class RpcUserConfiguration implements ISessionConfiguration {
    private final String userName;
    private final String password;
    private final int connectionTimeoutMillis;
    private boolean isSecurity = false;

    public RpcUserConfiguration(String userName, String password, int connectionTimeoutMillis) {
        this.userName = userName;
        this.password = password;
        this.connectionTimeoutMillis = connectionTimeoutMillis;
    }

    public RpcUserConfiguration(boolean isSecurity){
        this.isSecurity = isSecurity;
        this.userName = "";
        this.password = "";
        connectionTimeoutMillis = 50;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public int getConnectionTimeoutMillis() {
        return connectionTimeoutMillis;
    }

    public URL getTargetURL(String ipAddress, float mosVersion) throws MalformedURLException {

        StringBuilder builder = new StringBuilder();
        if (isSecurity){
            builder.append("https://");
        }else {
            builder.append("http://");
        }
        if (!isSecurity && mosVersion == 1.1f){

            builder.append(ipAddress).append("/goform/emsMsg");
        }else{
            builder.append(ipAddress).append(":444"/*"/goForm/emsMsg"*/);
        }

        return new URL(builder.toString());
    }

    @Override
    public String protocol()
    {
        return "RPC";
    }

    @Override
    public HashMap<String, String> toMap() {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("userName", userName);
        map.put("password", password);
        map.put("timeout", String.valueOf(this.connectionTimeoutMillis));
        map.put("isSecurity", String.valueOf(isSecurity));
        return map;
    }

    public boolean isSecure()
    {
        return isSecurity;
    }
}
