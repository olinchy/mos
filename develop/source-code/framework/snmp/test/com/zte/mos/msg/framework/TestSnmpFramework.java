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
import com.zte.mos.msg.impl.config.SnmpSessionConfigBuilder;
import com.zte.mos.msg.impl.snmp.SnmpConfiguration;
import com.zte.mos.msg.impl.snmp.SnmpSession;
import com.zte.mos.msg.impl.snmp.SnmpSessionService;
import com.zte.mos.msg.impl.snmp.SnmpV2Configuration;
import com.zte.mos.service.impl.MosHead;
import com.zte.mos.storage.DataUnit;
import com.zte.mos.storage.MapDbService;
import com.zte.mos.util.Singleton;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import static com.zte.mos.storage.MapDbService.DbNameEnum.SNMP_SECURITY;
import static org.junit.Assert.*;

/**
 * Created by luoqingkai on 15-9-30.
 */
public class TestSnmpFramework
{
    private static SnmpSessionService sessionService;

    @Before
    public void setup() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException
    {
        ProtocolService.setService(new MockProtocolService());
        MapDbService.setService(SNMP_SECURITY, new HashMapSimpleDB<String, DataUnit>());
        CommServiceFactory.initService();
        Constructor<?> constructor = SnmpSessionService.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        sessionService = (SnmpSessionService) constructor.newInstance();
        SessionConfigFactory.register(new SnmpSessionConfigBuilder());
    }

    private static TargetAddress buildTarget() throws MOSException
    {
//        ModelHead modelHead = new ModelHead(
//                Singleton.getInstance(MetaStringStore.class).getMeta("nr8250", "v241", "Root"));
//        IMosHead mosHead = new MosHead(V241NR8250.class.getAnnotation(Imaged.class));
//        ModelMETA meta = new ModelMETA(modelHead, mosHead);
//        ImagedSystem sys = new UTImagedSystem(meta);
        return new TargetAddressImpl("1", "127.0.0.1", new MockImagedSystem());
    }

    @Test
    public void Given_new_target_When_register_Then_config_saved_to_db() throws Exception
    {
        TargetAddress target = buildTarget();
        ISouthService sv = CommServiceFactory.getService();
        sv.register(target);
        DataUnit unit = MapDbService.getDB(SNMP_SECURITY).get(target.getIpAddress());
        assertNotNull(unit);
    }

    @Test
    public void Given_registered_target_When_unregister_Then_config_deleted_from_db() throws Exception
    {
        TargetAddress target = buildTarget();
        ISouthService sv = CommServiceFactory.getService();
        sv.register(target);
        DataUnit unit = MapDbService.getDB(SNMP_SECURITY).get(target.getIpAddress());
        assertNotNull(unit);

        sv.unRegister(target.getTargetID());
        unit = MapDbService.getDB(SNMP_SECURITY).get(target.getIpAddress());
        assertNull(unit);
    }

    @Test
    public void Given_registered_target_When_modified_session_cfg_Then_session_cfg_in_db_changed() throws MsgFrameException, MOSException
    {
        TargetAddress target = buildTarget();
        ISouthService sv = CommServiceFactory.getService();
        sv.register(target);
        DataUnit unit = MapDbService.getDB(SNMP_SECURITY).get(target.getIpAddress());
        assertNotNull(unit);
        assertEquals("ztesnmp2014", unit.getData().get("authPassword"));

        SnmpConfiguration sessionCfg = new SnmpV2Configuration(getSnmpCfg());
        sv.setSessionCfg(sessionCfg, target.getTargetID());

        unit = MapDbService.getDB(SNMP_SECURITY).get(target.getIpAddress());
        assertEquals(null, unit.getData().get("authPassword"));
    }

    @Test
    public void Given_session_cfg_in_db_When_recover_Then_new_target_() throws MOSException, MsgFrameException
    {
        DataUnit unit = new DataUnit("127.0.0.1");
        SnmpConfiguration sessionCfg = new SnmpV2Configuration(getSnmpCfg());
        unit.putAll(sessionCfg.toMap());
        MapDbService.getDB(SNMP_SECURITY).put(unit);

        ISessionService sv = new SnmpSessionService();
        SnmpSession session = (SnmpSession) sv.recover(buildTarget());
        assertFalse(session.isSecure());
    }

    private HashMap<String, String> getSnmpCfg()
    {
        HashMap<String, String> snmpCfg = new HashMap<String, String>();
        snmpCfg.put("version", "v2c");
        snmpCfg.put("port", "161");
        snmpCfg.put("readcommunity", "private");
        snmpCfg.put("writecommunity", "private");
        snmpCfg.put("retry_timeout", "5");
        snmpCfg.put("retry_times", "3");
        return snmpCfg;
    }

    /*
    private static void preload(String path) throws MOSException
    {
        PreLoadScanner scanner = new PreLoadScanner();
        scanner.scan(Scan.getClasses(path, Object.class));
    }

    @Test
    public void Given_registered_process_When_executed_Then_response_got()
            throws MOSException,MsgFrameException
    {
        UTTargetAddress address = initAddress(MockMo_V233.class);
        ISouthService service = initSouth(address);

        ManagementObject mo = new MockMo_V233();
        Create create = new Create(50, new Maybe<Integer>(3), mo);
        CreateResponse response = service.create(create, address.getTargetID());
        assertNotNull(response);
    }

    @Test(expected = UnsupportedProtocolException.class)
    public void Given_non_process_and_rpc_multi_When_not_local_only_and_executed_Then_except_thrown()
            throws MOSException, MsgFrameException
    {
        UTTargetAddress address = initAddress(MockMo_V241.class);
        ISouthService service = initSouth(address);

        ManagementObject mo = new MockMo_V241();
        mo.setDn("/Ems/1/Ne/1/Product/1");
        Delete delete = new Delete(50, new Maybe<Integer>(3), mo);

        service.delete(delete, address.getTargetID());
    }

    @Test
    public void Given_non_process_and_rpc_multi_When_local_only_and_executed_Then_response_got()
            throws MOSException, MsgFrameException, InstantiationException, IllegalAccessException {
        UTTargetAddress address = initAddress(MockMo_V241.class);
        ISouthService service = initSouth(address);

        LocalOnlyPool.register(UTLocalOnly.class);

        ManagementObject mo = new MockMo_V241();
        mo.setDn("/Ems/1/Ne/1/Product/1");
        Delete delete = new Delete(50, new Maybe<Integer>(3), mo);
        DeleteResponse response = service.delete(delete, address.getTargetID());
        assertNotNull(response);
    }

    @Test
    public void Given_non_process_and_snmp_single_When_executed_Then_response_got()
            throws MOSException, MsgFrameException
    {
        UTTargetAddress address = initAddress(MockMo_V233.class);
        ISouthService service = initSouth(address);

        ManagementObject mo = new MockMo_V233();
        mo.setDn("/Ems/1/Ne/1/Product/1");
        Delete delete = new Delete(50, new Maybe<Integer>(3), mo);
        DeleteResponse response = service.delete(delete, address.getTargetID());
        assertNotNull(response);
    }

    @Test(expected = UnsupportedProtocolException.class)
    public void Given_non_process_and_rpc_single_When_executed_Then_except_thrown()
            throws MOSException, MsgFrameException
    {
        UTTargetAddress address = initAddress(MockMo_V242x.class);
        ISouthService service = initSouth(address);

        ManagementObject mo = new MockMo_V242x();
        mo.setDn("/Ems/1/Ne/1/Product/1");
        Delete delete = new Delete(50, new Maybe<Integer>(3), mo);
        service.delete(delete, address.getTargetID());
    }

    private UTTargetAddress initAddress(Class<? extends ManagementObject> moClazz){
        UTTargetAddress address = new UTTargetAddress();
        address.setTargetID("/Ems/1/Ne/1/Product/1");
        MoMeta meta = new MoMeta();
        meta.setType(moClazz);
        address.setRootMeta(meta);
        return address;
    }

    private ISouthService initSouth(UTTargetAddress address)
            throws MOSException, MsgFrameException {
        preload("com.zte.mos.msg.impl");
        CommServiceFactory.initService();

        ISouthService service = CommServiceFactory.getService();

        ISessionConfiguration configSnmp = SessionConfigFactory.getConfiguration("SNMP", getSnmpConfigData());
        Protocol[] protocols = {new ProtocolImp("SNMP", configSnmp)};
        service.register(address, protocols);
        return service;
    }

    private HashMap<String, Object> getSnmpConfigData()
    {
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("version", "v2c");
        data.put("port", 161);
        data.put("readcommunity", "public");
        data.put("writecommunity", "private");
        data.put("retry_timeout", 5000);
        data.put("retry_times", 3);
        return data;
    }


*/
}
