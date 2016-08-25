package com.zte.smartlink.addressbook.service;

import com.zte.smartlink.Address;
import com.zte.smartlink.UrlAddress;
import com.zte.smartlink.addressbook.client.AddressClientService;

import java.rmi.Naming;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import static com.zte.mos.util.Singleton.getInstance;
import static com.zte.mos.util.basic.Logger.logger;

public class ClientAliveTimerTask extends TimerTask
{
    private static int MAX_TRY_TIMES = 10;

    public ClientAliveTimerTask()
    {
    }

    private static ConcurrentHashMap<Address, Integer> map = new ConcurrentHashMap<Address, Integer>();

    @Override
    public void run()
    {

        Address[] clients = null;
        try
        {
            clients = getInstance(ServiceAddressBook.class).getClient();
        }
        catch (Exception e2)
        {
            e2.printStackTrace();
        }

        for (int loop = 0; loop < clients.length; loop++)
        {
            try
            {
                getInstance(AddressClientStore.class).setService(
                        clients[loop],
                        (AddressClientService) Naming
                                .lookup(((UrlAddress) clients[loop]).getUrl()));
                map.remove(clients[loop]);
            }

            catch (Exception e)
            {
                logger(this.getClass()).warn("cannot lookup " + ((UrlAddress) clients[loop]).getUrl());
                if (achievedMaxRetry(clients[loop]))
                {
                    logger(this.getClass()).info(
                            "max retry times achieved, clearing client " + clients[loop].toString());
                    clearClient(clients[loop]);
                }
                continue;
            }
        }
    }

    private boolean achievedMaxRetry(Address client)
    {
        Integer retriedTimes = map.get(client);
        if (retriedTimes == null)
        {
            map.put(client, 1);
        }
        else
        {
            map.put(client, ++retriedTimes);
            return retriedTimes >= MAX_TRY_TIMES;
        }
        return false;
    }

    private void clearClient(Address client)
    {
        try
        {
            getInstance(AddressClientStore.class).setService(
                    client, null);
            new ServiceSendDelNodeThread(getInstance(
                    ServiceAddressBook.class).getClientNodeAddress(
                    client), client).start();
            getInstance(ServiceAddressBook.class).delClientBook(
                    client);
            map.remove(client);
        }
        catch (Exception e1)
        {
            logger(this.getClass()).warn(e1.getMessage(), e1);
        }
    }
}
