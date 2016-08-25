package com.zte.mos.node.app.ft.modelswitch;

import com.zte.mos.app.switchmodel.service.ModelSwitchService;
import com.zte.mos.app.switchmodel.service.NeIdAllocator;
import com.zte.mos.exception.MOSException;
import com.zte.mos.util.basic.Logger;
import com.zte.smartlink.SmartLinkNode;

import java.rmi.Remote;
import java.rmi.RemoteException;
import static com.zte.mos.util.Singleton.*;

/**
 * Created by ccy on 5/25/16.
 */

public class ModelSwitchApp extends SmartLinkNode
{
    private static Logger log = Logger.logger(ModelSwitchApp.class);

    public static String NAME = "SIMU_MODEL_SWITCH_APP";

    @Override
    public void start(String[] args) throws MOSException
    {
        getInstance(NeIdAllocator.class);
    }

    @Override
    public Remote getService() throws MOSException, RemoteException
    {
        return new ModelSwitchService();
    }

    @Override
    public String getName()
    {
        return NAME;
    }

    @Override
    public void post() throws MOSException
    {
    }
}
