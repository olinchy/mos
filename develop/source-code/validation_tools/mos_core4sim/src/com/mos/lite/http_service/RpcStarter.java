package com.mos.lite.http_service;

import com.zte.mos.util.tools.Prop;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.webapp.WebAppContext;

public class RpcStarter
{
    Server server;

    public RpcStarter() throws Exception
    {
        startup();
    }

    public void startup() throws Exception
    {
        server = new Server(Integer.parseInt(Prop.get("httpport")));
        WebAppContext context =
                new WebAppContext("./ca", "");
        context.setContextPath("/");
        server.setHandler(context);
        server.start();
    }

    public void stop() throws Exception
    {
        server.stop();
    }
}
