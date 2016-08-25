package com.zte.mos;

import com.odb.database.Odb;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.persistent.PersistentContext;
import com.zte.mos.storage.TriggerRegister;
import com.zte.mos.storage.triggers.*;

import static com.odb.database.trigger.TriggerTiming.*;

/**
 * Created by root on 14-12-5.
 */
public class UTTriggerRegister extends TriggerRegister
{
    public UTTriggerRegister()
    {
    }

    public void register(PersistentContext persistent, Odb<ManagementObject> odb)
    {
        regModelRulesTriggers(odb);
    }

    protected void regModelRulesTriggers(Odb<ManagementObject> odb)
    {
        // update relation with parent
        odb.regGlobalTrigger(afterDelete, new RemoveFromParent());
        odb.regGlobalTrigger(afterAdd, new AddToParent());

        // delete all children before del, add children after add
        odb.regGlobalTrigger(afterAdd, new AutoAddChildrenTrigger());
        odb.regGlobalTrigger(beforeDelete, new DeleteChildrenTrigger());

        // lock parent when add or delete child
        odb.regGlobalTrigger(beforeDelete, new LockParent());
        odb.regGlobalTrigger(beforeAdd, new LockParent());
        odb.regGlobalTrigger(afterCommit_remove, new ReleaseParentLock());
        odb.regGlobalTrigger(afterCommit_add, new ReleaseParentLock());

        // auto create group
        //odb.regGlobalTrigger(afterAdd, new GroupCreator());
    }

    //    private void regMsgTriggers(Odb<ManagementObject> odb){
    //        // send indicates
    //        try
    //        {
    //            odb.regGlobalTrigger(afterCommit_add, new AddNotiSender(router));
    //            odb.regGlobalTrigger(afterCommit_set, new SetNotiSender(router));
    //            odb.regGlobalTrigger(afterCommit_remove, new RemoveNotiSender(router));
    //        }
    //        catch (Exception e)
    //        {
    //        }
    //    }
}
