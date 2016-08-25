package com.zte.container.ext.switchmodel;

import com.zte.mos.container.NeDomain;
import com.zte.mos.domain.ImagedSystem;
import com.zte.mos.service.BundleService;
import com.zte.mos.service.MOS;
import com.zte.mos.service.ReferenceDBInBerkeley;

/**
 * Created by ccy on 5/5/16.
 */
public class ModelSwitchContext
{
    public ModelSwitchContext(String newModelName, String newModelVersion, BundleService service, NeDomain ne)
    {
        this.newModelName = newModelName;
        this.newModelVersion = newModelVersion;
        this.localBundle = service;
        this.localNe = ne;
        getReferenceDB();
    }

    private final NeDomain localNe;
    private final String newModelName;
    private final String newModelVersion;
    private ReferenceDBInBerkeley refDb = null;


    private final BundleService localBundle;

    public String getNewModelName()
    {
        return newModelName;
    }

    public String getNewModelVersion()
    {
        return newModelVersion;
    }

    public String getIpAddress()
    {
        return localNe.getIpAddress();
    }

    public String getID()
    {
        return localNe.getID();
    }

    public MOS getMos()
    {
        return localNe.getMos();
    }

    public String getNeType()
    {
        return this.localNe.getNeType();
    }

    public NeDomain getLocalNe()
    {
        return this.localNe;
    }

    public BundleService getLocalBundle()
    {
        return localBundle;
    }

    public String getOldModelName()
    {
        ImagedSystem old = (ImagedSystem)getMos();
        return old.getModelMETA().modelHead.modelName();
    }

    public String getOldModelVersion()
    {
        ImagedSystem old = (ImagedSystem)getMos();
        return old.getModelMETA().modelHead.modelVersion();
    }

    public ReferenceDBInBerkeley getReferenceDB()
    {
        if (refDb == null)
        {
            refDb = ReferenceDBInBerkeley.get(getMos());
        }
        return refDb;
    }


}
