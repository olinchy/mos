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
                new WebAppContext("./", "");
        context.setContextPath("/");
        server.setHandler(context);
        /*
        For historical reason, our sync rpc client and async rpc client use
        different url when posting http request to ne, where
        async url: http://ip/goform/emsMsg/ (login, ping...)
        sync url: http://ip/goform/emsMsg (sync, get...)
        Thus, when launching rpc server of sim ne, we add two same servlets
        to context, only path differs...
         */
        String name = Prop.get("export_name");
        if (name != null)
        {
            context.addServlet(MosLiteJsonServlet.class, name);
            context.addServlet(MosLiteJsonServlet.class, name + "/");
        }
        else
        {
            context.addServlet(MosLiteJsonServlet.class, "");
        }
        server.start();
    }

    public void stop() throws Exception
    {
        server.stop();
    }
}
