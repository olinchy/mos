package com.zte.mos.msg.framework.session;

import com.zte.mos.util.basic.Logger;

import java.util.HashMap;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by luoqingkai on 15-6-8.
 *
 */
public class SessionConfigFactory {

    private static Logger log = logger(SessionConfigFactory.class);

    private static HashMap<String, ISessionConfigBuilder> builders = new HashMap<String, ISessionConfigBuilder>();

    public static void register(ISessionConfigBuilder builder){
        builders.put(builder.name(), builder);
        log.info("add builder:"+builder.name());
    }

    public static ISessionConfiguration getConfiguration(String protocol, HashMap<String, String> data){
        ISessionConfigBuilder builder = builders.get(protocol);
        return builder.build(data);
    }
}
