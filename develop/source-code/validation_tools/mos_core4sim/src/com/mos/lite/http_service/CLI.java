package com.mos.lite.http_service;

import com.fasterxml.jackson.databind.JsonNode;
import com.mos.lite.Mos;
import com.zte.mos.domain.DN;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.Maybe;
import com.zte.mos.message.*;
import com.zte.mos.util.DistinguishedList;
import com.zte.mos.util.LambdaConverter;
import com.zte.mos.util.To;
import com.zte.mos.util.tools.IDAllocator;
import com.zte.mos.util.tools.JsonUtil;

import java.rmi.RemoteException;
import java.util.ArrayList;

import static com.zte.mos.util.tools.JsonUtil.newObjNode;

/**
 * Created by olinchy on 15-11-18.
 */
public class CLI implements User
{
    static IDAllocator allocator = new IDAllocator(1, 256);
    protected Mos mos = Mos.getInstance();

    @Override
    public JsonNode ping(String sessionId) throws RemoteException, MOSException
    {
        return null;
    }

    @Override
    public JsonNode get(String sessionId, String dn, JsonNode transId) throws RemoteException, MOSException
    {
        Maybe<Integer> trans = toTransId(transId);

        ManagementObject mo = mos.get(new DN(dn), trans);
        if (mo != null)
        {
            ValueObject value = new ValueObject(mo.toMoClass().getMo());
            return JsonUtil.toNode(value);
        }
        return JsonUtil.toNode(new Failure());
    }

    @Override
    public JsonNode getConfig(String sessionId, String dn, JsonNode transId) throws RemoteException, MOSException
    {
        return this.get(sessionId, dn, transId);
    }

    private Maybe<Integer> toTransId(JsonNode transId)
    {
        return JsonUtil.toObject(transId.toString(), Maybe.class);
    }

    @Override
    public JsonNode find(String sessionId, String exp, String dn, JsonNode transId) throws RemoteException, MOSException
    {
        return null;
    }

    @Override
    public JsonNode ls(String sessionId, String dn, JsonNode transId) throws RemoteException, MOSException
    {
        Maybe<Integer> trans = toTransId(transId);

        ArrayList<String> lst = new ArrayList<String>();
        LsResult res = new LsResult(trans, lst);
        try
        {
            ManagementObject father = mos.get(new DN(dn), trans);
            lst.addAll(To.map(father.lsDN(), new LambdaConverter<String, String>()
            {
                @Override
                public String to(String content)
                {
                    return new DN(content).value(-1);
                }
            }));
            res.setResult(0);
        }
        catch (Exception e)
        {
            res.setResult(1);
            res.setEx(e);
        }
        return JsonUtil.toNode(res);
    }

    @Override
    public JsonNode find(
            String sessionId, String exp, String dn, JsonNode transId, int startIndex,
            int count) throws MOSException, RemoteException
    {
        return find(sessionId, exp, dn, transId);
    }

    @Override
    public JsonNode add(String sessionId, JsonNode moList, JsonNode transId) throws RemoteException, MOSException
    {
        return config(new Add(mos), moList, allocateTransId(transId));
    }

    private JsonNode config(Config config, JsonNode moList, Maybe<Integer> transId) throws MOSException
    {
        DistinguishedList<String> lst = new DistinguishedList<String>();
        try
        {
            for (JsonNode jsonNode : moList)
            {
                lst.add(config.config(jsonNode, transId));
            }
        }
        catch (Throwable e)
        {
            return JsonUtil.toNode(new ConfResult(new MOSException(e), lst, transId));
        }
        return JsonUtil.toNode(new ConfResult(lst, transId));
    }

    private Maybe<Integer> allocateTransId(JsonNode transId)
    {
        Maybe<Integer> trans = toTransId(transId);
        if (trans.nothing())
        {
            return allocateTransId();
        }
        return trans;
    }

    private static Maybe<Integer> allocateTransId()
    {
        return new Maybe<Integer>(allocator.allocate());
    }

    @Override
    public JsonNode set(String sessionId, JsonNode moList, JsonNode transId) throws RemoteException, MOSException
    {
        return config(new Set(mos), moList, allocateTransId(transId));
    }

    @Override
    public JsonNode del(String sessionId, JsonNode dnList, JsonNode transId) throws RemoteException, MOSException
    {
        return config(new Del(mos), dnList, allocateTransId(transId));
    }

    @Override
    public JsonNode commit(String sessionId, int transId) throws RemoteException, MOSException
    {
        Revision.revision(mos.commit(new Maybe<Integer>(transId)));

        return JsonUtil.toNode(new Successful());
    }

    @Override
    public JsonNode rollback(String sessionId, int transId) throws RemoteException, MOSException
    {
        mos.rollback(new Maybe<Integer>(transId));
        return JsonUtil.toNode(new Successful());
    }

    @Override
    public JsonNode get_meta(
            String sessionId, String dn, String name, boolean isDnValid,
            JsonNode transId) throws RemoteException, MOSException
    {
        MoMetaClass metaString = null;
        try
        {
            if (isDnValid)
            {
                Maybe<Integer> trans;
                ManagementObject mo = mos.get(new DN(dn), trans = toTransId(transId));
                if (mo == null)
                {
                    mo = mos.get(new DN(dn).parent(), trans);
                    metaString = mo.childMeta(new DN(dn).value(-1));
                }
                else
                {
                    metaString = mo.meta();
                }
            }
            else
            {
                metaString = mos.getMeta(name);
            }
        }
        catch (Exception ignored)
        {
        }

        return JsonUtil.toNode(new GetMetaResult(metaString));
    }

    @Override
    public JsonNode login(
            String role, String userName, String pass, JsonNode server) throws RemoteException, MOSException
    {
        return null;
    }

    @Override
    public void logout(String sessionId) throws RemoteException, MOSException
    {

    }

    @Override
    public JsonNode action(
            String sessionId, String dn, String actionName, JsonNode paras) throws RemoteException, MOSException
    {
        return null;
    }

    @Override
    public JsonNode allSyncRequest(String sessionId) throws RemoteException, MOSException
    {
        return newObjNode();
    }

    @Override
    public JsonNode incSyncRequest(String sessionId, int revision) throws RemoteException, MOSException
    {
        return newObjNode();
    }

    @Override
    public JsonNode ping(String sessionId, int revision) throws RemoteException, MOSException
    {
        return newObjNode();
    }

    @Override
    public JsonNode forceSyncToRemote(String sessionId) throws RemoteException, MOSException
    {
        return newObjNode();
    }
}
