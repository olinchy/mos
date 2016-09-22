package com.mos.lite.http_service;

import com.zte.mos.util.tools.Prop;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.webapp.WebAppContext;

import java.io.File;
import java.net.URL;

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
        final URL warUrl = new File("ca").toURI().toURL();
        final String warUrlString = warUrl.toExternalForm();
        WebAppContext context = new WebAppContext(warUrlString, "");
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(MosLiteJsonServlet.class, "/mos");
        server.start();
    }

    public void stop() throws Exception
    {
        server.stop();
    }
}
