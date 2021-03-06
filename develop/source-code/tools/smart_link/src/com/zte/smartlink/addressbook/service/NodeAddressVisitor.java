package com.zte.smartlink.addressbook.service;

import com.zte.mos.exception.MOSException;
import com.zte.mos.util.Visitor;
import com.zte.smartlink.addressbook.NodeAddress;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by olinchy on 16-1-8.
 */
public class NodeAddressVisitor implements Visitor<ClientAddress>
{
    private ArrayList<NodeAddress> nodeAddress = new ArrayList<NodeAddress>();

    public NodeAddress[] getNodeAddress()
    {
        return nodeAddress.toArray(new NodeAddress[nodeAddress.size()]);
    }

    @Override
    public void visit(ClientAddress clientAddress) throws MOSException
    {
        nodeAddress.addAll(Arrays.asList(clientAddress.addressBook.getNodeAddress()));
    }
}
