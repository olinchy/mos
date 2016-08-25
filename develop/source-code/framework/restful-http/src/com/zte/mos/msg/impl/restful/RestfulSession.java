package com.zte.mos.msg.impl.restful;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.domain.TargetAddress;
import com.zte.mos.exception.BerkeleyDBException;
import com.zte.mos.exception.MOSException;
import com.zte.mos.msg.framework.except.InvalidUrlException;
import com.zte.mos.msg.framework.except.MsgFrameException;
import com.zte.mos.msg.framework.except.NotConnectException;
import com.zte.mos.msg.framework.operation.*;
import com.zte.mos.msg.framework.session.AbstractSession;
import com.zte.mos.msg.framework.session.ISessionConfiguration;
import com.zte.mos.storage.DataUnit;
import com.zte.mos.storage.MapDbService;
import com.zte.mos.type.Pair;
import com.zte.mos.util.basic.Logger;
import com.zte.mos.util.tools.JsonUtil;

import java.util.LinkedHashMap;
import java.util.List;

import static com.zte.mos.storage.MapDbService.DbNameEnum.RESTFUL;

/**
 * Created by dongyue on 16-7-13.
 */
public class RestfulSession extends AbstractSession {

    Logger logger = Logger.logger(RestfulSession.class);
    private static final int TIME_OUT = 3 * 1000;
    private RestfulConfiguration configuration;
    Client client = Client.create();
    public RestfulSession(TargetAddress myAddress, RestfulConfiguration configuration) {
        super(myAddress);
        this.configuration = configuration;
        client.setConnectTimeout(TIME_OUT);
    }

    @Override
    public String protocol() {
        return "RESTFUL";
    }

    @Override
    public void setConnectSwitch(boolean open) {

    }

    @Override
    public boolean mosSupport() {
        return false;
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public void localRollback() {

    }

    @Override
    public void setConfiguration(ISessionConfiguration cfg) throws InvalidUrlException {
        this.configuration = (RestfulConfiguration) cfg;
        //saveToDB();
    }

    @Override
    protected boolean isSecure() {
        return false;
    }

    @Override
    public PingResponse ping() throws NotConnectException {
        return null;
    }

    @Override
    public CreateResponse create(Create createOperation) throws MsgFrameException {
        return null;
    }

    @Override
    public UpdateResponse update(Update updateOperation) throws MsgFrameException {
        return null;
    }

    @Override
    public DeleteResponse delete(Delete deleteOperation) throws MsgFrameException {
        return null;
    }

    @Override
    public AckResponse commit(Commit commit) throws NotConnectException {
        return null;
    }

    @Override
    public AckResponse rollback(Rollback rollback) throws NotConnectException {
        return null;
    }

    @Override
    public GetResponse get(Get getOperation) throws NotConnectException {

        return null;
    }

    @Override
    public ReverseSynResponse reverseSyn(ReverseSyn reverseSynOperation) throws NotConnectException {
        return null;
    }

    @Override
    public ActionResponse act(Action actOperation) {
        List<Pair<String, Object>> paras = actOperation.getParas();
        String resourceName = (String) paras.get(0).second();
        WebResource resource = client.resource(configuration.getUrl() + resourceName);
        resource = addQueryParam(paras, resource);
        try {
            String content = resource.get(String.class);
            LinkedHashMap<String, JsonNode> map = new LinkedHashMap<String, JsonNode>();
            map.put("result", JsonUtil.toNode(content));
            return new ActionResponse(0, map);
        } catch (Exception e) {
            ActionResponse rsp = new ActionResponse(1, null);
            rsp.setThrowable(e);
            return rsp;
        }
    }

    private WebResource addQueryParam(List<Pair<String, Object>> paras, WebResource resource) {
        for (int i = 1; i < paras.size(); i++) {
            Pair<String, Object> pair = paras.get(i);
            resource = resource.queryParam(pair.first(), String.valueOf(pair.second()));
        }
        return resource;
    }

    @Override
    public ManagementObject getMo(Get getOperation) throws MsgFrameException {
        return null;
    }

    void saveToDB()
    {
        // IP is the primarykey
        DataUnit unit = new DataUnit(this.myAddress.getTargetID());
        unit.putAll(configuration.toMap());
        try
        {
            MapDbService.getDB(RESTFUL).put(unit);
        }
        catch (MOSException e)
        {
            logger.error(this.myAddress + " restful config save to db error:", e);
        }
    }

    void delFromDB()
    {
        try
        {
            MapDbService.getDB(RESTFUL).delete(myAddress.getTargetID());
        }
        catch (BerkeleyDBException e)
        {
            logger.error(this.myAddress + " restful config delete from db error:", e);
        }
    }

    public String getGuid(){
        return configuration.getGuid();
    }
}
