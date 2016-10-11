package com.mos.lite.ut;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import com.zte.mos.domain.DN;
import com.zte.mos.inf.Maybe;
import com.zte.mos.message.Mo;
import com.zte.mos.util.tools.JsonUtil;
import org.junit.Test;

import java.net.URL;
import java.util.UUID;

/**
 * Created by olinchy on 16-2-4.
 */
public class TestHttp
{
    @Test
    public void test() throws Throwable
    {
        JsonRpcHttpClient client = new JsonRpcHttpClient(new URL("http://localhost:8282/mos"));

        JsonNode node = client.invoke(
                "login",
                new Object[]{"MOSSERVICE", "", "", JsonUtil.toNode(new Maybe<String>(UUID.randomUUID().toString()))},
                JsonNode.class);
//        @JsonRpcParam("sessionId") String sessionId,
//        @JsonRpcParam("moList") JsonNode moList,
//        @JsonRpcParam("transId") JsonNode transId)
        ObjectNode objectNode = JsonUtil.newObjNode();
        objectNode.put("sessionId", node.get("sessionId").asText());
        objectNode.put("moList", JsonUtil.toNode(new Mo[]{new Mo("SecurityProtocolMode").setDn(
                new DN("/Security/1/SecurityCfg/1/SecurityProtocolMode/1")).setAttr("http", "https")}));
        objectNode.put("transId", JsonUtil.toNode(new Maybe<Integer>(null)));

        node = client.invoke("set", objectNode, JsonNode.class);
        System.out.println(node);
    }
}
