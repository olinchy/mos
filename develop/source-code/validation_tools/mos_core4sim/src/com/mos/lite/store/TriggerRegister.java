package com.mos.lite.store;

import com.odb.database.Odb;
import com.odb.database.trigger.Trigger;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.util.Scan;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Set;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by luoqingkai on 14-12-2.
 * For TriggerableDB to register triggers.
 */
public class TriggerRegister
{
    public void register(Odb<ManagementObject> odb)
    {
        regModelRulesTriggers(odb);
        regObjectTriggers(odb);
    }

    private void regObjectTriggers(Odb<ManagementObject> odb)
    {
        regTriggerIn("com.zte.mos.domain.model", odb);
    }

    private void regTriggerIn(String path, Odb<ManagementObject> odb)
    {
        Set<Class<Trigger>> clazzes = Scan.getClasses(path, Trigger.class);
        for (Class<Trigger> clazz : clazzes)
        {
            if (!Modifier.isAbstract(clazz.getModifiers()))
            {
                try
                {
                    Constructor<Trigger> con = clazz.getConstructor();
                    Trigger<ManagementObject> meta = con.newInstance();
                    meta.register(odb);
                }
                catch (Exception e)
                {
                    logger(TriggerRegister.class).info("reg " + clazz.getSimpleName() + " failed!");
                }
            }
        }
    }

    protected void regModelRulesTriggers(Odb<ManagementObject> odb)
    {
        regTriggerIn("com.zte.mos.storage.triggers", odb);
    }
}