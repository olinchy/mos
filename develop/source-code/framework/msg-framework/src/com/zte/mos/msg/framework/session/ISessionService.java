package com.zte.mos.msg.framework.session;

import com.zte.mos.domain.TargetAddress;
import com.zte.mos.msg.framework.except.InvalidUrlException;
import com.zte.mos.msg.framework.except.SessionConfigNotCastException;

/**
 * Created by luoqingkai on 15-5-7.
 *
 */
public interface ISessionService {

    //ISession getSession(String targetId);

    String getSupportedProtocol();

    ISession recover(TargetAddress address);

    ISession createSession(TargetAddress address, ISessionConfiguration configuration)
            throws InvalidUrlException, SessionConfigNotCastException;

    void deleteSession(ISession session);

}
