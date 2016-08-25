package com.zte.mos.app.rpcserver.handler;

import com.zte.mos.inf.MoCmds;

import java.util.Hashtable;

/**
 * Created by olinchy on 6/18/14 for MO_JAVA.
 */
public class AddHandler extends AbstractCfgHandler
{
    public AddHandler(Invoker invoker)
    {
        super(invoker);
    }

    @Override protected MoCmds getCmd()
    {
        return MoCmds.MoCreateRequest;
    }

}
