package com.zte.smartlink.addressbook.service;

import com.zte.smartlink.*;
import com.zte.smartlink.addressbook.*;
import com.zte.smartlink.addressbook.client.AddressClientService;

import java.util.Arrays;

import static com.zte.mos.util.Singleton.getInstance;
import static com.zte.mos.util.basic.Logger.logger;

public class ServiceSendAddNodeThread extends SendNodeThread
{
    protected final Address exclude;

    public ServiceSendAddNodeThread(NodeAddress[] nodes, Address exclude)
    {
        super(nodes);
        this.exclude = exclude;
    }

    @Override
    public void send() throws Exception
    {
        Address[] clients = getInstance(ServiceAddressBook.class).getClient();

        for (int loop = 0; loop < clients.length; loop++)
        {

            if (clients[loop].equals(exclude))
                continue;

            try
            {
                AddressClientService service = getInstance(
                        AddressClientStore.class).getService(clients[loop]);
                sendAddrToClient(service);
                logger(this.getClass()).info(
                        "send address " + Arrays.asList(nodes).toString() + " to " + clients[loop].toString());
            }
            catch (Exception e)
            {
                logger(this.getClass()).warn(String.format("can't get ClientService clients %s...",
                        ((UrlAddress) clients[loop]).getUrl()), e);
                continue;
            }
        }
    }

    public void sendAddrToClient(AddressClientService service) throws Exception
    {
        service.addNode(nodes);
    }
}
