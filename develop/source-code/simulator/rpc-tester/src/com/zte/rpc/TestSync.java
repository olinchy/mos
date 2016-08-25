package com.zte.rpc;

import com.fasterxml.jackson.databind.JsonNode;
import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import com.zte.mos.inf.Maybe;
import com.zte.mos.util.tools.JsonUtil;
import org.eclipse.jetty.server.Server;

import java.net.URL;

/**
 * Created by olinchy on 16-7-8.
 */
public class TestSync
{
    public static void main(String[] args) throws Throwable
    {
        JsonRpcHttpClient client = new JsonRpcHttpClient(new URL(args[0]));
        JsonNode node = client.invoke(
                "login",
                new Object[]{"CLI", "ems", "ems", JsonUtil.toNode(new Maybe<Server>(null))},
                JsonNode.class);

        System.out.println(node);
    }
}
