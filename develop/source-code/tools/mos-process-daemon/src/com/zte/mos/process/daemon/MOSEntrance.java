package com.zte.mos.process.daemon;

import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.Daemon;
import com.zte.mos.sim.preload.ScanPreloads;
import com.zte.mos.util.DaemonScanner;
import com.zte.mos.util.Scan;
import com.zte.mos.util.tools.Prop;
import org.apache.log4j.PropertyConfigurator;
import sun.rmi.transport.proxy.RMIDirectSocketFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.rmi.server.RMISocketFactory;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Set;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by olinchy on 2/12/15 for mosjava.
 */
public class MOSEntrance
{
    public static void main(String[] args)
            throws MOSException
    {
        try
        {
            startSimToAppBridgeForFT();

            ScanPreloads.load();

            setupRMI();

            Properties pop = new Properties();
            pop.load(new FileInputStream(new File("log4j.properties")));
            PropertyConfigurator.configure(pop);

            Set<Class<Daemon>> set = Scan.getClasses(args[0], Daemon.class);

            LinkedList<String> lst = new LinkedList<String>();
            lst.addAll(Arrays.asList(args));
            lst.remove(0);

            new DaemonScanner(Prop.getProp(), lst).scan(set);

            logger(MOSEntrance.class).info("have some fun!");
        }
        catch (IOException e)
        {
            logger(MOSEntrance.class).warn("start failed!", e);
        }
    }

    private static void setupRMI() throws IOException
    {
        System.setProperty("java.rmi.server.hostname", "mos-server");

        RMISocketFactory.setSocketFactory(new RMIDirectSocketFactory()
        {
            @Override
            public Socket createSocket(String s, int i) throws IOException
            {
                if (s.equals("mos-server"))
                {
                    return new Socket("127.0.0.1", i);
                }
                return new Socket(s, i);
            }
        });
    }

    private static void startSimToAppBridgeForFT()
    {
        try
        {
            Class.forName("com.zte.mos.test.SimToAppBridge");
        }
        catch (Throwable e)
        {
            logger(MOSEntrance.class).warn("for FT. Ignore. error: " + e.getMessage(), e);
        }
    }
}
