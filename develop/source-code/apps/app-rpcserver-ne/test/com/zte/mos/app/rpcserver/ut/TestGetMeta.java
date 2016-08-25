package com.zte.mos.app.rpcserver.ut;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.net.URL;
import java.util.*;

import static com.zte.mos.util.tools.JsonUtil.newObjNode;
import static org.junit.Assert.assertTrue;

/**
 * Created by olinchy on 7/10/14 for MO_JAVA.
 */
@RunWith(Parameterized.class)
public class TestGetMeta
{
    static JsonRpcHttpClient client = null;
    private String name;

    public TestGetMeta(String name)
    {
        this.name = name;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data()
    {
        return Arrays.asList(new Object[][]{
                {"Board"},
                {"AcrossLevelEnum"},
                {"NPlus1PathStatusEnum"},
        });

    }

    @BeforeClass
    public static void setUp() throws Exception
    {
        client = new JsonRpcHttpClient(new URL("http://127.0.0.1:54321/"));
    }

    @Ignore
    @org.junit.Test
    public void test_get_mo() throws Throwable
    {
        ObjectNode para = newObjNode();
        para.put("dn", "/Ems/1/Ne/mw.nr8250=8081");
        para.put("name", name);
        JsonNode node = client.invoke("get_meta", para, JsonNode.class);
        assertTrue(node != null);
        assertTrue(node.findValue("meta") != null);
        System.out.println(node);
    }

}
