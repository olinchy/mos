package com.zte.smartlink.addressbook.client;

import com.zte.smartlink.addressbook.NodeAddress;

import java.rmi.Remote;

public interface AddressClientService extends Remote
{
    void addNode(NodeAddress[] nodeAddresses) throws
            Exception;

    void delNode(NodeAddress[] nodeAddresses) throws
            Exception;
}
