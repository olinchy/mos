package com.mos.lite.http_service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.odb.database.BerkeleyDBIndexer;
import com.odb.database.Key;
import com.odb.database.ODBWalker;
import com.zte.mos.annotation.MoReference;
import com.zte.mos.domain.DN;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.Maybe;
import com.zte.mos.message.Successful;
import com.zte.mos.util.Singleton;
import com.zte.mos.util.tools.JsonUtil;

import java.lang.reflect.Field;
import java.rmi.RemoteException;
import java.util.ArrayList;

import static com.zte.mos.util.basic.Logger.logger;
import static com.zte.mos.util.tools.JsonUtil.newArrayNode;
import static com.zte.mos.util.tools.JsonUtil.newObjNode;

/**
 * Created by olinchy on 15-12-28.
 */
public class EMS extends CLI
{
    @Override
    public JsonNode commit(String sessionId, int transId) throws RemoteException, MOSException
    {
        mos.commit(new Maybe<Integer>(transId));
        return JsonUtil.toNode(new Successful());
    }

    @Override
    public JsonNode allSyncRequest(String sessionId) throws RemoteException, MOSException
    {
        final ArrayList<String> list = new ArrayList<String>();
        final ArrayNode arrayNode = newArrayNode();

        mos.all(new ODBWalker<ManagementObject>()
        {
            @Override
            public boolean contains(ManagementObject data)
            {
                return false;
            }

            @Override
            public void remove(ManagementObject data)
            {

            }

            @Override
            public void add(ManagementObject data) throws MOSException
            {

            }

            @Override
            public void walk(BerkeleyDBIndexer<Key, ManagementObject> indexer) throws MOSException
            {
                ManagementObject root = indexer.get(new Key(new DN("/")));
                push(root, indexer);
            }

            private void push(ManagementObject mo, BerkeleyDBIndexer<Key, ManagementObject> indexer)
            {
                if (hasParent(mo) && moNotIn(mo.dn().parent()))
                {
                    ManagementObject parent = indexer.get(new Key(mo.dn().parent()));
                    push(parent, indexer);
                }
                pushRef(mo, indexer);

                if (moNotIn(mo.dn()))
                {
                    list.add(mo.dn().toString());
                    try
                    {
                        if (!mo.isGroup())
                            arrayNode.add(JsonUtil.toNode(mo.toMoClass()));
                    }
                    catch (MOSException e)
                    {
                        logger(this.getClass()).warn("mo.toMoClass caught", e);
                    }
                    pushChildren(mo, indexer);
                }
            }

            private void pushChildren(ManagementObject mo, BerkeleyDBIndexer<Key, ManagementObject> indexer)
            {
                try
                {
                    for (ManagementObject child : mo.ls())
                    {
                        push(indexer.get(new Key(child.dn())), indexer);
                    }
                }
                catch (MOSException e)
                {
                    logger(this.getClass()).warn("when push child occur!", e);
                }
            }

            private void pushRef(ManagementObject mo, BerkeleyDBIndexer<Key, ManagementObject> indexer)
            {
                Field[] fields = mo.getClass().getFields();
                for (Field field : fields)
                {
                    if (field.isAnnotationPresent(MoReference.class))
                    {
                        try
                        {
                            Object refDN;
                            if ((refDN = field.get(mo)) != null)
                            {
                                ManagementObject ref = indexer.get(new Key(new DN(String.valueOf(refDN))));
                                if (ref != null)
                                    push(ref, indexer);
                            }
                        }
                        catch (IllegalAccessException e)
                        {
                        }
                    }
                }
            }

            private boolean moNotIn(final DN moDN)
            {
                return !list.contains(moDN.toString());
            }

            private boolean hasParent(ManagementObject mo)
            {
                return !mo.dn().toString().equals("/");
            }
        });

        ObjectNode node = newObjNode();
        node.put("result", 0);
        node.put("revision", Singleton.getInstance(Revision.class).getLatestRevision());
        node.put("moList", arrayNode);

        return node;
    }

    @Override
    public JsonNode incSyncRequest(String sessionId, int revision) throws RemoteException, MOSException
    {
        ObjectNode node = newObjNode();

        node.put("revision", revision);
        try
        {
            ArrayNode moList = Singleton.getInstance(Revision.class).toList(revision);
            node.put("result", 0);
            node.put("moOpList", moList);
        }
        catch (RevisionNotFoundException e)
        {
            node.put("result", 1);
        }

        return node;
    }
}
