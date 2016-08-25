package com.zte.mos.app.rpcserver.handler;

import com.zte.mos.inf.Maybe;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.util.msg.MoGetConfigMsg;
import com.zte.mos.util.msg.Template;

/**
 * Created by olinchy on 15-12-31.
 */
public class GetConfigHandler extends GetHandler
{
    public GetConfigHandler(Invoker invoker)
    {
        super(invoker);
    }

    @Override
    protected MoMsg createMsg(Integer transId, String strDN)
    {
        return new MoGetConfigMsg(new Template(), new Maybe<Integer>(transId), strDN);
    }
}
