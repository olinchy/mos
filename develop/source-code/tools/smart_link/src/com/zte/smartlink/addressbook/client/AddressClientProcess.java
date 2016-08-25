package com.zte.smartlink.addressbook.client;

import com.zte.mos.util.basic.Logger;
import com.zte.smartlink.UrlAddress;
import com.zte.smartlink.addressbook.service.AddressService;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import static com.zte.mos.util.Singleton.getInstance;

public class AddressClientProcess
{
    public static String serverUrl;
    private static Logger logger = Logger.logger(AddressClientProcess.class);

    public static void bind(int portNo, String url) throws RemoteException,
            MalformedURLException
    {
        logger.info("starting AddressClientProcess...");
        logger.info("binging port " + portNo + "...\n");
        LocateRegistry.createRegistry(portNo);
        logger.info("binding Client on " + url);
        Naming.rebind(url, new AddressClientServiceImpl());

        logger.info("Address Client started!");
    }

    public static String start(int port, String serverAddress)
            throws Exception
    {
        serverUrl = serverAddress;

        AddressService service = (AddressService) Naming.lookup(serverUrl);
        String ip = service.getClientIp();

        String url = "//" + ip + ":" + String.valueOf(port) + "/client";
        getInstance(ClientAddr.class).setAddr(new UrlAddress(url));

        logger.info("binding Client on " + url);
        Naming.rebind(url, new AddressClientServiceImpl());

        logger.info("Address Client started!");
        AddressServiceAliveTimer.start();
        AddressServiceSyncTimer.start();

        return url;
    }
}
