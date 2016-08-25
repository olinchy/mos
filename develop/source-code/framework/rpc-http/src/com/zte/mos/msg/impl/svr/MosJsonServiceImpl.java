package com.zte.mos.msg.impl.svr;

import com.fasterxml.jackson.databind.JsonNode;
import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.zte.mos.msg.framework.svr.ReverseListener;
import com.zte.mos.msg.framework.svr.RpcListener;
import com.zte.mos.msg.framework.svr.RpcSvrRegister;
import com.zte.mos.msg.impl.rpc.OldRpcUtil;
import com.zte.mos.msg.impl.rpc.RpcSession;
import com.zte.mos.msg.impl.rpc.RpcSessionService;
import com.zte.mos.util.basic.Logger;
import com.zte.mos.util.tools.JsonUtil;

import static com.zte.mos.util.basic.Logger.logger;

public class MosJsonServiceImpl implements MosJsonService {
    private static Logger log = logger(MosJsonServiceImpl.class);

    @Override
    public JsonNode allSyncRequest
            (@JsonRpcParam("sessionId") String sessionId,
             @JsonRpcParam("continueFlag") boolean isContinue,
             @JsonRpcParam("SN") int sn,
             @JsonRpcParam("moList") JsonNode node) {
        log.info("rec:all sync data ");
        RpcSession session = RpcSessionService.sv.getSession(sessionId);
        if (session == null){
            return JsonUtil.newObjNode();
        }

        ReverseListener lsnr = RpcSvrRegister.getReverseListener();
        lsnr.process(session.getMyAddress(), node, sn, isContinue);
        return OldRpcUtil.toJson(sn, 0, false);
    }

    public void incSyncInd(@JsonRpcParam("sessionId") String sessionId,
                           @JsonRpcParam("moOperList") JsonNode moOperList) {
        log.info("rec:ne config change report:" + moOperList);
        RpcListener listener = RpcSvrRegister.getListener();
        if (listener != null) {
            RpcSession session = RpcSessionService.sv.getSession(sessionId);

            listener.process(session.getMyAddress(), moOperList);
        }
    }

}
