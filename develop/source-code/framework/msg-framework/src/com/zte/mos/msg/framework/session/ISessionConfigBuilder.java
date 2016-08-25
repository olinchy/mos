package com.zte.mos.msg.framework.session;

import java.util.HashMap;

/**
 * Created by luoqingkai on 15-6-8.
 *
 */
public interface ISessionConfigBuilder {
    String name();
    ISessionConfiguration build(HashMap<String, String> data);
}
