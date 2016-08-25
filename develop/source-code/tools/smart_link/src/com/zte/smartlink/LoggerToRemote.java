package com.zte.smartlink;

import com.zte.mos.logging_service.Log;
import com.zte.mos.logging_service.LoggingService;
import com.zte.mos.util.tools.Prop;

import java.rmi.Naming;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by olinchy on 16-7-20.
 */
public class LoggerToRemote
{
    static LoggingService service = null;
    static Thread thread = new Thread()
    {
        @Override
        public void run()
        {
            while (true)
            {
                if (usingLocal)
                {
                    locateService();
                }
                try
                {
                    sleep(100000);
                }
                catch (InterruptedException ignored)
                {
                }
            }
        }
    };
    private static boolean usingLocal = true;

    static
    {
        thread.start();
    }

    private static void locateService()
    {
        String url;
        if ((url = Prop.get("loggingservice")) != null)
        {
            try
            {
                service = (LoggingService) Naming.lookup(url);
                usingLocal = false;
            }
            catch (Exception ignored)
            {
                service = null;
            }
        }
        if (service == null)
        {
            usingLocal = true;
            localService();
        }
    }

    public static void log(Log log)
    {
        try
        {
            service.log(log);
        }
        catch (Throwable e)
        {
            localService();
        }
    }

    private static void localService()
    {
        service = new LoggingService()
        {
            @Override
            public void log(Log log)
            {
                logger(LoggerToRemote.class).debug("debugging in massive<Message>, " + log.toString());
            }
        };
    }
}
