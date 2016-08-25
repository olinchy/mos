package com.zte.mos.appdaemon;

import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.Daemon;
import com.zte.smartlink.SmartLinkNodeStarter;

import java.util.Properties;

public class StartSmartLinkNode implements Daemon
{
    @Override
    public void start(Properties prop, String... args) throws MOSException
    {
        SmartLinkNodeStarter.start(prop.getProperty("serverIP"), prop, args);
    }

    @Override
    public int compareTo(Daemon o)
    {
        return 1;
    }
}
