package com.zte.mos.msg.impl.rpc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zte.mos.domain.TargetAddress;
import com.zte.mos.msg.framework.except.MsgFrameException;
import com.zte.mos.msg.framework.except.NotConnectException;
import com.zte.mos.msg.framework.observer.DefaultObserver;
import com.zte.mos.msg.framework.operation.*;
import com.zte.mos.msg.impl.rpc.decode.ResponseDecoder;
import com.zte.mos.util.tools.JsonUtil;

import java.net.MalformedURLException;
import java.util.concurrent.Future;

/**
 * Created by luoqingkai on 15-12-8.
 */
public class OldRpcSession extends RpcSession {
    public OldRpcSession(TargetAddress myAddress, RpcUserConfiguration userConfiguration)
            throws MalformedURLException {
        super(myAddress, userConfiguration);
    }

    @Override
    public ReverseSynResponse reverseSyn(ReverseSyn reverseSynOperation) throws NotConnectException {
        this.checkConnect(reverseSynOperation);
        ReverseSynResponse response = new ReverseSynResponse(0, 0, null);
        try{
            ObjectNode node = JsonUtil.newObjNode();
            node.put("sessionId", this.sessionId);
            rpcClient.invokeNotification("forceSyncToRemote", node);
        }catch (Throwable e){
            response = new ReverseSynResponse(1, -1, DefaultObserver.dummy);
            response.setThrowable(e);
        }
        return response;
    }

    @Override
    public PingResponse ping() throws NotConnectException {
        PingResponse response;
        if (!isConnected()){
            throw new NotConnectException(myAddress.getIpAddress());
        }
        JsonNode node = RpcUtil.toJson(sessionId, myAddress.getSystem().revision());
        Future<JsonNode> responseNode = asyncClient.invoke("ping", node, JsonNode.class);
        response = new RpcPingResponse(responseNode, this.getMyAddress(),this);
        return response;
    }

    @Override
    public CreateResponse create(Create createOperation) throws MsgFrameException {
        CreateResponse response;
        checkConnect(createOperation);
        startTransOfOper(createOperation);

        try {
            JsonNode node = OldRpcUtil.toJson(sessionId, createOperation);
            JsonNode responseNode = rpcClient.invoke("add", node, JsonNode.class);
            response = (CreateResponse) ResponseDecoder.decode(createOperation.getClass(), responseNode);
        } catch (Throwable e) {
            response = new CreateResponse(1, createOperation.getTransactionID().getValue());
            response.setThrowable(e);
        }
        return response;
    }

    @Override
    public UpdateResponse update(Update updateOperation) throws MsgFrameException {
        UpdateResponse response;
        checkConnect(updateOperation);
        startTransOfOper(updateOperation);

        try {
            JsonNode node = RpcUtil.toJson(updateOperation, this.sessionId);
            JsonNode responseNode = rpcClient.invoke("set", node, JsonNode.class);
            response = (UpdateResponse)ResponseDecoder.decode(updateOperation.getClass(), responseNode);
        } catch (Throwable e) {
            response = new UpdateResponse(1, updateOperation.getTransactionID().getValue());
            response.setThrowable(e);
        }
        return response;
    }

    @Override
    public DeleteResponse delete(Delete deleteOperation) throws MsgFrameException {
        DeleteResponse response;
        checkConnect(deleteOperation);
        startTransOfOper(deleteOperation);

        try {
            JsonNode node = RpcUtil.toJson(deleteOperation, sessionId);
            JsonNode responseNode = rpcClient.invoke("del", node, JsonNode.class);
            response = (DeleteResponse)ResponseDecoder.decode(deleteOperation.getClass(), responseNode);
        } catch (Throwable e) {
            response = new DeleteResponse(1, deleteOperation.getTransactionID().getValue());
            response.setThrowable(e);
        }
        return response;
    }

    @Override
    public AckResponse commit(Commit commit) throws NotConnectException {
        //checkConnect(commit);
        try {
            rpcClient.invokeNotification("commit", RpcUtil.commit2Json(commit, this.sessionId));
            return new AckResponse(0);
        } catch (Throwable e) {
            AckResponse resp = new AckResponse(1);
            resp.setThrowable(e);
            return resp;
        }
    }
}
