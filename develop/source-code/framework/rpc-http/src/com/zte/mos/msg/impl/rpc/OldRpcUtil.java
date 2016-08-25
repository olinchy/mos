package com.zte.mos.msg.impl.rpc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.inf.Maybe;
import com.zte.mos.msg.framework.operation.Create;
import com.zte.mos.msg.framework.operation.Delete;
import com.zte.mos.msg.framework.operation.Update;
import com.zte.mos.util.tools.JsonUtil;

import static com.zte.mos.util.tools.JsonUtil.newArrayNode;
import static com.zte.mos.util.tools.JsonUtil.newObjNode;

/**
 * Created by luoqingkai on 15-12-8.
 */
public class OldRpcUtil
{

    public static JsonNode toJson(String sessionId, Create cmd) throws Exception {
        ObjectNode reqNode = newObjNode();
        reqNode.put("sessionId", sessionId);

        ManagementObject mo = cmd.getMo();
        ObjectNode oneNode = newObjNode();
        oneNode.put("dn", mo.dn().toString());
        oneNode.put("mo", RpcUtil.moToJson(mo));
        oneNode.put("moClass", mo.getClass().getSimpleName());

        ArrayNode moListNode = newArrayNode();
        moListNode.add(oneNode);
        reqNode.put("moList", moListNode);

        JsonNode transIdMaybe = JsonUtil.toNode(cmd.getTransactionID());
        reqNode.put("transId", transIdMaybe);

        return reqNode;
    }
    public static JsonNode toJson(Update cmd, String sessionId) throws Exception
    {
        ObjectNode reqNode = newObjNode();
        reqNode.put("sessionId", sessionId);

        ManagementObject mo = cmd.getMo();
        ObjectNode oneNode = newObjNode();
        oneNode.put("dn", mo.dn().toString());
        oneNode.put("mo", RpcUtil.moToJson(mo));
        oneNode.put("moClass", mo.getClass().getSimpleName());

        ArrayNode moListNode = newArrayNode();
        moListNode.add(oneNode);
        reqNode.put("moList", moListNode);

        Maybe<Integer> transId = cmd.getTransactionID();
        JsonNode transIdMaybe = JsonUtil.toNode(transId);

        reqNode.put("transId", transIdMaybe);

        return reqNode;
    }

    public static JsonNode toJson(Delete cmd, String sessionId) throws Exception
    {
        ObjectNode reqNode = newObjNode();
        reqNode.put("sessionId", sessionId);

        ArrayNode dnListNode = newArrayNode();
        dnListNode.add(cmd.getDN());
        reqNode.put("dnList", dnListNode);

        Maybe<Integer> transId = cmd.getTransactionID();
        JsonNode transIdMaybe = JsonUtil.toNode(transId);

        reqNode.put("transId", transIdMaybe);

        return reqNode;
    }

    public static JsonNode toJson(int sn, int result, boolean isContinue){
        ObjectNode res = newObjNode();
        res.put("SN", sn);
        res.put("result", result);
        res.put("continueFlag", isContinue);
        return res;
    }

}
