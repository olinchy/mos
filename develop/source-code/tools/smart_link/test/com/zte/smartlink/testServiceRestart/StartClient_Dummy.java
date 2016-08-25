package com.zte.smartlink.testServiceRestart;

import com.zte.mos.util.basic.Log4JRegister;
import com.zte.smartlink.UrlAddress;
import com.zte.smartlink.addressbook.client.AddressClientProcess;
import com.zte.smartlink.addressbook.client.LocalAddressBook;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import static com.zte.mos.util.Singleton.getInstance;

/**
 * Created by olinchy on 16-1-13.
 */
public class StartClient_Dummy
{
    public static void main(String[] args) throws Exception
    {
        Log4JRegister.init();
        LocateRegistry.createRegistry(4392);

        String url = AddressClientProcess.start(4392, "//127.0.0.1:4000/addressService");
        String nodeUrl;
        Naming.rebind(nodeUrl = url + "/app", new DummyApiImpl());
        getInstance(LocalAddressBook.class).addNode(
                "DUMMY", new UrlAddress(
                        nodeUrl));
    }
}
