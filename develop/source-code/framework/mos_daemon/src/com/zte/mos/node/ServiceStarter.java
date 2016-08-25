package com.zte.mos.node;

import com.zte.mos.annotation.MoEnum;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.domain.MetaStringStore;
import com.zte.mos.exception.MOSException;
import com.zte.mos.type.CommonTypeRegister;
import com.zte.mos.util.Scan;
import com.zte.mos.util.basic.Logger;
import com.zte.mos.util.scaner.MoEnumInMoScanner;
import com.zte.mos.util.scaner.MoEnumScanner;
import com.zte.mos.util.scaner.PreLoadScanner;

import java.util.LinkedHashSet;
import java.util.Set;

import static com.zte.mos.util.Singleton.getInstance;
import static com.zte.mos.util.basic.Logger.logger;

public class ServiceStarter
{
    private static final Logger log = logger(ServiceStarter.class);

    public static void start() throws MOSException
    {
        initMeta();
        initMetaStringStore();
    }

    private static void initMeta() throws MOSException
    {
        Set<Class<Object>> set = new LinkedHashSet<Class<Object>>();
        set.addAll(Scan.getClasses("com.zte.mos.domain.model"));
        set.addAll(Scan.getClasses("com.zte.mos.autogen"));
        set.addAll(Scan.getClasses("com.zte.app.smartlink"));

        new MoEnumScanner().scan(Scan.getClasses("com.zte.mos.domain", MoEnum.class));
        new MoEnumInMoScanner().scan(Scan.getClasses("com.zte.mos.domain", ManagementObject.class));

        new MoEnumScanner().scan(Scan.getClasses("com.zte.mos.autogen", MoEnum.class));
        new MoEnumInMoScanner().scan(Scan.getClasses("com.zte.mos.autogen", ManagementObject.class));
        new PreLoadScanner().scan(set);

        CommonTypeRegister.regAll();
        log.info("meta related initialized.");
    }

    private static void initMetaStringStore() throws MOSException
    {
        getInstance(MetaStringStore.class);
        log.info("meta string store initialized.");
    }
}
