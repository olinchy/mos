package com.zte.smartlink;

import com.zte.mos.exception.MOSException;
import com.zte.mos.util.Scan;
import com.zte.mos.util.basic.Logger;
import com.zte.mos.util.scaner.PreLoadScanner;
import com.zte.smartlink.addressbook.client.AddressClientProcess;
import com.zte.smartlink.addressbook.client.LocalAddressBook;
import com.zte.smartlink.addressbook.service.AddressService;

import java.io.File;
import java.io.FileInputStream;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.*;

import static com.zte.mos.util.Singleton.getInstance;
import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by olinchy on 15-5-12.
 */
public class SmartLinkNodeStarter
{
    static Logger logger = logger(SmartLinkNodeStarter.class);
    static ArrayList<Remote> interfaces = new ArrayList<Remote>();

    private static int decidePort(String ports) throws Exception
    {
        String[] portScope = ports.split("-");
        for (
                int port = Integer.parseInt(portScope[0]);
                port < Integer.parseInt(portScope[1]); port++)
        {
            try
            {
                LocateRegistry.createRegistry(port);
                return port;
            }
            catch (RemoteException e)
            {
                continue;
            }
        }
        throw new Exception("cannot find available port");
    }

    public static void start(
            String serverIP, Properties pop,
            String... args) throws MOSException
    {
        try
        {
            if (pop == null)
            {
                pop = new Properties();
                pop.load(new FileInputStream(new File("conf.prop")));
            }
            if (serverIP == null)
            {
                serverIP = "127.0.0.1";
            }

            int clientPort = decidePort(pop.getProperty("ports"));

            String serverUrl = String.format("//%1$s:4000/addressService", serverIP);

            AddressService service = (AddressService) Naming.lookup(serverUrl);
            String ip = service.getClientIp();

            System.setProperty("java.rmi.server.hostname", ip);
            System.setProperty("java.net.preferIPv4Stack", "true");

            logger(SmartLinkNodeStarter.class).info(
                    "ports = " + clientPort + ", serverUrl = " + serverUrl);
            scanSmartLinks();
            startUnder(AddressClientProcess.start(clientPort, serverUrl), args);
        }
        catch (Exception e)
        {
            logger(SmartLinkNodeStarter.class).warn("start smartlink nodes failed!", e);
        }
    }

    private static void scanSmartLinks() throws MOSException
    {
        new PreLoadScanner().scan(Scan.getClasses("com.zte.app.smartlink"));
    }

    private static void startUnder(final String url, final String... args) throws MOSException
    {
        Set<Class<SmartLinkNode>> set = Scan.getClasses("com.zte.mos.node", SmartLinkNode.class);
        final LinkedList<SmartLinkNode> lstNode = new LinkedList<SmartLinkNode>();

        DependenceList.sort(lstNode, set);

        startNodes(url, lstNode, args);

        for (SmartLinkNode node : lstNode)
        {
            try
            {
                node.post();
            }
            catch (Throwable e)
            {
                logger.warn("", e);
            }
        }
    }

    private static void startNodes(String url, List<SmartLinkNode> lstNode, String[] args)
    {
        for (SmartLinkNode node : lstNode)
        {
            try
            {
                startNode(url, args, node);
            }
            catch (Throwable e)
            {
                logger.warn("start node " + node.getName() + " on " + url + " failed!", e);
            }
        }
    }

    private static void startNode(String url, String[] args, SmartLinkNode node) throws Exception
    {
        ArrayList<String> strings = getParas((Class<SmartLinkNode>) node.getClass(), args);
        logger.info("starting node " + node.getName());

        String nodeUrl = url + "/" + node.getName();
        strings.add(nodeUrl);
        node.start(strings.toArray(new String[strings.size()]));

        Remote remote = node.getService();
        if (remote != null)
        {
            Naming.rebind(nodeUrl, remote);
            getInstance(LocalAddressBook.class).addNode(
                    node.getName(), new UrlAddress(
                            nodeUrl));

            // add static reference to every node
            interfaces.add(remote);
            logger.info("bind node " + node.getName() + " on " + nodeUrl);
        }

        logger.info("node " + node.getName() + " started!");
    }

    private static ArrayList<String> getParas(Class<SmartLinkNode> clazz, String[] args)
    {
        ArrayList<String> strings = new ArrayList<String>();
        if (args != null && args.length != 0)
        {
            for (String arg : args)
            {
                String[] split = arg.split(":");
                String key = split[0];
                String value = split[1];
                if (key.equalsIgnoreCase(clazz.getSimpleName()))
                {
                    strings.add(value);
                }
            }
        }
        return strings;
    }
}
