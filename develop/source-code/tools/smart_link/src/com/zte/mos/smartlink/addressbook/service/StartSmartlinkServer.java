package com.zte.mos.smartlink.addressbook.service;

import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.Daemon;
import com.zte.mos.util.basic.Logger;
import com.zte.smartlink.addressbook.service.AddressClientAliveTimer;
import com.zte.smartlink.addressbook.service.AddressServiceImpl;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.util.Properties;

import static com.zte.mos.util.basic.Logger.logger;

public class StartSmartlinkServer implements Daemon
{
    static Logger logger = logger(StartSmartlinkServer.class);
    private static AddressServiceImpl serviceHolderAgainstGC;

    public static void start(String port, String serurl)
            throws MOSException
    {
        logger.info("starting StartSmartlinkServer...");
        logger.info("binging port " + port + "...\n");
        try
        {
            LocateRegistry.createRegistry(Integer.parseInt(port));
            logger.info("binding service...");

            serviceHolderAgainstGC = new AddressServiceImpl();
            Naming.rebind(serurl, serviceHolderAgainstGC);
            logger.info("Address Service started on " + serurl + "!");

            //startBundles();

            AddressClientAliveTimer.start();
        }
        catch (Exception e)
        {
            logger.warn("start StartSmartlinkServer failed", e);
        }
    }

//    private static void startBundles() throws MOSException
//    {
//        PreLoadScanner scanner = new PreLoadScanner();
//        scanner.scan(Scan.getClasses("com.zte.mos.daemon", Object.class));
//    }

    @Override
    public void start(Properties properties, String... args) throws MOSException
    {
        String port = properties.getProperty("addrport");
        String serurl = properties.getProperty("addressserver");
        start(port, serurl);
    }

    @Override
    public int compareTo(Daemon o)
    {
        return -1;
    }
}
