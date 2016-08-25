package com.zte.smartlink.addressbook.service;

import com.zte.mos.exception.MOSException;
import com.zte.mos.util.Visitor;
import com.zte.smartlink.Address;

import java.util.ArrayList;

/**
 * Created by olinchy on 16-1-8.
 */
public class ClientAddressVisitor implements Visitor<ClientAddress>
{
    private ArrayList<Address> allAddress = new ArrayList<Address>();

    @Override
    public void visit(ClientAddress clientAddress) throws MOSException
    {
        allAddress.add(clientAddress.address);
    }

    public Address[] getAllAddress()
    {
        return allAddress.toArray(new Address[allAddress.size()]);
    }
}
