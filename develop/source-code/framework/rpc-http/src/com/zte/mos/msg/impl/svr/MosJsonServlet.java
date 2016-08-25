package com.zte.mos.msg.impl.svr;

import com.googlecode.jsonrpc4j.JsonRpcServer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SuppressWarnings("serial")
public class MosJsonServlet extends HttpServlet {
    private JsonRpcServer jsonRpcServer;

    @Override
    public void init() {
        jsonRpcServer = new JsonRpcServer(new MosJsonServiceImpl());

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        jsonRpcServer.handle(req, resp);
    }

}
