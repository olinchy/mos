package com.zte.mos.test;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by zw on 15-12-2.
 */
public class Cache implements Serializable {
    public Cache(String dn, String ip, HashMap<String, Object> map){
        this.dn = dn;
        this.ip = ip;
        this.map = map;
    }

    public Cache(){}

    private String dn = "";

    private String ip = "";

    private HashMap<String, Object> map = new HashMap<String, Object>();

    public Cache(com.zte.mos.app.necache.Cache cache) {
        this.dn = cache.getDn();
        this.ip = cache.getIp();
        this.map = cache.getMap();
    }

    public String getIp() {return ip;}

    public String getDn() {
        return dn;
    }

    public HashMap<String, Object> getMap() {
        return map;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(! (obj instanceof com.zte.mos.test.Cache)) return false;
        com.zte.mos.test.Cache cahce = (com.zte.mos.test.Cache)obj;
        if(!this.dn.equals(cahce.getDn())) return false;
        if(!this.ip.equals(cahce.getIp())) return false;
        return this.map.equals(cahce.getMap());
    }
}
