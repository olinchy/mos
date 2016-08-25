package com.zte.mos.msg.impl.rpc.decode;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.domain.ModelHead;
import com.zte.mos.exception.MOSException;
import com.zte.mos.msg.framework.observer.DefaultObserver;
import com.zte.mos.msg.framework.operation.ReverseSynResponse;
import com.zte.mos.util.tools.JsonUtil;

/**
 * Created by luoqingkai on 15-11-13.
 */
public class ReverseSyncDecoder{

    public static ReverseSynResponse decode(JsonNode node, ModelHead header) throws MOSException {
        int revision = node.get("revision").asInt();
        ArrayNode array = (ArrayNode) node.get("moList");
        ManagementObject[] moArray = new ManagementObject[array.size()];

        int i = 0;
        for (JsonNode oneNode : array){
            String moClass = oneNode.get("moClass").asText();
            String dn = oneNode.get("dn").asText();
            JsonNode moNode = oneNode.get("mo");

            String moStr = JsonUtil.toString(moNode);
            ManagementObject mo = header.buildMo(moClass);
            if (mo == null)
            {
                throw new MOSException("build mo fails. className = " + moClass);
            }
            if (moNode.isNull())
            {
                moArray[i] = mo;
            }
            else
            {
                moArray[i] = JsonUtil.toObject(moStr, mo.getClass());
            }
            moArray[i].setDn(dn);
            i++;

        }
        ReverseSynResponse response = new ReverseSynResponse(0, revision, moArray, DefaultObserver.dummy);
        return response;
    }

}
