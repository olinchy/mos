package com.zte.smartlink.addressbook.client;

import com.zte.smartlink.addressbook.NodeAddress;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import static com.zte.mos.util.Singleton.getInstance;

public class AddressClientServiceImpl extends UnicastRemoteObject implements
        AddressClientService
{
    private static final long serialVersionUID = 1L;

    protected AddressClientServiceImpl() throws RemoteException
    {
        super();
    }

    @Override
    public void addNode(NodeAddress[] nodeAddresses) throws Exception
    {
        getInstance(LocalAddressBook.class).addOtherNode(nodeAddresses);
    }

    @Override
    public void delNode(NodeAddress[] nodeAddresses) throws Exception
    {
        getInstance(LocalAddressBook.class).delOtherNode(nodeAddresses);
    }

}
