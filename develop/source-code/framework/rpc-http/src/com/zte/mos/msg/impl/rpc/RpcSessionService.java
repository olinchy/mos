package com.zte.mos.msg.impl.rpc;

import com.zte.mos.annotation.PreLoad;
import com.zte.mos.domain.IMosHead;
import com.zte.mos.domain.TargetAddress;
import com.zte.mos.exception.BerkeleyDBException;
import com.zte.mos.msg.framework.except.InvalidUrlException;
import com.zte.mos.msg.framework.except.SessionConfigNotCastException;
import com.zte.mos.msg.framework.session.ISession;
import com.zte.mos.msg.framework.session.ISessionConfiguration;
import com.zte.mos.msg.framework.session.ISessionService;
import com.zte.mos.msg.framework.session.SessionFactory;
import com.zte.mos.msg.impl.config.RpcSessionConfigBuilder;
import com.zte.mos.msg.impl.svr.RpcServer;
import com.zte.mos.storage.DataUnit;
import com.zte.mos.storage.MapDbService;
import com.zte.mos.util.basic.Logger;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.zte.mos.storage.MapDbService.DbNameEnum.RPC_SECURITY;
import static com.zte.mos.util.basic.Logger.logger;


/**
 * Created by luoqingkai on 15-5-13.
 * modify on 2015-09-24
 */
@PreLoad
public class RpcSessionService implements ISessionService {
    private static final Logger log = logger(RpcSessionService.class);
    public static RpcSessionService sv;

    // 临时加上，后边改成拉的方式后，会把这里删除
    private ConcurrentHashMap<String, RpcSession> sessionIdMap =
            new ConcurrentHashMap<String, RpcSession>();

    private final Timer timer = new Timer("Timer-"+RpcSessionService.class.getSimpleName());

    private RpcSessionService() {
        sv = this;
        SessionFactory.register(this);
        timer.scheduleAtFixedRate(new ConnectTimerTask(),
                new Date(System.currentTimeMillis() + 100), 30000);
        executor.allowCoreThreadTimeOut(true);
        ConnectFutureProcessor.getInstance();
        RpcServer.start();
    }

    public RpcSession getSession(String localId) {
        return sessionIdMap.get(localId);
    }

    @Override
    public String getSupportedProtocol() {
        return "RPC";
    }

    @Override
    public ISession recover(TargetAddress address) {
        try {
            RpcSession session = constructSessionOnDiffVersion(address, readFromDB(address.getIpAddress()));
            sessionIdMap.put(session.getLocalID(), session);
            return session;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    private RpcUserConfiguration readFromDB(String ip) throws BerkeleyDBException {
        DataUnit unit = MapDbService.getDB(RPC_SECURITY).get(ip);
        RpcSessionConfigBuilder builder = new RpcSessionConfigBuilder();
        RpcUserConfiguration userCfg = builder.buildFromString(unit.getData());
        return userCfg;
    }

    @Override
    public ISession createSession(TargetAddress address, ISessionConfiguration configuration)
            throws InvalidUrlException, SessionConfigNotCastException {
        try {
            if (configuration instanceof RpcUserConfiguration){
                RpcUserConfiguration userConfig = (RpcUserConfiguration)configuration;
                RpcSession session = constructSessionOnDiffVersion(address, userConfig);
                sessionIdMap.put(session.getLocalID(), session);
                session.saveToDB();
                return session;
            }
            else{
                throw new SessionConfigNotCastException();
            }
        } catch (MalformedURLException e) {
            throw new InvalidUrlException();
        }
    }

    private RpcSession constructSessionOnDiffVersion(TargetAddress address, RpcUserConfiguration userConfig) throws MalformedURLException {
        IMosHead mosHead = address.getMosHead();
        if (mosHead.version() == 1.1f) {
            return new OldRpcSession(address, userConfig);
        }else{
            return new RpcSession(address, userConfig);
        }
    }


    @Override
    public void deleteSession(ISession session) {
        RpcSession rpcSession = (RpcSession) session;
        sessionIdMap.remove(rpcSession.getLocalID());
        //session.setConnectSwitch(false);
        log.info("delete session!");
        rpcSession.delFromDB();
    }

    private class ConnectTimerTask extends TimerTask{

        @Override
        public void run() {
            for (RpcSession session : sessionIdMap.values()) {
                if (session.isConnectSwitchOpen() &&
                        !session.isConnected()){
                    RpcConnectTask task = new RpcConnectTask(session);
                    executor.execute(task);
                }else if (session.isConnected()
                        && (session instanceof OldRpcSession)){
                    OldRpcPingTask pingTask = new OldRpcPingTask(session);
                    executor.execute(pingTask);
                }
            }
        }
    }


    private static ThreadPoolExecutor executor =
            new ThreadPoolExecutor(2, 2, 2, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

}
