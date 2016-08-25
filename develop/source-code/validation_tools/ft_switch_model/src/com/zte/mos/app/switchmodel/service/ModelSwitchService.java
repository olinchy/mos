package com.zte.mos.app.switchmodel.service;

import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.MsgService;
import com.zte.mos.message.Result;
import com.zte.mos.message.Successful;
import com.zte.mos.util.msg.MoEvent;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import static com.zte.mos.app.switchmodel.service.ModelSwitchEventMonitor.notifyAllObservers;
import static com.zte.mos.app.switchmodel.service.ModelSwitchResult.setModelSwitchResult;
import static com.zte.mos.util.Singleton.*;

/**
 * Created by ccy on 5/25/16.
 */
public class ModelSwitchService extends UnicastRemoteObject implements MsgService
{
    public ModelSwitchService() throws RemoteException
    {

    }

    @Override
    public Result<?> onMsg(MoMsg msg) throws RemoteException, MOSException
    {
        if (msg instanceof MoEvent)
        {
            handleEvent((MoEvent)msg);
        }
        return new Successful<Object>();
    }

    private void handleEvent(MoEvent msg)
    {
        if (msg.dn().matches("/Ems/1/ModelSwitchEnd/1"))
        {
            if (String.valueOf(msg.getContent().get("result")).equalsIgnoreCase("success"))
            {
                setModelSwitchResult(true);
                notifyAllObservers("success");
            }
        }
    }

    @Override
    public Result<?> onMsg(ArrayList<? extends MoMsg> msg) throws RemoteException, MOSException
    {
        return null;
    }
}
