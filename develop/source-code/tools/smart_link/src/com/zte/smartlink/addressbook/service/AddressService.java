package com.zte.smartlink.addressbook.service;

import com.zte.smartlink.Address;
import com.zte.smartlink.addressbook.AddressBook;
import com.zte.smartlink.addressbook.NodeAddress;

import java.rmi.Remote;

public interface AddressService extends Remote
{
    NodeAddress[] clientRegister(Address clientAddress, AddressBook book)
            throws Exception;

    void addNode(Address clientAddress, NodeAddress... nodes)
            throws Exception;

    void delNode(Address clientAddress, NodeAddress... nodes)
            throws Exception;

    String getClientIp() throws Exception;
}
