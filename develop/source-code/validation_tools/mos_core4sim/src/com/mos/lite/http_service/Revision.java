package com.mos.lite.http_service;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;
import com.zte.mos.persistent.Log;
import com.zte.mos.util.tools.IDAllocator;
import com.zte.mos.util.tools.JsonUtil;

import java.util.LinkedList;

import static com.zte.mos.util.basic.Logger.logger;
import static com.zte.mos.util.tools.JsonUtil.newArrayNode;
import static com.zte.mos.util.tools.JsonUtil.newObjNode;

/**
 * Created by olinchy on 15-12-28.
 */
public class Revision
{
    private Revision()
    {
    }

    static IDAllocator revisionAllocator = new IDAllocator(1, 65535);
    static int MAX_CAPACITY = 20;
    static LinkedList<CommitLog> logList = new LinkedList<CommitLog>();

    public static synchronized void revision(LinkedList<Log<ManagementObject>> logs)
    {
        if (logList.size() == MAX_CAPACITY)
        {
            logList.removeFirst();
        }
        int id = revisionAllocator.allocate();
        logList.add(new CommitLog(id, logs));
    }

    public synchronized ArrayNode toList(int revision) throws RevisionNotFoundException
    {
        ArrayNode arrayNode = newArrayNode();

        int index = logList.indexOf(new CommitLog(revision + 1));

        if (index == -1)
            throw new RevisionNotFoundException();

        for (int i = index; i < logList.size(); i++)
        {
            LinkedList<Log<ManagementObject>> list = logList.get(i).logs;
            try
            {
                for (Log<ManagementObject> log : list)
                {
                    ObjectNode objNode = newObjNode();
                    //Insert, Update, Delete
                    switch (Log.LogType.valueOf(log.type()))
                    {
                        case Insert:
                            objNode = (ObjectNode) JsonUtil.toNode(log.newValue().toMoClass());
                            objNode.put("type", "add");
                            break;
                        case Delete:
                            objNode = newObjNode();
                            objNode.put("type", "del");
                            objNode.put("dn", log.oldValue().dn().toString());
                            break;
                        case Update:
                            objNode = (ObjectNode) JsonUtil.toNode(log.newValue().toMoClass());
                            objNode.put("type", "set");
                            break;
                    }
                    arrayNode.add(objNode);
                }
            }
            catch (MOSException e)
            {
                logger(this.getClass()).warn("", e);
            }
        }

        return arrayNode;
    }

    public int getLatestRevision()
    {
        if (logList.size() > 0)
            return logList.getLast().id;
        return 0;
    }
}
