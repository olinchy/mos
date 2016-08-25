package com.zte.mos.msg.framework.operation;

/**
 * Created by luoqingkai on 15-5-12.
 */
public class ConnectResponse extends AbstractResponse{
    private static String Invalid_Session_ID = "";

    private final String sessionId;

    public ConnectResponse(int result, String sessionId) {
        super(result);
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId == null ? Invalid_Session_ID : sessionId;
    }
}
