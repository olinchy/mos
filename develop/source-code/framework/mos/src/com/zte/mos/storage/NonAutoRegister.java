package com.zte.mos.storage;

import com.odb.database.Odb;
import com.odb.database.trigger.Trigger;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.util.Scan;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Set;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by luoqingkai on 15-3-23.
 */
public class NonAutoRegister extends TriggerRegister
{
//    protected void regModelRulesTriggers(Odb<ManagementObject> odb)
//    {
//        // update relation with parent
//        odb.regGlobalTrigger(afterDelete, new RemoveFromParent());
//        odb.regGlobalTrigger(afterAdd, new AddToParent());
//
//        // lock parent when add or delete child
//        odb.regGlobalTrigger(beforeDelete, new LockParent());
//        odb.regGlobalTrigger(beforeAdd, new LockParent());
//        odb.regGlobalTrigger(afterCommit_remove, new ReleaseParentLock());
//        odb.regGlobalTrigger(afterCommit_add, new ReleaseParentLock());
//    }

    public void register(Odb<ManagementObject> odb)
    {
        regModelRulesTriggers(odb);
        regObjectTriggers(odb);
    }

    public void regObjectTriggers(Odb<ManagementObject> odb)
    {
        regTriggerIn("com.zte.mos.domain.model", odb);
    }

    private void regTriggerIn(String path, Odb<ManagementObject> odb)
    {
        Set<Class<Trigger>> clazzes = Scan.getClasses(path, Trigger.class);
        for (Class<Trigger> clazz : clazzes)
        {
            if (clazz.getSimpleName().equals("AutoAddChildrenTrigger") ||
                    clazz.getSimpleName().equals("DeleteChildrenTrigger"))
            {
                continue;
            }
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
