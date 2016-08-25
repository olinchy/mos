package com.zte.mos.test;

import com.zte.mos.app.necache.Cache;
import com.zte.mos.app.necache.CacheServiceHolder;
import com.zte.mos.domain.DN;
import com.zte.mos.exception.MOSException;
import com.zte.mos.util.Visitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import static com.zte.mos.util.Singleton.getInstance;
import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by zw on 15-12-1.
 */
public class SimToAppBridge
{
    private static final void log(String info){
        logger(SimToAppBridge.class).info("PM FT::SimToAppBridge " + info);
    }

    private static final void log(String info, Throwable e){
        logger(SimToAppBridge.class).error("PM FT::SimToAppBridge " + info, e);
    }

    private static final void write(ObjectOutputStream out, Object msg) throws IOException {
        if(msg == null){
            log("msg is " + msg + ". client don't send.");
        } else {
            log("client send: " + msg);
            out.writeObject(msg);
        }
        out.flush();
    }

    private static final Object getCache(Object cache, String cmd){
        Object obj = null;
        if(cache == null){
            obj = new com.zte.mos.test.Cache();
            log("get cache is null. cmd: " + cmd);
        } else {
            obj = new com.zte.mos.test.Cache((Cache)cache);
        }
        return obj;
    }

    static {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ServerSocket svr = null;
                try {
                    svr = new ServerSocket(8085);
                    log("start: " + svr);
                    while (true) {
                        Socket client = null;
                        try {
                            client = svr.accept();
                            log("accept client: " + client);
                            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                            ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
                            while (true){
                                String cmd = in.readLine();
                                log("client receive: " + cmd);
                                if("over".equals(cmd)){
                                    client.close();
                                    log("client close.");
                                    break;
                                } else {
                                    String[] cmds = cmd.split(":");
                                    Object obj = null;
                                    if ("Cache->get(String)".equals(cmds[0])) {
                                        obj = getCache(getInstance(CacheServiceHolder.class).getCacheService().get(cmds[1]), cmds[1]);
                                    } else if ("Cache->get(DN)".equals(cmds[0])) {
                                        obj = getCache(getInstance(CacheServiceHolder.class).getCacheService().get(new DN(cmds[1])), cmds[1]);
                                    } else if ("Cache->walk()".equals(cmds[0])) {
                                        final ArrayList<com.zte.mos.test.Cache> res = new ArrayList<com.zte.mos.test.Cache>();
                                        getInstance(CacheServiceHolder.class).getCacheService().walk(new Visitor<Cache>() {
                                            @Override
                                            public void visit(Cache cache) throws MOSException { res.add(new com.zte.mos.test.Cache(cache)); }
                                        });
                                        obj = res;
                                    }
                                    write(out, obj);
                                }
                            }
                        } catch (Exception ex) {
                            log(client + " error: " + ex.getMessage(), ex);
                        } finally {
                            if(client != null) try {
                                client.close();
                                log(client + " close.");
                            } catch (IOException e2) {log(client + " close error: " + e2.getMessage(), e2);}
                        }
                    }
                } catch (Exception e) {
                    log(svr + "error: " + e.getMessage(), e);
                } finally {
                    if(svr != null) try {
                        svr.close();
                        log(svr + " close.");
                    } catch (IOException e2) {log(svr + " close error: " + e2.getMessage(), e2);}
                }
            }
        }).start();
    }
}
