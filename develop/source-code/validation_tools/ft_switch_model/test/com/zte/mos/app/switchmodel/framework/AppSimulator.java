package com.zte.mos.app.switchmodel.framework;

import com.zte.smartlink.SmartLinkNode;
import com.zte.smartlink.UrlAddress;
import com.zte.smartlink.addressbook.client.AddressClientProcess;
import com.zte.smartlink.addressbook.client.LocalAddressBook;
import sun.rmi.transport.proxy.RMIDirectSocketFactory;

import java.io.IOException;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.RMISocketFactory;

import static com.zte.mos.util.Singleton.getInstance;

public class AppSimulator
{

    private final Class<? extends SmartLinkNode> nodeClass;

    public AppSimulator(Class<? extends SmartLinkNode> clazz )
    {
        this.nodeClass = clazz;
    }

    public void start()
    {
        try
        {
            setRmiFactory();
            int port = decidePort("8000-9000");
            String clientUrl = AddressClientProcess.start(port, "//127.0.0.1:4000/addressService");
            SmartLinkNode node = nodeClass.newInstance();
            String nodeUrl = clientUrl + "/" + node.getName();
            Naming.rebind(nodeUrl, node.getService());
            getInstance(LocalAddressBook.class).addNode(node.getName(), new UrlAddress(nodeUrl));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


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

    private static void setRmiFactory()
    {
        try
        {
            RMISocketFactory.setSocketFactory(
                    new RMIDirectSocketFactory()
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
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}