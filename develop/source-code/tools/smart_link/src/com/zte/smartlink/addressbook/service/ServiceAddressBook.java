package com.zte.smartlink.addressbook.service;

import com.odb.database.BerkeleyDB;
import com.zte.mos.exception.BerkeleyDBException;
import com.zte.mos.exception.MOSException;
import com.zte.smartlink.Address;
import com.zte.smartlink.addressbook.AddressBook;
import com.zte.smartlink.addressbook.NodeAddress;

public class ServiceAddressBook
{
    BerkeleyDB<Address, ClientAddress> books = new BerkeleyDB<Address, ClientAddress>(new AddressCacheIndex());

    private ServiceAddressBook()
    {
    }

    public NodeAddress[] addClientBook(Address clientAddress, AddressBook book) throws MOSException
    {
        NodeAddress[] nodeAddr = getAllClientNodeAddress();

        books.put(new ClientAddress(clientAddress, book));

        return nodeAddr;
    }

    public void delClientBook(Address clientAddress) throws BerkeleyDBException
    {
        books.remove(clientAddress);
    }

    public void addNode(Address clientAddress, NodeAddress[] nodes) throws MOSException
    {
        ClientAddress cache = books.get(clientAddress);
        if (cache == null)
        {
            cache = new ClientAddress(clientAddress, new AddressBook());
        }
        cache.addressBook.addAddress(nodes);
        books.put(cache);
    }

    public void delNode(Address clientAddress, NodeAddress[] nodes) throws MOSException
    {
        ClientAddress cache = books.get(clientAddress);
        if (cache == null)
        {
            return;
        }
        else
        {
            cache.addressBook.delAddress(nodes);
            books.put(cache);
        }
    }

    public Address[] getClient() throws MOSException
    {

        ClientAddressVisitor visitor;
        books.all(new AddressCacheWalker(visitor = new ClientAddressVisitor()));

        return visitor.getAllAddress();
    }

    public NodeAddress[] getClientNodeAddress(Address clientAddress) throws BerkeleyDBException
    {
        ClientAddress cache = books.get(clientAddress);
        if (cache != null)
        {
            return cache.addressBook.getNodeAddress();
        }
        return null;
    }

    public void reset()
    {
        books.clearAll();
    }

    private NodeAddress[] getAllClientNodeAddress() throws MOSException
    {
        NodeAddressVisitor visitor;
        books.all(new AddressCacheWalker(visitor = new NodeAddressVisitor()));

        return visitor.getNodeAddress();
    }
}
