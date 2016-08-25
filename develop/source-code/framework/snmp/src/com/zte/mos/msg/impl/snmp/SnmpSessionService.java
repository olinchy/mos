package com.zte.mos.msg.impl.snmp;

import com.zte.mos.annotation.PreLoad;
import com.zte.mos.domain.TargetAddress;
import com.zte.mos.exception.BerkeleyDBException;
import com.zte.mos.msg.framework.except.InvalidUrlException;
import com.zte.mos.msg.framework.except.SessionConfigNotCastException;
import com.zte.mos.msg.framework.session.ISession;
import com.zte.mos.msg.framework.session.ISessionConfiguration;
import com.zte.mos.msg.framework.session.ISessionService;
import com.zte.mos.msg.framework.session.SessionFactory;
import com.zte.mos.msg.impl.config.SnmpSessionConfigBuilder;
import com.zte.mos.storage.DataUnit;
import com.zte.mos.storage.MapDbService;
import com.zte.mos.util.basic.Logger;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by luoqingkai on 15-5-20.
 *
 */
@PreLoad
public class SnmpSessionService implements ISessionService {

    private static final Logger log = logger(SnmpSessionService.class);
    public SnmpSessionService() {
        SessionFactory.register(this);
    }

    @Override
    public String getSupportedProtocol() {
        return "SNMP";
    }

    @Override
    public ISession recover(TargetAddress address) {

        try {
            return new SnmpSession(address, readConfiguratironFromDb(address.getIpAddress()));
        } catch (BerkeleyDBException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    private SnmpConfiguration readConfiguratironFromDb(String ip) throws BerkeleyDBException {
        DataUnit unit = MapDbService.getDB(MapDbService.DbNameEnum.SNMP_SECURITY).get(ip);
        SnmpSessionConfigBuilder builder = new SnmpSessionConfigBuilder();
        SnmpConfiguration userCfg = (SnmpConfiguration) builder.build(unit.getData());
        return userCfg;
    }

    @Override
    public ISession createSession(TargetAddress address, ISessionConfiguration configuration)
            throws InvalidUrlException, SessionConfigNotCastException
    {
        if (configuration instanceof SnmpConfiguration){
            SnmpConfiguration config = (SnmpConfiguration)configuration;
            SnmpSession session = new SnmpSession(address, config);
            session.saveToDB();
            return session;
        }else{
            throw new SessionConfigNotCastException();
        }
    }

    @Override
    public void deleteSession(ISession session) {
        SnmpSession snmpSession = (SnmpSession) session;
        snmpSession.delFromDB();
    }



}
