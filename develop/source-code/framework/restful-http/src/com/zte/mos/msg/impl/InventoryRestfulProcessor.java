package com.zte.mos.msg.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.zte.mos.domain.TargetAddress;
import com.zte.mos.exception.MOSException;
import com.zte.mos.msg.framework.MsgProcess;
import com.zte.mos.msg.framework.operation.Action;
import com.zte.mos.msg.framework.operation.ActionResponse;
import com.zte.mos.msg.framework.session.ISession;
import com.zte.mos.msg.impl.restful.RestfulSession;
import com.zte.mos.util.tools.JsonUtil;

import java.util.LinkedHashMap;

import static com.zte.mos.msg.SupportedProtocol.RESTFUL;
import static com.zte.mos.msg.framework.operation.OperEnum.Action;

/**
 * Created by dongyue on 16-7-26.
 */
public class InventoryRestfulProcessor extends MsgProcess {
    private RestfulSession session;
    @Override
    public void setSession(ISession session) {
        this.session = (RestfulSession) session;
    }

    @Override
    protected boolean isSupported(TargetAddress target) {
        return true;
    }

    @Override
    public boolean isTypeSupported(String modelType) {
        return true;
    }

    @Override
    public boolean isVersionSupported(String modelVersion) {
        return true;
    }

    @Override
    public ActionResponse act(Action actOperation) {
        ActionResponse netRsp = session.act(actOperation);
        try {
            JsonNode node = (JsonNode) netRsp.getMo().get("result");
            Object neJson = node.get(session.getGuid());
            return new ActionResponse(0, JsonUtil.toObject(JsonUtil.toString(neJson), LinkedHashMap.class));
        } catch (Exception e) {
            ActionResponse rsp = new ActionResponse(1, null);
            rsp.setThrowable(e);
            return rsp;
        }
    }

    @Override
    public String[] moTypes() {
        return new String[]{"Inventory"};
    }

    @Override
    public String operation() {
        return Action.name();
    }

    @Override
    public String protocol() {
        return RESTFUL.name();
    }
}
