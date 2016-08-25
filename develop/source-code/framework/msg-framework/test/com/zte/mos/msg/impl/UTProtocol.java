package com.zte.mos.msg.impl;

import com.zte.mos.msg.framework.Protocol;
import com.zte.mos.msg.framework.session.ISessionConfiguration;

/**
 * Created by luoqingkai on 15-5-7.
 */
public class UTProtocol implements Protocol {
    private String name;
    private ISessionConfiguration config;

    public UTProtocol(String name, ISessionConfiguration config) {
        this.name = name;
        this.config = config;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public ISessionConfiguration configurations() {
        return null;
    }

    public int hashCode(){
        return name.hashCode();
    }
}
