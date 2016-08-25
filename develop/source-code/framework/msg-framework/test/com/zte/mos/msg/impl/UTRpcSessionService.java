package com.zte.mos.msg.impl;

import com.zte.mos.annotation.PreLoad;
import com.zte.mos.domain.TargetAddress;
import com.zte.mos.msg.framework.session.ISession;
import com.zte.mos.msg.framework.session.ISessionConfiguration;
import com.zte.mos.msg.framework.session.ISessionService;
import com.zte.mos.msg.framework.session.SessionFactory;

/**
 * Created by luoqingkai on 15-5-7.
 *
 */
@PreLoad
public class UTRpcSessionService implements ISessionService {

    public UTRpcSessionService() {
        SessionFactory.register(this);
    }

    @Override
    public String getSupportedProtocol() {
        return "RPC";
    }

    @Override
    public ISession recover(TargetAddress address) {
        return null;
    }


    @Override
    public ISession createSession(TargetAddress address, ISessionConfiguration configuration) {
        return new UTRpcSession(address);
    }

    @Override
    public void deleteSession(ISession session) {

    }
}
