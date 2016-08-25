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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static com.zte.mos.msg.SupportedProtocol.RESTFUL;
import static com.zte.mos.msg.framework.operation.OperEnum.Action;

/**
 * Created by dongyue on 16-7-28.
 */
public class AlarmRestfulProcessor extends MsgProcess {
    private RestfulSession session;
    @Override
    public void setSession(ISession session) {
        this.session = (RestfulSession) session;
    }

    @Override
    public ActionResponse act(Action actOperation) {
        try {
            JsonNode node = getAlarmDataOfNetWork(actOperation);
            List<LinkedHashMap<String, String>> alarmData = findAlarmDataByGuid(node);
            return buildActionResponse(alarmData);
        } catch (Exception e) {
            ActionResponse rsp = new ActionResponse(1, null);
            rsp.setThrowable(e);
            return rsp;
        }
    }

    private ActionResponse buildActionResponse(List<LinkedHashMap<String, String>> alarmData) {
        LinkedHashMap<String, List<LinkedHashMap<String, String>>> map = new LinkedHashMap<String, List<LinkedHashMap<String, String>>>();
        map.put("result", alarmData);
        return new ActionResponse(0, map);
    }

    private JsonNode getAlarmDataOfNetWork(Action actOperation) {
        ActionResponse netRsp = session.act(actOperation);
        return (JsonNode) netRsp.getMo().get("result");
    }

    private List<LinkedHashMap<String, String>> findAlarmDataByGuid(JsonNode node) throws MOSException {
        List<LinkedHashMap<String, String>> alarmData = new ArrayList<LinkedHashMap<String, String>>();
        for (int i = 0; i < node.size(); i++) {
            JsonNode alarmNode = node.get(i);
            if (alarmNode.get("source").textValue().equals(session.getGuid())){
                LinkedHashMap alarm = JsonUtil.toObject(JsonUtil.toString(alarmNode), LinkedHashMap.class);
                alarmData.add(alarm);
            }
        }
        return alarmData;
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
    protected boolean isSupported(TargetAddress target) {
        return true;
    }

    @Override
    public String[] moTypes() {
        return new String[]{"Alarm"};
    }

    @Override
    public String operation() {
        return Action.name() ;
    }

    @Override
    public String protocol() {
        return RESTFUL.name();
    }
}
