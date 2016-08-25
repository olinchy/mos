package com.zte.mos.node;

import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.MsgService;
import com.zte.mos.message.Result;
import com.zte.mos.util.basic.Logger;
import com.zte.mos.util.msg.MoAckMsg;
import com.zte.scope.ems.EmsDomain;
import com.zte.smartlink.Counter;
import com.zte.smartlink.Executing;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by luoqingkai on 15-7-28.
 */
public class EmsMsgRouter extends UnicastRemoteObject implements MsgService
{
    private static final Logger log = logger(EmsMsgRouter.class);
    private EmsDomain ems;

    EmsMsgRouter(EmsDomain userEms) throws RemoteException
    {
        this.ems = userEms;
    }

    @Override
    public Result<?> onMsg(final MoMsg msg) throws RemoteException, MOSException
    {
        mergeSameDN(msg);
        ems.allocateTrasnID(msg);
        return Counter.count(new Executing()
        {
            @Override
            public Result<?> execute() throws MOSException, RemoteException
            {
                return ems.onMsg(msg);
            }

            @Override
            public MoMsg getMsg()
            {
                return msg;
            }
        }, "handled");
    }

    @Override
    public Result<?> onMsg(ArrayList<? extends MoMsg> msg) throws RemoteException, MOSException
    {
        return null;
    }

    private void mergeSameDN(MoMsg msg)
    {
        String[] dns = msg.dns();
        if (dns.length > 1)
        {
            Set<String> dnSet = new HashSet();
            for (String dn : dns)
            {
                dnSet.add(dn);
            }
            String[] newDNs = new String[dnSet.size()];
            int i = 0;
            for (String dn : dnSet)
            {
                newDNs[i] = dn;
                i++;
            }
            ((MoAckMsg) msg).setDn(newDNs);
        }
    }
}
