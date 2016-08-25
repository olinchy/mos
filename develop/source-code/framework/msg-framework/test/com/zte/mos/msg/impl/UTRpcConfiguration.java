package com.zte.mos.msg.impl;

import com.zte.mos.msg.framework.session.ISessionConfiguration;

import java.util.HashMap;

/**
 * Created by luoqingkai on 15-5-7.
 */
public class UTRpcConfiguration implements ISessionConfiguration {
    @Override
    public String protocol()
    {
        return "RPC";
    }

    @Override
    public HashMap<String, String> toMap() {
        return null;
    }
}
