package com.zte.mos.msg.impl.rpc;

import com.zte.mos.msg.framework.MsgProcess;
import com.zte.mos.msg.framework.session.ISession;

/**
 * Created by zhangbin10086509 on 16-3-24.
 */
public abstract class RpcProcess extends MsgProcess
{
    protected RpcSession session;
    @Override
    public void setSession(ISession session)
    {
        this.session = (RpcSession)session;
    }

    @Override
    public String protocol() {
        return "RPC";
    }
}
