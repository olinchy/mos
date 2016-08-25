package com.zte.mos.msg.impl.restful.southservice;

import com.odb.database.HashMapSimpleDB;
import com.zte.mos.domain.TargetAddressImpl;
import com.zte.mos.msg.framework.MsgProcessPool;
import com.zte.mos.msg.framework.SouthService;
import com.zte.mos.msg.framework.except.MsgFrameException;
import com.zte.mos.msg.framework.operation.Action;
import com.zte.mos.msg.framework.operation.ActionResponse;
import com.zte.mos.msg.framework.protocol.ProtocolService;
import com.zte.mos.msg.framework.session.SessionConfigFactory;
import com.zte.mos.msg.impl.InventoryRestfulProcessor;
import com.zte.mos.msg.impl.restful.RestfulConfiguration;
import com.zte.mos.msg.impl.restful.RestfulSessionBuilder;
import com.zte.mos.msg.impl.restful.RestfulSessionService;
import com.zte.mos.msg.impl.restful.southservice.mocks.MockImagedSystem;
import com.zte.mos.msg.impl.restful.southservice.mocks.Inventory;
import com.zte.mos.msg.impl.restful.southservice.mocks.MockProtocolService;
import com.zte.mos.msg.impl.restful.southservice.mocks.MockUR2000;
import com.zte.mos.storage.DataUnit;
import com.zte.mos.storage.MapDbService;
import com.zte.mos.type.Pair;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static com.zte.mos.domain.ConnectionState.ONLINE;
import static com.zte.mos.msg.framework.operation.OperEnum.Action;
import static com.zte.mos.storage.MapDbService.DbNameEnum.RESTFUL;
import static org.junit.Assert.*;

/**
 * Created by dongyue on 16-7-14.
 */
public class TestSouthserviceForUR2000_ChangeConfiguration {
    private final String ipAddress = "127.0.0.1";
    private String targetID = "/Ems/1/Ne/1/Product/1";
    SouthService southService = new SouthService();

    @BeforeClass
    public static void start() throws IOException, InterruptedException {
        MsgProcessPool.register(InventoryRestfulProcessor.class);
        ProtocolService.setService(new MockProtocolService());
        MockUR2000.start();
    }

    @AfterClass
    public static void end(){
        MockUR2000.stop();
    }
    @Before
    public void setUp() throws Exception {
        new RestfulSessionService();
        MapDbService.setService(RESTFUL, new HashMapSimpleDB<String, DataUnit>());
        SessionConfigFactory.register(new RestfulSessionBuilder());
        initSouthService();
    }

    @Test
    public void Given_registered_southservice_when_change_net_belongs_Then_aciton_to_get_inventory_succ() throws Exception {

        ActionResponse errorResponse = changeConfiguration("net", "net-new");
        assertFalse(errorResponse.isSuccess());
        assertNull(errorResponse.getMo());

        ActionResponse rightResponse = changeConfiguration("net", "net-name");
        assertTrue(rightResponse.isSuccess());
        assertEquals("e000001", rightResponse.getMo().get("guid"));
    }

    @Test
    public void Given_registered_southservice_when_change_ems_belongs_Then_aciton_to_get_inventory_succ() throws Exception {

        ActionResponse errorResponse = changeConfiguration("emsIp", "111.11.1.1");
        assertFalse(errorResponse.isSuccess());
        assertNull(errorResponse.getMo());

        ActionResponse rightResponse = changeConfiguration("emsIp", "127.0.0.1");
        assertTrue(rightResponse.isSuccess());
        assertEquals("e000001", rightResponse.getMo().get("guid"));
    }

    private ActionResponse changeConfiguration(String key, String value) throws MsgFrameException {
        RestfulConfiguration newConfig = buildConfiguration(key, value);
        southService.setSessionCfg(newConfig, targetID);
        return southService.action(buildOperationParam("inventory"), "/Ems/1/Ne/1/Product/1");
    }

    private RestfulConfiguration buildConfiguration(String key, String value) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("emsIp", "127.0.0.1");
        map.put("net", "net-name");
        map.put("guid", "e000001");
        map.put(key, value);
        return new RestfulConfiguration(map);
    }

    private Action buildOperationParam(String url) {
        ArrayList<Pair<String, Object>> pairs = new ArrayList<Pair<String, Object>>();
        pairs.add(new Pair<String, Object>("resource", url));
        return new Action(new Inventory(), Action.name(), pairs);
    }
    private void initSouthService() throws MsgFrameException {
        southService.register(new TargetAddressImpl(targetID, ipAddress, new MockImagedSystem()));
        southService.setSessionCfg(buildRestfulCfg(), targetID);
        southService.setConnectSwitch(targetID, ONLINE);
    }

    private RestfulConfiguration buildRestfulCfg() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("emsIp", "127.0.0.1");
        map.put("net", "net-name");
        map.put("guid", "e000001");
        return new RestfulConfiguration(map);
    }
}
