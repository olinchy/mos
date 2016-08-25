package com.zte.smartlink.addressbook.service;

import com.zte.mos.util.basic.Logger;
import com.zte.smartlink.Address;
import com.zte.smartlink.addressbook.AddressBook;
import com.zte.smartlink.addressbook.NodeAddress;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;

import static com.zte.mos.util.Singleton.getInstance;

public class AddressServiceImpl extends UnicastRemoteObject implements
        AddressService
{
    private static final long serialVersionUID = 1L;

    public AddressServiceImpl() throws RemoteException
    {
        super();
    }

    @Override
    public NodeAddress[] clientRegister(Address clientAddress, AddressBook book)
            throws Exception
    {
        Logger.logger(this.getClass()).info("clientRegister: " + clientAddress + ", " + book);

        NodeAddress[] addresses = getInstance(ServiceAddressBook.class)
                .addClientBook(clientAddress, book);

        new ServiceSendAddNodeThread(book.getNodeAddress(), clientAddress)
                .start();
        return addresses;
    }

    @Override
    public void addNode(Address clientAddress, NodeAddress... nodes)
            throws Exception
    {
        Logger.logger(this.getClass()).info("addNode: " + clientAddress + ", " + Arrays.asList(nodes));

        getInstance(ServiceAddressBook.class).addNode(clientAddress, nodes);

        new ServiceSendAddNodeThread(nodes, clientAddress).start();
    }

    @Override
    public void delNode(Address clientAddress, NodeAddress... nodes)
            throws Exception
    {
        Logger.logger(this.getClass()).info("delNode: " + clientAddress + ", " + nodes);

        getInstance(ServiceAddressBook.class).delNode(clientAddress, nodes);

        new ServiceSendDelNodeThread(nodes, clientAddress).start();
    }

    @Override
    public String getClientIp() throws Exception
    {
        return getClientHost();
    }

}
