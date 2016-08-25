package com.zte.mos.msg.impl.restful;

import com.zte.mos.msg.framework.session.ISessionConfigBuilder;
import com.zte.mos.msg.framework.session.ISessionConfiguration;
import com.zte.mos.msg.framework.session.SessionConfigFactory;

import java.util.HashMap;

/**
 * Created by dongyue on 16-7-14.
 */
public class RestfulSessionBuilder implements ISessionConfigBuilder{
    static {
        SessionConfigFactory.register(new RestfulSessionBuilder());
    }
    @Override
    public String name() {
        return "RESTFUL";
    }

    @Override
    public ISessionConfiguration build(HashMap<String, String> data) {
        return new RestfulConfiguration(data);
    }
}
