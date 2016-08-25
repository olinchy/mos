package com.zte.container.ext;

import com.zte.mos.container.RealBundleDomain;
import com.zte.mos.service.IRegService;
import com.zte.mos.service.Regable;
import com.zte.mos.service.VersionReg;
import com.zte.mos.util.tools.Prop;

import java.rmi.Naming;

import static com.zte.mos.util.basic.Logger.logger;

class BundleRegService implements IRegService
{
    //private final RealBundleDomain domain;

    public BundleRegService() {
        //this.domain = domain;
    }

    public String register(String selfURL, Regable domain) throws Exception {
        String regAddress = Prop.get("bundleframe");

        VersionReg verReg = ((VersionReg) Naming.lookup(regAddress));
        String localId = domain.getID();
        if (localId == null)
        {
            String id = verReg.register("", selfURL);
            logger(this.getClass()).debug("start to register bundle id : " + id);
            domain.setID(id);
        }
        else
        {
            logger(this.getClass()).debug("start to update bundle id : " + localId);
            verReg.update("", selfURL, localId);
        }
        return localId;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
