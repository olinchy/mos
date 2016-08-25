package com.zte.mos.app.rpcserver;

import com.zte.mos.util.tools.Prop;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.server.ssl.SslSocketConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

public class Starter
{
    Server server;

    public Starter() throws Exception
    {
        startup();
    }

    public void startup() throws Exception
    {
        int port = Integer.parseInt(Prop.get("httpport"));
        String protocol = Prop.get("protocol");

        if (protocol != null && protocol.equalsIgnoreCase("https"))
        {
            server = new Server();//;
            SslSocketConnector ssl_connector = new SslSocketConnector();
            ssl_connector.setPort(port);
            ssl_connector.setAcceptors(2);
            ssl_connector.setLowResourcesMaxIdleTime(5000);
            SslContextFactory cf = ssl_connector.getSslContextFactory();
            cf.setKeyStorePath("./mos.keystore");
            cf.setCertAlias("mos");
            cf.setKeyStorePassword("123456");
            cf.setKeyManagerPassword("123456");
            cf.setTrustAll(true);
            server.addConnector(ssl_connector);
        }
        else
        {
            server = new Server();
            SelectChannelConnector connector = new SelectChannelConnector();
            connector.setLowResourcesConnections(5000);
            connector.setLowResourcesMaxIdleTime(5000);
            connector.setAcceptors(3);
            connector.setPort(port);
            server.addConnector(connector);
        }

        server.setThreadPool(new QueuedThreadPool(50));
        ServletContextHandler context =
                new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
        context.addServlet(EmsJsonServlet.class, "");
        server.start();
    }

    public void stop() throws Exception
    {
        server.stop();
    }
}
