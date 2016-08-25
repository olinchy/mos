package com.zte.mos.app.rpcserver.handler;

import com.zte.mos.inf.MoCmds;

/**
 * Created by olinchy on 6/18/14 for MO_JAVA.
 */
public class SetHandler extends AbstractCfgHandler
{
    public SetHandler(Invoker invoker)
    {
        super(invoker);
    }

    @Override protected MoCmds getCmd()
    {
        return MoCmds.MoSetRequest;
    }
}
