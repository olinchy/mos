package com.zte.mos.msg.impl.svr;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Created by luoqingkai on 14-10-16.
 */
public interface MosJsonService {

//    JsonNode login(
//            String role,
//            String userName,
//            String pass,
//            JsonNode server)
//            throws MOSException;
//
//    void logout(String sessionId) throws MOSException;
//
//    JsonNode ping(String sessionId) throws MOSException;
//
//    JsonNode revisionRequest(String sessionId, int revision) throws MOSException;

    JsonNode allSyncRequest(String sessionId, boolean continueFlag, int sn, JsonNode node);

    //JsonNode incSyncRequest(String sessionId, boolean continueFlag, int sn, JsonNode node);

    void incSyncInd(String sessionId, JsonNode moOperList);
}
