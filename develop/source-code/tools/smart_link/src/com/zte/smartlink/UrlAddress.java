package com.zte.smartlink;

import com.zte.mos.exception.MOSException;
import com.zte.mos.exception.MOSRMIException;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.MsgService;
import com.zte.mos.message.Result;
import com.zte.mos.util.basic.Logger;
import com.zte.smartlink.deliver.MsgMask;

import java.io.Serializable;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class UrlAddress implements Address, Comparable<UrlAddress>, Serializable
{
    private static final long serialVersionUID = 1L;
    private final String url;

    public UrlAddress(String url)
    {
        this.url = url;
    }

    public String getUrl()
    {
        return url;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        UrlAddress that = (UrlAddress) o;
        return this.getUrl().equals(that.getUrl());
    }

    @Override
    public int hashCode()
    {
        return 0;
    }

    @Override
    public int compareTo(UrlAddress other)
    {
        return this.getUrl().compareTo(other.getUrl());
    }

    @Override
    public String toString()
    {
        return url;
    }

    @Override
    public Result on(final MoMsg msg) throws MOSException
    {
        final Result ret;
        try
        {
            ret = MsgMask.method(msg).send(new Sending()
            {
                public Result send() throws MOSException, RemoteException
                {
                    final MsgService service;
                    try
                    {
                        Logger.logger(this.getClass()).debug(
                                "send " + msg.toString() + " to " + UrlAddress.this.toString());
                        service = (MsgService) Naming.lookup(UrlAddress.this.getUrl());
                        return Counter.count(new Executing()
                        {
                            public Result<?> execute() throws MOSException, RemoteException
                            {
                                return service.onMsg(msg);
                            }

                            public MoMsg getMsg()
                            {
                                return msg;
                            }
                        }, "sent");
                    }
                    catch (Throwable throwable)
                    {
                        throw new MOSRMIException(throwable, this.toString());
                    }
                }
            });
        }
        catch (RemoteException e)
        {
            throw new MOSRMIException(e, this.toString());
        }
        return ret;
    }

    @Override
    public Result on(ArrayList<? extends MoMsg> msg) throws MOSRMIException
    {
        Result ret;
        try
        {
            Logger.logger(this.getClass()).debug(
                    "send " + msg.toString() + " to " + this.toString());
            MsgService service = (MsgService) Naming.lookup(this.getUrl());
            ret = service.onMsg(msg);
        }
        catch (Throwable throwable)
        {
            throw new MOSRMIException(throwable, this.toString());
        }
        return ret;
    }
}
