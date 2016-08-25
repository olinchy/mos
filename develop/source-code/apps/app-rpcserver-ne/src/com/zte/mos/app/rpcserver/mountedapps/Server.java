package com.zte.mos.app.rpcserver.mountedapps;

/**
 * Created by olinchy on 7/16/14 for MO_JAVA.
 */
public class Server
{
    private String url;
    private String sessionId;

    public Server(String url, String sessionId)
    {
        this.url = url;
        this.sessionId = sessionId;
    }

    public String getUrl()
    {
        return url;
    }

    public String getSessionId()
    {
        return sessionId;
    }

    public void setSessionId(String sessionId)
    {
        this.sessionId = sessionId;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }
}
