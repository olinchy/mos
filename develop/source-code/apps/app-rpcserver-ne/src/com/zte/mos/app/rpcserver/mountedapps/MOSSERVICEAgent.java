package com.zte.mos.app.rpcserver.mountedapps;

import com.zte.mos.inf.Maybe;

import java.net.MalformedURLException;

/**
 * Created by olinchy on 16-6-29.
 */
@Role("MOSSERVICE")
public class MOSSERVICEAgent extends CliAgent
{
    public MOSSERVICEAgent(
            String userName, String pass, Maybe<Server> server) throws MalformedURLException
    {
        super(userName, pass, server);
    }

    @Override
    protected String getName()
    {
        return "MOSSERVICE";
    }
}
