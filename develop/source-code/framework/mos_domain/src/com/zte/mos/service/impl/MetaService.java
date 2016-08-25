package com.zte.mos.service.impl;

import com.zte.mos.annotation.MoEnum;
import com.zte.mos.domain.Action;
import com.zte.mos.domain.ActionRepo;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.domain.MetaStringStore;
import com.zte.mos.message.MoMetaClass;
import com.zte.mos.service.IMetaService;
import com.zte.mos.service.ReservedMoScanner;
import com.zte.mos.util.Scan;
import com.zte.mos.util.scaner.MoEnumInMoScanner;
import com.zte.mos.util.scaner.MoEnumScanner;
import com.zte.mos.util.scaner.PreLoadScanner;

import java.util.LinkedHashSet;
import java.util.Set;

import static com.zte.mos.util.Singleton.getInstance;
import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by olinchy on 16-4-1.
 */
public class MetaService implements IMetaService
{
    @Override
    public void start()
    {
        Set<Class<Object>> set = new LinkedHashSet<Class<Object>>();
        set.addAll(Scan.getClasses("com.zte.mos.domain"));
        set.addAll(Scan.getClasses("com.zte.app.smartlink"));
        set.addAll(Scan.getClasses(MoMetaClass.Package_Prefix));
        try
        {
            new MoEnumScanner().scan(Scan.getClasses(MoMetaClass.Package_Prefix, MoEnum.class));
            new MoEnumInMoScanner().scan(Scan.getClasses(MoMetaClass.Package_Prefix, ManagementObject.class));
            getInstance(ActionRepo.class).scan(Scan.getClasses(MoMetaClass.Package_Prefix, Action.class));
            new PreLoadScanner().scan(set);
            new ReservedMoScanner().scan(set);

            getInstance(MetaStringStore.class);
        }
        catch (Throwable e)
        {
            logger(this.getClass()).error(" fail to start meta service", e);
        }
    }

    @Override
    public void stop()
    {

    }
}
