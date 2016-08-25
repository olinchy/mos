package com.zte.smartlink.addressbook.service;

import com.zte.smartlink.Address;
import com.zte.smartlink.addressbook.client.AddressClientService;

import java.net.MalformedURLException;
import java.rmi.*;
import java.util.HashMap;

public class AddressClientStore
{
    private final HashMap<Address, AddressClientService> services = new HashMap<Address, AddressClientService>();

    private AddressClientStore()
    {
    }

    public synchronized void setService(Address address, AddressClientService service)
    {
        services.put(address, service);
    }

    public synchronized AddressClientService getService(Address address) throws ServiceNotAvaliableException, RemoteException, NotBoundException, MalformedURLException
    {
        AddressClientService client = services.get(address);
        if (client == null)
        {
            client = (AddressClientService) Naming.lookup(address.toString());
            this.setService(address, client);
        }
        return client;
    }
}
