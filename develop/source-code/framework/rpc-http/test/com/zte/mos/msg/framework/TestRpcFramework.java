package com.zte.mos.msg.framework;

import com.odb.database.HashMapSimpleDB;
import com.zte.mos.annotation.Imaged;
import com.zte.mos.domain.*;
import com.zte.mos.exception.BerkeleyDBException;
import com.zte.mos.exception.MOSException;
import com.zte.mos.msg.framework.except.MsgFrameException;
import com.zte.mos.msg.framework.protocol.ProtocolService;
import com.zte.mos.msg.framework.session.ISessionService;
import com.zte.mos.msg.framework.session.SessionConfigFactory;
import com.zte.mos.msg.impl.config.RpcSessionConfigBuilder;
import com.zte.mos.msg.impl.config.SnmpSessionConfigBuilder;
import com.zte.mos.msg.impl.rpc.RpcSession;
import com.zte.mos.msg.impl.rpc.RpcSessionService;
import com.zte.mos.msg.impl.rpc.RpcUserConfiguration;
import com.zte.mos.msg.impl.svr.IServer;
import com.zte.mos.msg.impl.svr.RpcServer;
import com.zte.mos.service.impl.MosHead;
import com.zte.mos.storage.DataUnit;
import com.zte.mos.storage.MapDbService;
import com.zte.mos.util.Singleton;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Constructor;

import static com.zte.mos.storage.MapDbService.DbNameEnum.RPC_SECURITY;
import static com.zte.mos.storage.MapDbService.DbNameEnum.SNMP_SECURITY;
import static org.junit.Assert.*;

public class TestRpcFramework
{
    private static RpcSessionService sessionService;

    @Before
    public void setup() throws Exception
    {
        RpcServer.setServer(new IServer()
        {
        });

        MapDbService.setService(RPC_SECURITY, new HashMapSimpleDB<String, DataUnit>());
        MapDbService.setService(SNMP_SECURITY, new HashMapSimpleDB<String, DataUnit>());
        ProtocolService.setService(new MockProtocolService());
        CommServiceFactory.initService();
        Constructor<?> constructor = RpcSessionService.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        sessionService = (RpcSessionService) constructor.newInstance();
        SessionConfigFactory.register(new RpcSessionConfigBuilder());
        SessionConfigFactory.register(new SnmpSessionConfigBuilder());
    }

    private static TargetAddress buildTarget() throws MOSException
    {
//        ModelHead modelHead = new ModelHead(
//                Singleton.getInstance(MetaStringStore.class).getMeta("nr8960b", "v242", "NR8960B"));
//        IMosHead mosHead = new MosHead(V242NR8960B.class.getAnnotation(Imaged.class));
//        ModelMETA meta = new ModelMETA(modelHead, mosHead);
//        ImagedSystem sys = new UTImagedSystem(meta);

        return new TargetAddressImpl("1", "127.0.0.1", new MockImagedSystem());
    }

    @Test
    public void Given_new_target_When_register_Then_config_saved_to_db()
            throws MsgFrameException, MOSException
    {
        TargetAddress target = buildTarget();
        ISouthService sv = CommServiceFactory.getService();
        sv.register(target);
        DataUnit unit = MapDbService.getDB(RPC_SECURITY).get(target.getIpAddress());
        assertNotNull(unit);
    }

    @Test
    public void Given_registered_target_When_unregister_Then_config_deleted_from_db() throws MsgFrameException, MOSException
    {
        TargetAddress target = buildTarget();
        ISouthService sv = CommServiceFactory.getService();
        sv.register(target);
        DataUnit unit = MapDbService.getDB(RPC_SECURITY).get(target.getIpAddress());
        assertNotNull(unit);

        sv.unRegister(target.getTargetID());
        unit = MapDbService.getDB(RPC_SECURITY).get(target.getIpAddress());
        assertNull(unit);
    }

    @Test
    public void Given_registered_target_When_modified_session_cfg_Then_saved_to_db() throws MsgFrameException, MOSException
    {
        TargetAddress target = buildTarget();
        ISouthService sv = CommServiceFactory.getService();
        sv.register(target);
        DataUnit unit = MapDbService.getDB(RPC_SECURITY).get(target.getIpAddress());
        assertNotNull(unit);
        assertEquals(false, Boolean.valueOf(unit.getData().get("isSecurity")));

        RpcUserConfiguration sessionCfg = new RpcUserConfiguration(true);
        sv.setSessionCfg(sessionCfg, target.getTargetID());
        unit = MapDbService.getDB(RPC_SECURITY).get(target.getIpAddress());
        assertEquals(true, Boolean.valueOf(unit.getData().get("isSecurity")));
    }

    @Test
    public void Given_saved_session_cfg_When_recover_Then_session_rebuilt_from_config()
            throws MOSException, MsgFrameException
    {
        DataUnit unit = new DataUnit("127.0.0.1");
        RpcUserConfiguration sessionCfg = new RpcUserConfiguration(true);
        unit.putAll(sessionCfg.toMap());
        MapDbService.getDB(RPC_SECURITY).put(unit);

        ISessionService sv = RpcSessionService.sv;
        RpcSession session = (RpcSession) sv.recover(buildTarget());
        assertTrue(session.isSecure());
    }


/*
    @Test
    public void Given_reged_session_When_get_by_sessionid_Then_got() throws MOSException
    {
        preload("com.zte.mos.msg.impl");
        TargetAddress address = new UTTargetAddress();
        ISouthService service = CommServiceFactory.getService();
        try
        {
            ISessionConfiguration configSnmp = SessionConfigFactory.getConfiguration("RPC", null);
            Protocol[] protocols = {new ProtocolImp("RPC", configSnmp)};
            service.register(address, protocols);
//            RpcSessionService.getMe().getSession("sdfsf");

        }
        catch (Exception e)
        {
            fail();
        }
    }*/
}
