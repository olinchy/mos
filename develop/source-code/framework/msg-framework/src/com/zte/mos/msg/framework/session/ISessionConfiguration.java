package com.zte.mos.msg.framework.session;

import java.util.HashMap;


public interface ISessionConfiguration {
    String protocol();
    HashMap<String, String> toMap();
}
