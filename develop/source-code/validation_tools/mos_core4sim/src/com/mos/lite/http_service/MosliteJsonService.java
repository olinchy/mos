package com.mos.lite.http_service;

import com.fasterxml.jackson.databind.JsonNode;
import com.zte.mos.exception.MOSException;
import com.zte.mos.httpservice.EmsJsonService;

import java.rmi.RemoteException;

/**
 * Created by olinchy on 15-12-28.
 */
public interface MosliteJsonService extends EmsJsonService
{
    //    {
//        "method":"allSyncRequest",
//            "params":{
//        "sessionId": "uuid"
//    },
//        "returns": {
//        "result" : 0,
//                "revision": 1,
//                "moList":[
//        {"moClass":"P2pRoute", "dn":"/P2pRoute/1","mo":{}}
//        ]
//    }
//    },
    JsonNode allSyncRequest(String sessionId) throws RemoteException, MOSException;
//    {
//        "method":"incSyncRequest",
//            "params":{
//        "sessionId": "uuid",
//                "revision": 1
//    },
//        "returns": {
//        "result" : 0,
//                "revision" : 1,
//                "moOpList":[
//        {"type":"add","dn":"aaaa", "moClass":"Board","mo":{}},
//        {"type":"del","dn":"aaaa"},
//        {"type":"set","dn":"aaaa","moClass":"Board", "mo":{}},
//        {"type":"replace","dn":"aaaa","moClass":"Board", "mo":{}}
//        ]
//    }
//    },

    JsonNode incSyncRequest(String sessionId, int revision) throws RemoteException, MOSException;

    JsonNode ping(String sessionId, int revision) throws RemoteException, MOSException;
    JsonNode forceSyncToRemote(String sessionId) throws RemoteException, MOSException;
}
