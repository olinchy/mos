package com.zte.smartlink.testServiceRestart;

import com.zte.mos.exception.MOSException;
import com.zte.mos.util.Singleton;
import com.zte.smartlink.Address;
import com.zte.smartlink.addressbook.client.LocalAddressBook;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by olinchy on 16-1-13.
 */
public class PrinterAPI extends UnicastRemoteObject implements Remote
{
    protected PrinterAPI() throws RemoteException
    {
        new Timer("Timer-"+PrinterAPI.class.getSimpleName()).schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                try
                {
                    Address[] addresses = Singleton.getInstance(LocalAddressBook.class).getAddress("DUMMY");
                    if (addresses.length > 0)
                        ((DummyApi) Naming.lookup(addresses[0].toString())).print();
                }
                catch (MOSException e)
                {
                    e.printStackTrace();
                }
                catch (RemoteException e)
                {
                    e.printStackTrace();
                }
                catch (NotBoundException e)
                {
                    e.printStackTrace();
                }
                catch (MalformedURLException e)
                {
                    e.printStackTrace();
                }
            }
        }, 10, 5000);
    }
}
