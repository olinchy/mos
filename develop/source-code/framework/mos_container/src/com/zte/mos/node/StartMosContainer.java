package com.zte.mos.node;

import com.zte.container.kernel.BundleContainer;
import com.zte.mos.domain.MosSettings;
import com.zte.mos.exception.MOSException;
import com.zte.mos.util.Singleton;
import com.zte.mos.util.basic.Logger;
import com.zte.smartlink.SmartLinkNode;

import java.rmi.Remote;

import static com.zte.mos.util.basic.Logger.logger;


public class StartMosContainer extends SmartLinkNode
{
    private static final Logger log = logger(StartMosContainer.class);
    private String url;
    private BundleContainer container;

    @Override
    public void start(String... args) throws MOSException
    {
        this.url = args[0];

        Singleton.getInstance(MosSettings.class).setProcessName("BUNDLE");
        container = new BundleContainer(url);
        try
        {
            container.start();
            log.debug(this.url + " start mos container success");
        }
        catch (Exception e)
        {
            log.debug(this.url + " fail to start mos container", e);
            throw new MOSException(e);
        }
    }

    @Override
    public Remote getService() throws MOSException
    {
        return container.getServicesHolder().getOperService().getRemote();
    }

    @Override
    public String getName()
    {
        return "BUNDLE";
    }
}
