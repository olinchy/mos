package com.zte.mos.app.rpcserver.mountedapps;

import com.zte.mos.inf.Maybe;

import java.net.MalformedURLException;

/**
 * Created by olinchy on 7/16/14 for MO_JAVA.
 */
@Role("CLI")
public class CliAgent extends DefaultAgent
{
    public CliAgent(
            String userName, String pass,
            Maybe<Server> server) throws MalformedURLException
    {
        super(userName, pass, server);
    }

    @Override
    protected String getName()
    {
        return "CLI";
    }

}
