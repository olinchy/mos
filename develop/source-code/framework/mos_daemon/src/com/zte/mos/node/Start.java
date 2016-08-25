package com.zte.mos.node;

import com.zte.mos.domain.Action;
import com.zte.mos.domain.ActionRepo;
import com.zte.mos.domain.MetaStringStore;
import com.zte.mos.domain.MosSettings;
import com.zte.mos.exception.MOSException;
import com.zte.mos.message.MsgService;
import com.zte.mos.service.MOS;
import com.zte.mos.service.VersionReg;
import com.zte.mos.util.Scan;
import com.zte.mos.util.basic.Logger;
import com.zte.mos.util.scaner.PreLoadScanner;
import com.zte.mos.util.tools.Prop;
import com.zte.scope.ems.EmsDomain;
import com.zte.smartlink.SmartLinkNode;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Set;

import static com.zte.mos.message.MoMetaClass.Package_Prefix;
import static com.zte.mos.util.Singleton.getInstance;

public class Start extends SmartLinkNode
{

    //private MOS mos;
    private EmsDomain emsDomain;

    private static void prepare() throws MOSException
    {
        preload();
        ServiceStarter.start();
    }

    private static void saveParams() throws MOSException
    {
        getInstance(MosSettings.class).set("Root");
        getInstance(MosSettings.class).setProcessName("MOS");
    }

    private static void preload() throws MOSException
    {
        getInstance(ActionRepo.class).scan(Scan.getClasses(Package_Prefix,
                Action.class));

        Set<Class<Object>> set = Scan.getClasses("com.zte.mos.persistent", Object.class);
        PreLoadScanner scanner = new PreLoadScanner();
        scanner.scan(set);
    }

    @Override
    public void start(String... args) throws MOSException
    {
        saveParams();
        prepare();

        MOS mos = MosFactory.createMOS(getInstance(MetaStringStore.class).getMeta("ems", "v1540", "Root"),
                MOS.ROOT_INTERNAL_DN,
                null, null);
        this.emsDomain = new EmsDomain(MOS.ROOT_INTERNAL_DN, mos);
        try{
            VersionReg reg = new VersionRegImpl(emsDomain);
            Naming.bind(Prop.get("bundleframe"), reg);
            Logger.logger(Start.class).info("binding bundle frame!");
        }catch (Exception e){
            throw new MOSException(e);
        }

    }

    @Override
    public MsgService getService() throws MOSException
    {
        EmsMsgRouter router;
        try
        {
            router = new EmsMsgRouter(emsDomain);
        }
        catch (RemoteException e)
        {
            throw new MOSException(e);
        }
        return router;
    }

    @Override
    public String getName()
    {
        return "MOS";
    }

}
