package com.zte.mos.app.alarm.stub;

import com.zte.app.common.fm.AlarmDispatcherService;
import com.zte.app.common.fm.FmService;
import com.zte.mos.app.necache.CacheService;
import com.zte.mos.exception.MOSException;
import com.zte.mos.httpservice.MosService;
import com.zte.mos.util.Singleton;
import com.zte.ums.api.common.fm.ppu.service.FmPPUServerService;
import com.zte.ums.api.common.resource.ppu.service.ResourcePPUServerService;
import com.zte.ums.uep.api.pfl.emb.EMBService;
import com.zte.ums.uep.api.pfl.embmml.EMBMmlService;

/**
 * Created by ccy on 8/13/15.
 */
public class FmServiceImplStub implements FmService {
    @Override
    public EMBMmlService getEMBMmlService()
    {
        return new EMBMmlServiceStub();
    }

    @Override
    public EMBService getEMBService() {
        return new EMBServiceStub();
    }

    @Override
    public CacheService getCacheService() {
        try
        {
            return Singleton.getInstance(CacheServiceStub.class);
        }
        catch (MOSException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public FmPPUServerService getFmPpuServerService() {
        return new FmPPUServerServiceStub();
    }

    @Override
    public MosService getMosService() {
        return new MosServiceHttpStub();
    }

    @Override
    public AlarmDispatcherService getDispatcherService()
    {
        return new AlarmDispatcherStub();
    }

    @Override
    public ResourcePPUServerService getResourcePpuServerService()
    {
        return new ResourcePPUServerServiceStub();
    }
}
