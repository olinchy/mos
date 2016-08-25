package com.zte.mos.domain;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

import java.io.Serializable;

/**
 * Created by luoqingkai on 15-7-28.
 */
@Entity
public class NeIdentity implements Serializable{
    @PrimaryKey
    public String dn;
    public String ip;
    public String netype;

    public NeIdentity() {
    }

    public NeIdentity(String dn, String ip, String netype) {
        this.dn = dn;
        this.ip = ip;
        this.netype = netype;
    }

    public String toString(){
        return "netype: " + netype + ", dn: " + dn + ", ip: " + ip;
    }

    public NeIdentity clone() {
        return new NeIdentity(dn, ip, netype);
    }

    public boolean equals(NeIdentity obj){
        if (obj == null){
            return false;
        }
        return netype.equals(obj.netype)
                && ip.equals(obj.ip)
                && dn.equals(obj.dn);
    }
}
