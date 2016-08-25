package com.zte.mos.app.rpcserver.mountedapps;

import com.zte.mos.inf.Maybe;
import com.zte.mos.util.basic.Logger;

import java.net.MalformedURLException;

/**
 * Created by olinchy on 7/15/14 for MO_JAVA.
 */
public abstract class AbstractApp implements MountedApp
{
    private static Logger logger = Logger.logger(AbstractApp.class);
    protected String userName, pass;
    protected Maybe<Server> server;

    public AbstractApp(String userName, String pass, Maybe<Server> server)
            throws MalformedURLException
    {
        this.userName = userName;
        this.pass = pass;
        this.server = server;
    }

    @Override
    public void start()
    {
        logger.info("start Mounted APP: " + getName());
    }

    protected abstract String getName();
}
