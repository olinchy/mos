package com.zte.mos.msg.framework;

import com.zte.mos.msg.framework.session.ISessionConfiguration;

/**
 * Created by luoqingkai on 15-5-7.
 */
public interface Protocol {
    String name();
    ISessionConfiguration configurations();

}
