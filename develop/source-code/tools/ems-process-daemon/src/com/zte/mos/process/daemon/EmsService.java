package com.zte.mos.process.daemon;


import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.Daemon;
import com.zte.mos.util.DaemonScanner;
import com.zte.mos.util.Scan;
import com.zte.mos.util.scaner.ConverterTempStorage;
import com.zte.ums.uep.api.BaseMBeanSupport;
import com.zte.ums.uep.api.ServiceAccess;
import com.zte.ums.uep.api.psl.systemsupport.SystemStateEvent;
import com.zte.ums.uep.api.psl.systemsupport.SystemStateListener;
import com.zte.ums.uep.psl.console.ProcessControlService;
import sun.rmi.transport.proxy.RMIDirectSocketFactory;

import java.io.IOException;
import java.net.Socket;
import java.rmi.server.RMISocketFactory;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Set;

import static com.zte.mos.util.Singleton.getInstance;
import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by zhangbin10086509 on 15-5-8.
 */
public class EmsService extends BaseMBeanSupport implements EmsServiceMBean
{

    protected void startService() throws Exception
    {
        ServiceAccess.getSystemSupportService().addSystemStateListener(new SystemStateListener()
        {
            public void stateChanged(SystemStateEvent e)
            {
                if (e.getActionCommand() == SystemStateEvent.START_ACTION)
                {
                    try
                    {
                        getInstance(ConverterTempStorage.class).registerAgain();
                    }
                    catch (MOSException e1)
                    {
                        logger(EmsService.class).warn("reg converter to convertUtils failed!", e1);
                    }
                }
            }
        });


        String ip = ProcessControlService.getCurrentIp();
        logger(EmsService.class).info("serverip is " + ip);

        setupRMINaming();

        setupSocketFactory(ip);

        Properties prop = PropertiesLoader.load();
        prop.setProperty("serverIP", ip);


        start(prop);

        logger(EmsService.class).info("have some fun!");
    }

    private void setupRMINaming()
    {
        System.setProperty("java.rmi.server.hostname", "ems-server");
    }

    private void start(Properties prop)
    {
        Set<Class<Daemon>> set = Scan.getClasses(prop.getProperty("searching_package"), Daemon.class);

        LinkedList<String> lst = new LinkedList<String>();
        if (prop.getProperty("start_args") != null)
            lst.addAll(Arrays.asList(prop.getProperty("start_args").split("\\|")));

        try
        {
            new DaemonScanner(prop, lst).scan(set);
        }
        catch (Throwable throwable)
        {
            logger(EmsService.class).warn("", throwable);
        }
    }

    protected void stopService() throws Exception
    {
    }

    public static void setupSocketFactory(final String ip) throws IOException
    {
        if (RMISocketFactory.getSocketFactory() != null)
        {
            return;
        }
        RMISocketFactory.setSocketFactory(new RMIDirectSocketFactory()
        {
            @Override
            public Socket createSocket(String s, int i) throws IOException
            {
                if (s.equals("ems-server"))
                {
                    return new Socket(ip, i);
                }
                return new Socket(s, i);
            }
        });
    }

}
