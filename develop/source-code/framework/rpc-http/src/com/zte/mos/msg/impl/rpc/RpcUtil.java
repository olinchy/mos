package com.zte.mos.msg.impl.rpc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zte.mos.annotation.MoAttribute;
import com.zte.mos.annotation.MoChild;
import com.zte.mos.domain.OperationLog;
import com.zte.mos.domain.TransactionLog;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.Maybe;
import com.zte.mos.message.Mo;
import com.zte.mos.msg.framework.operation.*;
import com.zte.mos.type.Pair;
import com.zte.mos.util.tools.JsonUtil;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import static com.zte.mos.util.tools.JsonUtil.newArrayNode;
import static com.zte.mos.util.tools.JsonUtil.newObjNode;

/**
 * Created by luoqingkai on 15-5-18.
 */
public class RpcUtil {

    public static JsonNode toReverseSyncResponseJson(int sn, int result, boolean isContinue)
    {
        ObjectNode res = newObjNode();
        res.put("SN", sn);
        res.put("result", result);
        res.put("continueFlag", isContinue);
        return res;
    }

    public static JsonNode toJson(RpcSysConfiguration sysConfig, RpcUserConfiguration userConfig)
    {
        ObjectNode node = newObjNode();
        node.put("role",sysConfig.getLocalRole());

        node.put("username", userConfig.getUserName());
        node.put("passwd", userConfig.getPassword());

        ObjectNode serverMaybe = newObjNode();
        node.put("server", serverMaybe);
        serverMaybe.put("present", true);

        ObjectNode value = newObjNode();

        serverMaybe.put("value", value);
        value.put("url", sysConfig.getLocalURL());
        value.put("sessionId", sysConfig.getLocalID().toString());

        return node;
    }

    public static ConnectResponse toConnectResponse(JsonNode node){
        int result = node.get("result").asInt();
        JsonNode sNode = node.get("sessionId");
        String sessionId = null;
        if (sNode != null){
            sessionId = sNode.asText();
        }
        ConnectResponse response = new ConnectResponse(result, sessionId);
        return response;
    }

    public static JsonNode toJson(Update cmd, String sessionId) throws Exception {
        ObjectNode reqNode = newObjNode();
        reqNode.put("sessionId", sessionId);

        ObjectNode oneNode = JsonUtil.newObjNode();
        oneNode.put("dn", cmd.getDN());

        if (cmd.getDelta() != null){
            oneNode.put("mo", deltaToJson(cmd.getDelta()));
        }else{
            oneNode.put("mo", moToJson(cmd.getMo()));
        }

        oneNode.put("moClass", cmd.getMo().getClass().getSimpleName());

        ArrayNode moListNode = newArrayNode();
        moListNode.add(oneNode);
        reqNode.put("moList", moListNode);

        Maybe<Integer> transId = cmd.getTransactionID();
        JsonNode transIdMaybe = JsonUtil.toNode(transId);

        reqNode.put("transId", transIdMaybe);

        return reqNode;
    }

    public static JsonNode toJson(Delete cmd, String sessionId) throws MOSException {
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

    private static JsonNode deltaToJson(Mo delta) throws MOSException {
        ObjectNode node = JsonUtil.newObjNode();

        HashMap<String, Object> valueMap = delta.getMo();
        for (String key : delta.getMo().keySet()){
            node.put(key, JsonUtil.toNode(valueMap.get(key)));
        }
        return node;
    }

    public static JsonNode toJson(String sessionId, int revision){
        ObjectNode node = newObjNode();
        node.put("sessionId", sessionId);
        node.put("revision", revision);
        return node;
    }

    public static JsonNode get2Json(Get get, String sessionId) throws MOSException {
        ObjectNode node = newObjNode();
        node.put("dn", get.getDN());
        node.put("sessionId", sessionId);
        Maybe<Integer> transId = get.getTransactionID();
        JsonNode transIdMaybe = JsonUtil.toNode(transId);
        node.put("transId", transIdMaybe);
        return node;
    }
    public static JsonNode commit2Json(Commit cmd, String sessionId) {
        ObjectNode node = newObjNode();
        node.put("sessionId", sessionId);
        node.put("transId", cmd.getTransactionID().getValue());
        return node;
    }
    public static JsonNode rollback2Json(Rollback cmd, String sessionId) {
        ObjectNode node = newObjNode();
        node.put("sessionId", sessionId);
        node.put("transId", cmd.getTransactionID().getValue());
        return node;
    }

    public static JsonNode reverseSync2Json(String sessionId){
        ObjectNode reqNode = newObjNode();
        reqNode.put("sessionId", sessionId);
        return reqNode;
    }

    public static JsonNode moToJson(Object mo) throws Exception {
        ObjectNode node = JsonUtil.newObjNode();
        for (Field field : mo.getClass().getFields()) {
            if (!field.isAnnotationPresent(MoChild.class) && field.isAnnotationPresent(MoAttribute.class)) {
                boolean isAccessible = field.isAccessible();
                if (!isAccessible) {
                    field.setAccessible(true);
                }
                JsonNode fieldNode = JsonUtil.toNode(field.get(mo));
                //log.info(fieldNode.toString());
                if (fieldNode.isNull()) {
//                    if (field.getType().)
//                    node.put(field.getName(), "");
                } else {
                    node.put(field.getName(), fieldNode);
                }
                field.setAccessible(isAccessible);
            }
        }
        return node;
    }

    public static JsonNode addRevision(JsonNode node, int revision) throws MOSException {
        ObjectNode oNode = (ObjectNode)node;
        Maybe<Integer> revisionMaybe = new Maybe<Integer>(revision);
        oNode.put("revision", JsonUtil.toNode(revisionMaybe));
        return oNode;
    }


    public static Object action2Json(Action actOperation, String sessionId) {
        ObjectNode node = JsonUtil.newObjNode();
        node.put("sessionId", sessionId);
        node.put("dn", actOperation.getDN());
        node.put("action", actOperation.getOperation());

        ObjectNode paraNode = JsonUtil.newObjNode();
        for (Pair<String, Object> pair : actOperation.getParas())
        {
            try
            {
                paraNode.put(pair.first(), JsonUtil.toNode(pair.second()));
            }
            catch (MOSException e)
            {
                e.printStackTrace();
            }
        }
        node.put("mo", paraNode);
        return node;

    }

    public static AckResponse toAckResponse(JsonNode ackNode) throws MOSException
    {
        int result = ackNode.get("result").asInt();
        int revision = ackNode.get("revision").asInt();
        if (result != 0)
        {
            return new AckResponse(result);
        }
        Queue<OperationLog> queue = new LinkedList<OperationLog>();
        ArrayNode arrayNode = (ArrayNode) ackNode.get("moOpList");
        for (JsonNode oneNode : arrayNode)
        {
            String moClass = null;
            String type = oneNode.get("type").asText();
            String dn = oneNode.get("dn").asText();
            if (!type.equals("del"))
            {
                moClass = oneNode.get("moClass").asText();
            }

            JsonNode moNode = oneNode.get("mo");
            String mo = JsonUtil.toString(moNode);
            queue.add(new OperationLog(type, dn, moClass, mo));
        }
        return new AckResponse(result, new TransactionLog(queue), revision);
    }

}
