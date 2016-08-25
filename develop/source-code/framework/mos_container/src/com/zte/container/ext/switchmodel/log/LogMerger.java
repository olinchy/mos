package com.zte.container.ext.switchmodel.log;

import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.Maybe;
import com.zte.mos.message.Mo;
import com.zte.mos.persistent.Log;
import com.zte.mos.service.MOS;
import com.zte.mos.service.RawMos;

import java.util.*;

import static com.zte.container.ext.switchmodel.tool.MoComparatorToolKit.isMoEqual;

/**
 * Created by ccy on 5/6/16.
 */
public class LogMerger
{
    private final RawMos mos;
    private List<Log<Mo>> Insert = new ArrayList<Log<Mo>>();
    private List<Log<Mo>> Update = new ArrayList<Log<Mo>>();
    private List<Log<Mo>> Delete = new ArrayList<Log<Mo>>();

    private Map<String, Log<Mo>> UpdateMap = new HashMap<String, Log<Mo>>();
    private Map<String, Log<Mo>> DeleteMap = new HashMap<String, Log<Mo>>();


    public List<Log<Mo>> getInsert() throws MOSException
    {
        Queue<ManagementObject> queue = new LinkedList<ManagementObject>();
        queue.add(mos.getMO(MOS.ROOT_INTERNAL_DN, new Maybe<Integer>()));
        while(!queue.isEmpty())
        {
            ManagementObject mo = queue.poll();
            Mo messageMo = mo.toMoClass();

            if (!UpdateMap.containsKey(mo.dn().toString()))
            {
                if (!isGroup(messageMo))
                {
                    Insert.add(new Log<Mo>(null, messageMo, Log.LogType.Insert));
                }
            }
            addChildren(mos, mo, queue);
        }
        return Insert;
    }

    public List<Log<Mo>> getUpdate()
    {
        List<Log<Mo>> list = new ArrayList<Log<Mo>>();

        for(Log<Mo> log: Update)
        {
            if (isGroup(log.newValue()))
            {
                continue;
            }
            if (!isMoEqual(log.oldValue(), log.newValue()))
            {
                list.add(log);
            }
        }

        return list;
    }

    private boolean isGroup(Mo mo)
    {
        return mo.getMoClass().equalsIgnoreCase("Group");
    }

    public List<Log<Mo>> getDelete()
    {
        List<Log<Mo>> list = new ArrayList<Log<Mo>>();

        for(Log<Mo> log: Delete)
        {
            if (isGroup(log.oldValue()))
            {
                continue;
            }
            list.add(log);
        }
        return list;
    }

    public LogMerger(RawMos mos) throws MOSException
    {
        this.mos = mos;
    }


    private void addChildren(RawMos mos, ManagementObject parent, Queue<ManagementObject> queue) throws MOSException
    {
        ManagementObject[] children = parent.ls();
        if (children != null)
        {
            for (ManagementObject child : children)
            {
                queue.add(child);
            }
        }
    }

    public void mergeLog(List<Log<Mo>> list)
    {
        for(Log<Mo> log : list)
        {
            if (Log.LogType.valueOf(log.type()) == Log.LogType.Delete)
            {
                if (!DeleteMap.containsKey(log.oldValue().getDn().toString()))
                {
                    Delete.add(log);
                    DeleteMap.put(log.oldValue().getDn().toString(), log);
                }
            }
            else if (Log.LogType.valueOf(log.type()) == Log.LogType.Update)
            {
                if (!UpdateMap.containsKey(log.oldValue().getDn().toString()))
                {
                    Update.add(log);
                    UpdateMap.put(log.oldValue().getDn().toString(), log);
                }
            }
        }

    }


}
