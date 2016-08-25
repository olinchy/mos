package com.zte.mos.msg.impl.restful.southservice;

import com.odb.database.HashMapSimpleDB;
import com.zte.mos.domain.TargetAddressImpl;
import com.zte.mos.msg.framework.MsgProcessPool;
import com.zte.mos.msg.framework.SouthService;
import com.zte.mos.msg.framework.operation.Action;
import com.zte.mos.msg.framework.operation.ActionResponse;
import com.zte.mos.msg.framework.operation.OperEnum;
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
import static com.zte.mos.storage.MapDbService.DbNameEnum.RESTFUL;
import static org.junit.Assert.assertEquals;

/**
 * Created by dongyue on 16-7-15.
 */
public class TestSouthserviceForUR2000_Recover {
    ///////////////////////////////todo no need test db an more
    private final String ipAddress = "127.0.0.1";
    private String targetID = "/Ems/1/Ne/1/Product/1";
    private final TargetAddressImpl targetAddress = new TargetAddressImpl(targetID, ipAddress, new MockImagedSystem());
    SouthService southService = new SouthService();

    @BeforeClass
    public static void start() throws IOException, InterruptedException {
        MsgProcessPool.register(InventoryRestfulProcessor.class);
        ProtocolService.setService(new MockProtocolService());
        MockUR2000.start();
        new RestfulSessionService();
        MapDbService.setService(RESTFUL, new HashMapSimpleDB<String, DataUnit>());
        SessionConfigFactory.register(new RestfulSessionBuilder());
    }

    @AfterClass
    public static void end(){
        MockUR2000.stop();
    }

    @Before
    public void setUp() throws Exception {


    }

    @Test
    public void Given_registered_target_When_modified_session_Then_saved_to_db() throws Exception {
        southService.register(targetAddress);
        DataUnit initialData = MapDbService.getDB(RESTFUL).get(targetID);
        assertEquals("e000001", initialData.getData().get("guid"));

        southService.setSessionCfg(buildConfiguration("guid", "e000002"), targetID);
        DataUnit changedData = MapDbService.getDB(RESTFUL).get(targetID);
        assertEquals("e000002", changedData.getData().get("guid"));
    }

    @Test
    public void Given_session_cfg_When_reover_Then_session_built() throws Exception {

        DataUnit savedData = new DataUnit(targetID);
        savedData.putAll(buildConfiguration("emsIp", "127.0.0.1").toMap());
        MapDbService.getDB(RESTFUL).put(savedData);

        southService.recover(targetAddress);
        southService.setConnectSwitch(targetID, ONLINE);

        ActionResponse inventory = southService.action(buildOperationParam("inventory"), targetID);
        assertEquals("aa:aa:aa:aa:aa:aa", inventory.getMo().get("mac_address"));
    }

    private Action buildOperationParam(String url) {
        ArrayList<Pair<String, Object>> pairs = new ArrayList<Pair<String, Object>>();
        pairs.add(new Pair<String, Object>("resource", url));
        return new Action(new Inventory(), OperEnum.Action.name(), pairs);
    }
    private RestfulConfiguration buildConfiguration(String key, String value) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("emsIp", "127.0.0.1");
        map.put("net", "net-name");
        map.put("guid", "e000001");
        map.put(key, value);
        return new RestfulConfiguration(map);
    }
}
