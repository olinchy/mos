package com.zte.mos.msg.impl.restful;

import com.zte.mos.msg.framework.session.ISessionConfiguration;

import java.util.HashMap;

/**
 * Created by dongyue on 16-7-13.
 */
public class RestfulConfiguration implements ISessionConfiguration {
    private String emsIp;
    private String net;

    public RestfulConfiguration(HashMap<String, String> data) {
        emsIp = data.get("emsIp");
        net = data.get("net");
        guid = data.get("guid");
    }

    @Override
    public String protocol() {
        return "RESTFUL";
    }

    public String getUrl(){
        if (getEmsIp()==null || getNet() == null) return null;
        return "http://"+ getEmsIp() +":8091" + "/net/" + getNet() + "/";
    }

    @Override
    public HashMap<String, String> toMap() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("emsIp", emsIp);
        map.put("net", net);
        map.put("guid", guid);
        return map;
    }

    public String getGuid() {
        return guid;
    }

    public String getNet() {
        return net;
    }

    public String getEmsIp() {
        return emsIp;
    }

    private String guid;
}
