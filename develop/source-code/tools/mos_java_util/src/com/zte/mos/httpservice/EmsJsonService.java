package com.zte.mos.httpservice;

import com.fasterxml.jackson.databind.JsonNode;
import com.zte.mos.exception.MOSException;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface EmsJsonService extends Remote
{
    JsonNode ping(String sessionId) throws RemoteException, MOSException;

    JsonNode get(String sessionId, String dn, JsonNode transId) throws RemoteException, MOSException;

    JsonNode getConfig(String sessionId, String dn, JsonNode transId) throws RemoteException, MOSException;

    JsonNode find(String sessionId, String exp, String dn, JsonNode transId)
            throws RemoteException, MOSException;

    JsonNode find(
            String sessionId, String exp, String dn, JsonNode transId, int startIndex,
            int count) throws MOSException, RemoteException;

    JsonNode ls(String sessionId, String dn, JsonNode transId) throws RemoteException, MOSException;

    JsonNode add(String sessionId, JsonNode moList, JsonNode transId)
            throws RemoteException, MOSException;

    JsonNode set(String sessionId, JsonNode moList, JsonNode transId)
            throws RemoteException, MOSException;

    JsonNode del(String sessionId, JsonNode dnList, JsonNode transId) throws RemoteException, MOSException;

    JsonNode commit(String sessionId, int transId) throws RemoteException, MOSException;

    JsonNode rollback(String sessionId, int transId) throws RemoteException, MOSException;

    JsonNode get_meta(
            String sessionId, String dn, String name, boolean isDnValid,
            JsonNode transId) throws RemoteException, MOSException;

    JsonNode login(String role, String userName, String pass, JsonNode server)
            throws RemoteException, MOSException;

    void logout(String sessionId) throws RemoteException, MOSException;

    JsonNode action(
            String sessionId, String dn, String actionName, JsonNode paras) throws RemoteException, MOSException;
}
