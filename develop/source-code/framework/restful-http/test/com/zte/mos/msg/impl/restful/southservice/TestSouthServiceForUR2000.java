package com.zte.mos.msg.impl.restful.southservice;

import com.odb.database.HashMapSimpleDB;
import com.zte.mos.domain.TargetAddressImpl;
import com.zte.mos.msg.framework.MsgProcessPool;
import com.zte.mos.msg.framework.SouthService;
import com.zte.mos.msg.framework.except.MsgFrameException;
import com.zte.mos.msg.framework.except.UnregisteredException;
import com.zte.mos.msg.framework.operation.Action;
import com.zte.mos.msg.framework.operation.ActionResponse;
import com.zte.mos.msg.framework.protocol.ProtocolService;
import com.zte.mos.msg.framework.session.SessionConfigFactory;
import com.zte.mos.msg.impl.InventoryRestfulProcessor;
import com.zte.mos.msg.impl.AlarmRestfulProcessor;
import com.zte.mos.msg.impl.restful.RestfulConfiguration;
import com.zte.mos.msg.impl.restful.RestfulSessionBuilder;
import com.zte.mos.msg.impl.restful.RestfulSessionService;
import com.zte.mos.msg.impl.restful.southservice.mocks.*;
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
import java.util.LinkedHashMap;
import java.util.List;

import static com.zte.mos.domain.ConnectionState.ONLINE;
import static com.zte.mos.msg.framework.operation.OperEnum.Action;
import static com.zte.mos.storage.MapDbService.DbNameEnum.RESTFUL;
import static org.junit.Assert.*;

/**
 * Created by dongyue on 16-7-14.
 */
public class TestSouthServiceForUR2000 {
    private final String ipAddress = "127.0.0.1";
    private String targetID = "/Ems/1/Ne/1/Product/1";
    SouthService southService = new SouthService();

    @BeforeClass
    public static void start() throws IOException, InterruptedException {
        MockUR2000.start();
        //ProtocolService.setService(new MockProtocolService());
        MsgProcessPool.register(InventoryRestfulProcessor.class);
        MsgProcessPool.register(AlarmRestfulProcessor.class);
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
    public void Given_registed_session_When_action_to_get_inventory_Then_correct_inventory_got() throws Exception {
        ActionResponse response = southService.action(buildInventoryParam(), targetID);
        assertEquals("e000001", response.getMo().get("guid"));
    }

    @Test
    public void Given_registed_session_When_action_to_get_history_alarm_Then_correct_alarm_data_got() throws Exception {
        ActionResponse response = southService.action(buildAlaramParam(), targetID);
        List<LinkedHashMap<String, String>> result = (List<LinkedHashMap<String, String>>) response.getMo().get("result");
        assertEquals("e000001", result.get(0).get("source"));
    }

    @Test(expected = UnregisteredException.class)
    public void Given_unregisted_session_When_action_to_get_inventory_Then_unregisted_exception_throws() throws Exception {
        southService.unRegister("/Ems/1/Ne/1/Product/1");
        ActionResponse response = southService.action(buildInventoryParam(), targetID);
        assertEquals("e000001", response.getMo().get("guid"));
    }

    @Test
    public void Given_registered_session_When_action_to_get_inventory_with_fault_url_Then_fault_result_returns() throws Exception {
        ActionResponse response = southService.action(buildWrongInventoryParam(), targetID);
        assertFalse(response.isSuccess());
        assertNull(response.getMo());
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

    private Action buildAlaramParam() {
        ArrayList<Pair<String, Object>> pairs = new ArrayList<Pair<String, Object>>();
        pairs.add(new Pair<String, Object>("resource", "event_history"));
        pairs.add(new Pair<String, Object>("period", "3600"));
        return new Action(new Alarm(), Action.name(), pairs);
    }

    private Action buildInventoryParam() {
        ArrayList<Pair<String, Object>> pairs = new ArrayList<Pair<String, Object>>();
        pairs.add(new Pair<String, Object>("resource", "inventory"));
        return new Action(new Inventory(), Action.name(), pairs);
    }

    private Action buildWrongInventoryParam() {
        ArrayList<Pair<String, Object>> pairs = new ArrayList<Pair<String, Object>>();
        pairs.add(new Pair<String, Object>("resource", "inventory1"));
        return new Action(new Inventory(), Action.name(), pairs);
    }
}
