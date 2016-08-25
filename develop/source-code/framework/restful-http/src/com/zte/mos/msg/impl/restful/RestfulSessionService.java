package com.zte.mos.msg.impl.restful;

import com.zte.mos.annotation.PreLoad;
import com.zte.mos.domain.TargetAddress;
import com.zte.mos.exception.BerkeleyDBException;
import com.zte.mos.msg.framework.except.InvalidUrlException;
import com.zte.mos.msg.framework.except.SessionConfigNotCastException;
import com.zte.mos.msg.framework.session.ISession;
import com.zte.mos.msg.framework.session.ISessionConfiguration;
import com.zte.mos.msg.framework.session.ISessionService;
import com.zte.mos.msg.framework.session.SessionFactory;
import com.zte.mos.storage.DataUnit;
import com.zte.mos.storage.MapDbService;
import com.zte.mos.util.basic.Logger;

import static com.zte.mos.storage.MapDbService.DbNameEnum.RESTFUL;

/**
 * Created by dongyue on 16-7-14.
 */
@PreLoad
public class RestfulSessionService implements ISessionService {
    Logger logger = Logger.logger(RestfulSessionService.class);
    public RestfulSessionService() {
        SessionFactory.register(this);
    }

    @Override
    public String getSupportedProtocol() {
        return "RESTFUL";
    }

    @Override
    public ISession recover(TargetAddress address) {
//        try {
//            DataUnit unit = MapDbService.getDB(RESTFUL).get(address.getTargetID());
//            RestfulSessionBuilder builder = new RestfulSessionBuilder();
//            RestfulConfiguration conf = (RestfulConfiguration) builder.build(unit.getData());
//            return new RestfulSession(address, conf);
//        } catch (BerkeleyDBException e) {
//            logger.error(e.getMessage(), e);
//            return null;
//        }
        return new RestfulSession(address, null);
    }

    @Override
    public ISession createSession(TargetAddress address, ISessionConfiguration configuration) throws InvalidUrlException, SessionConfigNotCastException {

        RestfulSession session = new RestfulSession(address, (RestfulConfiguration) configuration);
        //session.saveToDB();
        return session;
    }

    @Override
    public void deleteSession(ISession session) {
        //RestfulSession restful = (RestfulSession) session;
        //restful.delFromDB();
    }
}
