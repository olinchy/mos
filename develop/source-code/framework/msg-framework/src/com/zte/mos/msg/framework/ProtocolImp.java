package com.zte.mos.msg.framework;

import com.zte.mos.msg.framework.session.ISessionConfiguration;

/**
 * Created by luoqingkai on 15-6-9.
 * modify on 1110, del isMosSupport
 */
public class ProtocolImp implements Protocol {
    private final String name;
    private final ISessionConfiguration config;
    //private boolean isMosSupport = true;

    public ProtocolImp(String name, ISessionConfiguration config) {
        this.name = name;
        this.config = config;
        //this.isMosSupport = isMosSupport;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public ISessionConfiguration configurations() {
        return config;
    }

}
