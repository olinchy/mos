package com.mos.lite.http_service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.zte.mos.exception.MOSException;
import com.zte.mos.httpservice.EmsJsonService;
import com.zte.mos.httpservice.SessionNotFoundException;
import com.zte.mos.message.Successful;
import com.zte.mos.util.tools.JsonUtil;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.UUID;

import static com.zte.mos.util.Singleton.getInstance;
import static com.zte.mos.util.tools.JsonUtil.newObjNode;

public class MosLiteJsonServiceImpl extends UnicastRemoteObject implements MosliteJsonService
{
    public MosLiteJsonServiceImpl() throws RemoteException
    {
    }

    @Override
    public JsonNode ping(@JsonRpcParam("sessionId") String sessionId) throws MOSException
    {
        ObjectNode node = newObjNode();
        EmsJsonService service = locateApp(sessionId);
        node.put("result", service == null ? 1 : 0);
        return node;
    }

    @Override
    public JsonNode ping(@JsonRpcParam("sessionId") String sessionId,
                         @JsonRpcParam("revision") int revision) throws MOSException
    {
        ObjectNode node = newObjNode();
        EmsJsonService service = locateApp(sessionId);
        node.put("result", service == null ? 1 : 0);
        return node;
    }

    @Override
    public JsonNode get(
            @JsonRpcParam("sessionId") String sessionId,
            @JsonRpcParam("dn") String dn, @JsonRpcParam("transId") JsonNode transId)
            throws MOSException, RemoteException
    {
        return locateApp(sessionId).get(sessionId, dn, transId);
    }

    @Override
    public JsonNode getConfig(String sessionId, String dn, JsonNode transId) throws RemoteException, MOSException
    {
        return locateApp(sessionId).getConfig(sessionId, dn, transId);
    }

    @Override
    public JsonNode find(
            @JsonRpcParam("sessionId") String sessionId,
            @JsonRpcParam("criteria") String exp, @JsonRpcParam("dn") String dn,
            @JsonRpcParam("transId") JsonNode transId)
            throws MOSException, RemoteException
    {
        return locateApp(sessionId).find(sessionId, exp, dn, transId);
    }

    @Override
    public JsonNode find(
            @JsonRpcParam("sessionId") String sessionId,
            @JsonRpcParam("criteria") String exp, @JsonRpcParam("dn") String dn,
            @JsonRpcParam("transId") JsonNode transId, @JsonRpcParam("startIndex") int startIndex,
            @JsonRpcParam("count") int count)
            throws MOSException, RemoteException
    {
        return locateApp(sessionId).find(sessionId, exp, dn, transId, startIndex, count);
    }

    @Override
    public JsonNode ls(
            @JsonRpcParam("sessionId") String sessionId,
            @JsonRpcParam("dn") String dn, @JsonRpcParam("transId") JsonNode transId)
            throws MOSException, RemoteException
    {
        return locateApp(sessionId).ls(sessionId, dn, transId);
    }

    @Override
    public JsonNode add(
            @JsonRpcParam("sessionId") String sessionId,
            @JsonRpcParam("moList") JsonNode moList,
            @JsonRpcParam("transId") JsonNode transId)
            throws MOSException, RemoteException
    {
        return locateApp(sessionId).add(sessionId, moList, transId);
    }

    @Override
    public JsonNode set(
            @JsonRpcParam("sessionId") String sessionId,
            @JsonRpcParam("moList") JsonNode moList,
            @JsonRpcParam("transId") JsonNode transId)
            throws MOSException, RemoteException
    {
        return locateApp(sessionId).set(sessionId, moList, transId);
    }

    @Override
    public JsonNode del(
            @JsonRpcParam("sessionId") String sessionId,
            @JsonRpcParam("dnList") JsonNode dnList,
            @JsonRpcParam("transId") JsonNode transId)
            throws MOSException, RemoteException// dnList:["x/y/z"]
    {
        return locateApp(sessionId).del(sessionId, dnList, transId);
    }

    @Override
    public JsonNode commit(
            @JsonRpcParam("sessionId") String sessionId,
            @JsonRpcParam("transId") int transId) throws MOSException, RemoteException
    {
        locateApp(sessionId).commit(sessionId, transId);
        return JsonUtil.toNode(new Successful());
    }

    @Override
    public JsonNode forceSyncToRemote(@JsonRpcParam("sessionId") String sessionId) throws RemoteException, MOSException
    {
        locateApp(sessionId).allSyncRequest(sessionId);
        return JsonUtil.toNode(new Successful());
    }

    @Override
    public JsonNode rollback(
            @JsonRpcParam("sessionId") String sessionId,
            @JsonRpcParam("transId") int transId) throws MOSException, RemoteException
    {
        locateApp(sessionId).rollback(sessionId, transId);
        return JsonUtil.toNode(new Successful());
    }

    @Override
    public JsonNode get_meta(
            @JsonRpcParam("sessionId") String sessionId,
            @JsonRpcParam("dn") String dn, @JsonRpcParam("name") String name,
            @JsonRpcParam("isDnValid") boolean isDnValid,
            @JsonRpcParam("transId") JsonNode transId) throws MOSException, RemoteException
    {
        return locateApp(sessionId).get_meta(sessionId, dn, name, isDnValid, transId);
    }

    @Override
    public JsonNode login(
            @JsonRpcParam("role") String role, @JsonRpcParam("username") String userName,
            @JsonRpcParam("passwd") String pass,
            @JsonRpcParam("server") JsonNode server)
            throws MOSException
    {
        String sessionId = UUID.randomUUID().toString();
        getInstance(LoginUserStore.class).add(sessionId, Role.valueOf(role).create());
        return loginRes(sessionId);
    }

    @Override
    public JsonNode action(
            @JsonRpcParam("sessionId") String sessionId,
            @JsonRpcParam("dn") String dn,
            @JsonRpcParam("action") String actionName,
            @JsonRpcParam("paras") JsonNode paras)
            throws MOSException, RemoteException
    {
        return locateApp(sessionId).action(sessionId, dn, actionName, paras);
    }

    @Override
    public void logout(@JsonRpcParam("sessionId") String sessionId) throws MOSException
    {
        User app = getInstance(LoginUserStore.class).get(sessionId);
        if (app != null)
        {
            getInstance(LoginUserStore.class).remove(sessionId);
        }
    }

    private ObjectNode loginRes(String forRemote)
    {
        ObjectNode node = newObjNode();
        node.put("result", 0);
        node.put("sessionId", forRemote);
        return node;
    }

    private MosliteJsonService locateApp(String sessionId) throws MOSException
    {
        MosliteJsonService service = getInstance(LoginUserStore.class).get(sessionId);
        if (service == null)
        {
            throw new SessionNotFoundException();
        }
        return service;
    }

    @Override
    public JsonNode allSyncRequest(@JsonRpcParam("sessionId") String sessionId) throws RemoteException, MOSException
    {
        return locateApp(sessionId).allSyncRequest(sessionId);
    }

    @Override
    public JsonNode incSyncRequest(
            @JsonRpcParam("sessionId") String sessionId,
            @JsonRpcParam("revision") int revision) throws RemoteException, MOSException
    {
        return locateApp(sessionId).incSyncRequest(sessionId, revision);
    }
}
