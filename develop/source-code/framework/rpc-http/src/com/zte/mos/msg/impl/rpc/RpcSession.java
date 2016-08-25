package com.zte.mos.msg.impl.rpc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.googlecode.jsonrpc4j.JsonRpcHttpAsyncClient;
import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.domain.TargetAddress;
import com.zte.mos.exception.BerkeleyDBException;
import com.zte.mos.exception.MOSException;
import com.zte.mos.httpservice.JsonRpcHttpClientCreator;
import com.zte.mos.message.Result;
import com.zte.mos.msg.framework.except.*;
import com.zte.mos.msg.framework.observer.DefaultObserver;
import com.zte.mos.msg.framework.operation.*;
import com.zte.mos.msg.framework.session.AbstractSession;
import com.zte.mos.msg.framework.session.ISessionConfiguration;
import com.zte.mos.msg.impl.rpc.decode.ResponseDecoder;
import com.zte.mos.msg.impl.rpc.decode.ReverseSyncDecoder;
import com.zte.mos.storage.DataUnit;
import com.zte.mos.storage.MapDbService;
import com.zte.mos.util.basic.Logger;
import com.zte.mos.util.msg.MoEvent;
import com.zte.mos.util.tools.JsonUtil;
import com.zte.smartlink.deliver.DeliverService;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Future;

import static com.zte.mos.msg.impl.rpc.RpcUtil.moToJson;
import static com.zte.mos.storage.MapDbService.DbNameEnum.RPC_SECURITY;
import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by luoqingkai on 15-5-13.
 * modify on 2015-09-24
 */
public class RpcSession extends AbstractSession
{
    private static Logger log = logger(RpcSession.class);
    String sessionId;
    private RpcUserConfiguration userConfiguration;
    private RpcSysConfiguration sysConfiguration;
    private boolean connected = false;
    private boolean isConSwitchOpen = false;
    JsonRpcHttpClient rpcClient;// = new JsonRpcHttpClient(null);
    JsonRpcHttpAsyncClient asyncClient;// = new JsonRpcHttpAsyncClient(null);
    private static final String RPC_STATE_ABNORMAL = "abnormal";
    private static final String RPC_STATE_NORMAL = "normal";

    public RpcSession(TargetAddress myAddress, RpcUserConfiguration userConfiguration) throws MalformedURLException
    {
        super(myAddress);
        this.userConfiguration = userConfiguration;
        this.sysConfiguration = new RpcSysConfiguration();

        buildRpcClient();
    }

    private void buildRpcClient() throws MalformedURLException
    {
        URL targetURL = userConfiguration.getTargetURL(myAddress.getIpAddress(), myAddress.getMosHead().version());

        rpcClient = JsonRpcHttpClientCreator.create(targetURL);
        rpcClient.setConnectionTimeoutMillis(userConfiguration.getConnectionTimeoutMillis());
        asyncClient = JsonRpcHttpClientCreator.createAsync(targetURL);
    }

    @Override
    public void setConnectSwitch(boolean open)
    {
        this.isConSwitchOpen = open;
        if (!open)
        {
            this.connected = false;
            notifyPollApp(RPC_STATE_ABNORMAL);
        }
    }

    private void notifyPollApp(String rpcState)
    {
        String dn = myAddress.getTargetID().substring(0, myAddress.getTargetID().indexOf("Product") - 1);
        MoEvent event = new MoEvent(dn);
        event.put("rpcState", rpcState);
        try
        {
            Result result = new DeliverService("RPC_HTTP").send(event);
            if (!result.isSuccess())
            {
                log.warn("send event to poll fail!");
            }
        }
        catch (MOSException e)
        {
            log.error(e.getMessage(), e);
        }
    }

    boolean isConnectSwitchOpen()
    {
        return this.isConSwitchOpen;
    }

    public String getLocalID()
    {
        return this.sysConfiguration.getLocalID().toString();
    }

    public TargetAddress getMyAddress()
    {
        return myAddress;
    }

    @Override
    public boolean isSecure()
    {
        return userConfiguration.isSecure();
    }

    @Override
    public void setIp(String ipAddress) throws MalformedURLException
    {
        super.setIp(ipAddress);
        buildRpcClient();
    }

    void setSessionId(Future<JsonNode> future)
    {
        try
        {
            JsonNode node = future.get();
            int result = node.get("result").asInt();
            if (result == 0)
            {
                JsonNode sNode = node.get("sessionId");
                this.sessionId = sNode.asText();
                this.connected = true;
                notifyPollApp(RPC_STATE_NORMAL);
            }else {
                log.debug("login fail!" + node);
                notifyPollApp(RPC_STATE_ABNORMAL);
            }
        }
        catch (Exception e)
        {
            notifyPollApp(RPC_STATE_ABNORMAL);
            log.warn(this.getMyAddress().getTargetID(), e);
        }
    }

    public RpcConnectResponse connect()
    {
        JsonNode node = RpcUtil.toJson(sysConfiguration, userConfiguration);
        Future<JsonNode> future = asyncClient.invoke("login", node, JsonNode.class);
        RpcConnectResponse response = new RpcConnectResponse(future, this);
        ConnectFutureProcessor.add(response);
        return response;
    }
//    public void connectInSyncMode() {
//        JsonNode node = RpcUtil.toJson(sysConfiguration, userConfiguration);
//        try {
//            JsonNode res = rpcClient.invoke("login", node, JsonNode.class);
//            if (res.get("sessionId") != null)
//            {
//                this.sessionId = res.get("sessionId").asText();
//                this.connected=true;
//            }
//        } catch (Throwable throwable) {
//            log.warn("login in sync mode failed", throwable);
//        }
//    }

    public boolean isConnected()
    {
        return connected;
    }

    void checkConnect(IOperation op) throws NotConnectException
    {
        if (!connected)
        {
            throw new NotConnectException(op.getDN());
        }
    }

    @Override
    public PingResponse ping() throws NotConnectException
    {
        PingResponse response;
        if (!connected)
        {
            throw new NotConnectException(this.myAddress.getIpAddress());
        }
        JsonNode node = RpcUtil.toJson(this.sessionId, this.myAddress.getSystem().revision());
        Future<JsonNode> responseNode = this.asyncClient.invoke("incSyncRequest", node, JsonNode.class);
        response = new PingResponse(responseNode, this.getMyAddress());
        return response;
    }

    @Override
    public CreateResponse create(Create createOperation) throws MsgFrameException
    {
        CreateResponse response;
        checkConnect(createOperation);
        startTransOfOper(createOperation);
        try
        {
            JsonNode node = moToJson(createOperation.getMo());
            node = RpcUtil.addRevision(node, this.myAddress.getSystem().revision());
            JsonNode responseNode = rpcClient.invoke("add", node, JsonNode.class);
            response = (CreateResponse) ResponseDecoder.decode(createOperation.getClass(), responseNode);
        }
        catch (Throwable e)
        {
            response = new CreateResponse(1, createOperation.getTransactionID().getValue());
            response.setThrowable(e);
        }
        return response;
    }

    @Override
    public UpdateResponse update(Update updateOperation) throws MsgFrameException
    {
        UpdateResponse response;
        checkConnect(updateOperation);
        startTransOfOper(updateOperation);
        try
        {
            JsonNode node = RpcUtil.toJson(updateOperation, this.sessionId);
            node = RpcUtil.addRevision(node, this.myAddress.getSystem().revision());
            JsonNode responseNode = rpcClient.invoke("set", node, JsonNode.class);
            response = (UpdateResponse) ResponseDecoder.decode(updateOperation.getClass(), responseNode);
        }
        catch (Throwable e)
        {
            response = new UpdateResponse(1, updateOperation.getTransactionID().getValue());
            response.setThrowable(e);
        }
        return response;
    }

    protected void startTransOfOper(IOperation updateOperation) throws MsgFrameException
    {
        try {
            if (updateOperation.getTransactionID().nothing())
            {
                updateOperation.setTransactionId(this.startTrans());
            }
        }catch (Exception ex){
            log.info("the rpc server of 241 has no handler of StartTrans");
            log.info(ex.getMessage(), ex);
        }

    }

    @Override
    public DeleteResponse delete(Delete deleteOperation) throws MsgFrameException
    {
        DeleteResponse response;
        checkConnect(deleteOperation);
        this.startTransOfOper(deleteOperation);
        try
        {
            JsonNode node = RpcUtil.toJson(deleteOperation, sessionId);
            node = RpcUtil.addRevision(node, myAddress.getSystem().revision());
            JsonNode responseNode = rpcClient.invoke("del", node, JsonNode.class);
            response = (DeleteResponse) ResponseDecoder.decode(deleteOperation.getClass(), responseNode);
        }
        catch (Throwable e)
        {
            response = new DeleteResponse(1, deleteOperation.getTransactionID().getValue());
            response.setThrowable(e);
        }
        return response;
    }

    @Override
    public AckResponse commit(Commit commit) throws NotConnectException
    {
        AckResponse response;
        checkConnect(commit);
        try
        {
            JsonNode responseNode =
                    rpcClient.invoke("commit", RpcUtil.commit2Json(commit, this.sessionId), JsonNode.class);
            response = RpcUtil.toAckResponse(responseNode);
            return response;
        }
        catch (Throwable e)
        {
            AckResponse resp = new AckResponse(1);
            resp.setThrowable(e);
            return resp;
        }
    }

    @Override
    public AckResponse rollback(Rollback rollback) throws NotConnectException
    {
        checkConnect(rollback);
        try
        {
            rpcClient.invokeNotification("rollback", RpcUtil.rollback2Json(rollback, this.sessionId));
            return new AckResponse(0);
        }
        catch (Throwable e)
        {
            AckResponse resp = new AckResponse(1);
            resp.setThrowable(e);
            return resp;
        }
    }

    private ManagementObject getFromRemote(Get getOperation) throws MsgFrameException
    {
        checkConnect(getOperation);

        JsonNode responseNode = null;
        try
        {
            responseNode = rpcClient.invoke("get",
                                            RpcUtil.get2Json(getOperation, this.sessionId), JsonNode.class);
        }
        catch (Throwable throwable)
        {
            throw new RemoteException(-1);
        }
        int result = responseNode.get("result").asInt();
        if (result != 0)
        {
            throw new RemoteException(result);
        }
        String moClass;
        Object moClazzObj = responseNode.get("moClass");
        if (moClazzObj == null)
        {
            moClass = getOperation.getMoClazz();
        }
        else
        {
            moClass = moClazzObj.toString();
        }

        JsonNode moNode = responseNode.get("mo");

        ManagementObject mo = myAddress.getModelHead().buildMo(moClass, moNode.toString());
        if (mo == null)
        {
            throw new ParseException();
        }

        mo.setDn(getOperation.getDN());
        return mo;
    }

    @Override
    public GetResponse get(Get getOperation) throws NotConnectException
    {
        GetResponse response;
        try
        {
            ManagementObject mo = this.getFromRemote(getOperation);
            response = new GetResponse(0, mo.toMoClass());
        }
        catch (RemoteException e)
        {
            response = new GetResponse(e.getErrorCode(), null);
        }
        catch (Exception e)
        {
            response = new GetResponse(1, null);
            response.setThrowable(e);
        }
        return response;
    }

    @Override
    public ReverseSynResponse reverseSyn(ReverseSyn reverseSynOperation) throws NotConnectException
    {
        checkConnect(reverseSynOperation);
        ReverseSynResponse response;
        logger(this.getClass()).debug(getMyAddress().getTargetID() + " start to do all sync request");
        try
        {
            JsonNode res = rpcClient.invoke("allSyncRequest", RpcUtil.reverseSync2Json(this.sessionId), JsonNode.class);
            response = ReverseSyncDecoder.decode(res, myAddress.getModelHead());
        }
        catch (Throwable e)
        {
            response = new ReverseSynResponse(1, -1, DefaultObserver.dummy);
            response.setThrowable(e);
        }
        logger(this.getClass()).debug(getMyAddress().getTargetID() + " end to do all sync request, result :" + response.getResult());
        return response;
    }

    @Override
    public ActionResponse act(Action oper)
    {

        ActionResponse rsp;
        JsonNode result;
        try
        {
            result = rpcClient.invoke("action", RpcUtil.action2Json(oper, this.sessionId), JsonNode.class);
            rsp = (ActionResponse) ResponseDecoder.decode(oper.getClass(), result);
        }
        catch (Throwable e)
        {
            rsp = new ActionResponse(1, null);
            rsp.setThrowable(e);
        }
        return rsp;
    }

    @Override
    public ManagementObject getMo(Get getOperation) throws MsgFrameException
    {

        return this.getFromRemote(getOperation);
    }

    @Override
    public String protocol()
    {
        return "RPC";
    }

    @Override
    public boolean mosSupport()
    {
        return true;
    }

    @Override
    public void localRollback()
    {

    }

    @Override
    public void setConfiguration(ISessionConfiguration cfg) throws InvalidUrlException
    {
        RpcUserConfiguration bakConfiguration = userConfiguration;
        if (cfg instanceof RpcUserConfiguration)
        {
            this.userConfiguration = (RpcUserConfiguration) cfg;
            recordSetCfgMsg(userConfiguration);
            try
            {
                buildRpcClient();
                this.saveToDB();
            }
            catch (MalformedURLException e)
            {
                this.userConfiguration = bakConfiguration;
                log.error(e.getMessage(), e);
                throw new InvalidUrlException();
            }
        }
    }

    private void recordSetCfgMsg(RpcUserConfiguration cfg){
        try {
            log.info("set Configuration " + cfg.getTargetURL(myAddress.getIpAddress(), myAddress.getMosHead().version()) );
        } catch (MalformedURLException e) {
            log.info(e.getMessage(), e);
        }
    }

    void saveToDB()
    {
        // IP is the primarykey
        DataUnit unit = new DataUnit(this.myAddress.getIpAddress());
        unit.putAll(userConfiguration.toMap());
        try
        {
            MapDbService.getDB(RPC_SECURITY).put(unit);
        }
        catch (MOSException e)
        {
            log.error(this.myAddress + " rpc config save to db error:", e);
        }
    }

    void delFromDB()
    {
        try
        {
            MapDbService.getDB(RPC_SECURITY).delete(myAddress.getIpAddress());
        }
        catch (BerkeleyDBException e)
        {
            log.error(this.myAddress + " rpc config delete from db error:", e);
        }
    }

    private int startTrans() throws MsgFrameException
    {
        JsonNode result;
        try
        {
            ObjectNode node = JsonUtil.newObjNode();
            node.put("sessionId", sessionId);

            result = rpcClient.invoke("startTrans", node, JsonNode.class);
            if (result.get("result").asInt() == 0)
            {
                return result.get("transId").asInt();
            }
        }
        catch (Throwable throwable)
        {
            throw new LinkException(throwable);
        }
        throw new StartTransactionOnRemoteException(
                "sessionId: " + sessionId + ", ip: " + rpcClient.getServiceUrl().toString());
    }
}
