package com.zte.mos.app.rpcserver;

import com.googlecode.jsonrpc4j.JsonRpcServer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.rmi.RemoteException;

@SuppressWarnings("serial")
public class EmsJsonServlet extends HttpServlet
{
    private JsonRpcServer jsonRpcServer;

    @Override
    public void init()
    {
        try
        {
            jsonRpcServer = new JsonRpcServer(new EmsJsonServiceImpl());
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException
    {
        jsonRpcServer.handle(req, resp);
    }

}
