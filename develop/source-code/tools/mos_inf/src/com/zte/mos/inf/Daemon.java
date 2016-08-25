package com.zte.mos.inf;

import com.zte.mos.exception.MOSException;

import java.util.Properties;

/**
 * Created by olinchy on 2/12/15 for mosjava.
 */
public interface Daemon extends Comparable<Daemon>
{
    void start(Properties properties, String... args) throws MOSException;
}
