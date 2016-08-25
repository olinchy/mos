package com.zte.mos.msg.impl.svr;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

import static com.zte.mos.util.basic.Logger.logger;

@SuppressWarnings("all")
public class Starter implements IServer{
    private Server jettyServer;

    public Starter() throws Exception {
        this.jettyServer = new Server(55555);
        ServletContextHandler context =
                new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        jettyServer.setHandler(context);
        context.addServlet(MosJsonServlet.class, "");
        logger(Starter.class).info("network service starting at:");
        jettyServer.start();
        logger(Starter.class).info("network service started at:");
    }

    public void stop() throws Exception {
        jettyServer.stop();
    }
}
