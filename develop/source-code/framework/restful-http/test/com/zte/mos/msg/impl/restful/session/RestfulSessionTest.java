package com.zte.mos.msg.impl.restful.session;

import com.zte.mos.domain.TargetAddressImpl;
import com.zte.mos.msg.framework.operation.Action;
import com.zte.mos.msg.framework.operation.ActionResponse;
import com.zte.mos.msg.impl.restful.RestfulConfiguration;
import com.zte.mos.msg.impl.restful.RestfulSession;
import com.zte.mos.type.Pair;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static org.junit.Assert.assertEquals;

/**
 * Created by dongyue on 16-7-13.
 */
public class RestfulSessionTest {

    @Test
    public void testAction() throws Exception {
        RestfulConfiguration configuration = buildConfiguration();
        Action actOperation = buildOperationParam();
        RestfulSession session = new RestfulSession(new TargetAddressImpl("", "", new MockImagedSystem()), configuration);
        ActionResponse response = session.act(actOperation);
        assertResult(response);
    }

    private void assertResult(ActionResponse response) {
        LinkedHashMap jsonResult = response.getMo();
        assertEquals("e000001", jsonResult.get("guid"));
    }

    private Action buildOperationParam() {
        ArrayList<Pair<String, Object>> pairs = new ArrayList<Pair<String, Object>>();
        pairs.add(new Pair<String, Object>("resource", "inventory"));
        return new Action(new MockManagementObject(), null, pairs);
    }

    private RestfulConfiguration buildConfiguration() {
        HashMap map = new HashMap();
        map.put("emsIp", "127.0.0.1");
        map.put("net", "net-name");
        map.put("guid", "e000001");
        return new RestfulConfiguration(map);
    }
}