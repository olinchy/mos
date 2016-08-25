package com.zte.smartlink.addressbook.service;

import com.zte.smartlink.Address;
import com.zte.smartlink.addressbook.NodeAddress;
import com.zte.smartlink.addressbook.client.AddressClientService;

class ServiceSendDelNodeThread extends ServiceSendAddNodeThread
{
    public ServiceSendDelNodeThread(NodeAddress[] nodes, Address exclude)
    {
        super(nodes, exclude);
    }

    @Override
    public void sendAddrToClient(AddressClientService service) throws Exception
    {
        service.delNode(nodes);
    }
}