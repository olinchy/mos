package com.zte.mos.node.app.rpcserver;

import com.zte.app.smartlink.rpcserver.CLITreeRegister;
import com.zte.mos.app.rpcserver.Starter;
import com.zte.mos.exception.MOSException;
import com.zte.mos.message.MsgService;
import com.zte.smartlink.SmartLinkNode;

/**
 * Created by olinchy on 6/4/14 for MO_JAVA.
 */
public class App_RPC_SERVER extends SmartLinkNode
{
    @Override
    public void start(String[] args) throws MOSException
    {
        new CLITreeRegister();
        try
        {
            new Starter();
        }
        catch (Exception e)
        {
            throw new MOSException(e);
        }
    }

    @Override
    public MsgService getService()
    {
        return null;
    }

    @Override
    public String getName()
    {
        return "RPC_Server";
    }

    @Override
    public String depends()
    {
        return "MOS";
    }



}
