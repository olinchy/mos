package com.zte.mos.massive.monitor;

import com.zte.mos.logging_service.Log;

/**
 * Created by olinchy on 16-7-20.
 */
public interface Sampler
{
    String name();

    String maxY();

    String startY();

    float rate();

    void took(Log log);

    boolean support(Log log);
}
