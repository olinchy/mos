package com.odb.database.trigger;

/**
 * Created by olinchy on 5/21/14 for MO_JAVA.
 */
@SuppressWarnings("unused")
public enum TriggerTiming
{
    beforeAdd, beforeDelete, beforeSet, afterAdd, afterDelete, afterSet,
    beforeCommit_add, beforeCommit_set, beforeCommit_remove,
    afterCommit_add, afterCommit_set, afterCommit_remove

}
