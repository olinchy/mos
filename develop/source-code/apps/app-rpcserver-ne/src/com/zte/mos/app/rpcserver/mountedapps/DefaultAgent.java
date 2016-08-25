package com.zte.mos.app.rpcserver.mountedapps;

import com.fasterxml.jackson.databind.JsonNode;
import com.zte.mos.app.rpcserver.handler.*;
import com.zte.mos.domain.DN;
import com.zte.mos.exception.EmptyRequestException;
import com.zte.mos.exception.MOSException;
import com.zte.mos.httpservice.EmsJsonService;
import com.zte.mos.inf.Maybe;
import com.zte.mos.inf.MoCmds;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.*;
import com.zte.mos.type.Pair;
import com.zte.mos.util.DistinguishedList;
import com.zte.mos.util.msg.*;
import com.zte.mos.util.tools.JsonUtil;
import com.zte.smartlink.deliver.DeliverService;

import java.net.MalformedURLException;
import java.util.*;

import static com.zte.mos.util.basic.Logger.logger;
import static com.zte.mos.util.msg.Template.FieldTypes.all;
import static com.zte.mos.util.tools.JsonUtil.*;

/**
 * Created by olinchy on 7/15/14 for MO_JAVA.
 */
public abstract class DefaultAgent extends AbstractApp implements Invoker, EmsJsonService
{
    public DefaultAgent(String userName, String pass, Maybe<Server> server) throws MalformedURLException
    {
        super(userName, pass, server);
    }

    private HashMap<Integer, LinkedHashSet<String>> map = new HashMap<Integer, LinkedHashSet<String>>();

    @Override
    public JsonNode ping(String sessionId) throws MOSException
    {
        return newObjNode();
    }

    @Override
    public JsonNode get(String sessionId, String dn, JsonNode transId) throws MOSException
    {
        return new GetHandler(this).handleQuery(getDN(dn), transId);
    }

    public JsonNode getConfig(String sessionId, String dn, JsonNode transId) throws MOSException
    {
        return new GetConfigHandler(this).handleQuery(getDN(dn), transId);
    }

    @Override
    public JsonNode find(String sessionId, String exp, String dn, JsonNode transId) throws MOSException
    {
        MoFindMsg msg;
        if (dn.contains(";"))
        {
            String dnExp[] = dn.split(";");
            msg = new MoFindMsg(
                    exp, new Template(all), JsonUtil.toObject(transId.toString(), Maybe.class), dnExp[1], dnExp[0]);
        }
        else
        {
            msg = new MoFindMsg(
                    exp, new Template(all), JsonUtil.toObject(transId.toString(), Maybe.class), "/", DN.isDN(
                    dn) ? new DN(dn).value(
                    -1) : dn);
        }

        return toNode(send(msg).getMo().get(0));
    }

    @Override
    public JsonNode find(
            String sessionId, String exp, String dn, JsonNode transId, int startIndex, int count) throws MOSException
    {
        MoFindMsg msg;
        if (dn.contains(";"))
        {
            String dnExp[] = dn.split(";");
            msg = new MoFindMsg(
                    exp, new Template(all), JsonUtil.toObject(transId.toString(), Maybe.class), dnExp[1], dnExp[0]);
        }
        else
        {
            msg = new MoFindMsg(
                    exp, new Template(all), JsonUtil.toObject(transId.toString(), Maybe.class), "/", DN.isDN(
                    dn) ? new DN(dn).value(
                    -1) : dn);
        }
        msg.setStartIndex(startIndex);
        msg.setExpectedCount(count);

        return toNode(send(msg).getMo().get(0));
    }

    @Override
    public JsonNode ls(String sessionId, String dn, JsonNode transId) throws MOSException
    {
        return new lsHandler(this).handleQuery(getDN(dn), transId);
    }

    @Override
    public JsonNode add(String sessionId, JsonNode moList, JsonNode transId) throws MOSException
    {
        AddHandler handler = new AddHandler(this);
        return handTransaction(handler, moList, transId);
    }

    @Override
    public JsonNode set(String sessionId, JsonNode moList, JsonNode transId) throws MOSException
    {
        SetHandler handler = new SetHandler(this);
        return handTransaction(handler, moList, transId);
    }

    @Override
    public JsonNode del(String sessionId, JsonNode dnList, JsonNode transId) throws MOSException
    {
        DeleteHandler handler = new DeleteHandler(this);
        return handTransaction(handler, dnList, transId);
    }

    @Override
    public JsonNode commit(String sessionId, int transId) throws MOSException
    {
        return toNode(sendAck(transId, MoAckMsg.Ack.commit).getMo().get(0));
    }

    @Override
    public JsonNode rollback(String sessionId, int transId) throws MOSException
    {
        Result result = sendAck(transId, MoAckMsg.Ack.rollback).getMo().get(0);
        return toNode(result);
    }

    @Override
    public JsonNode get_meta(
            String sessionId, String dn, String name, boolean isDnValid, JsonNode transId) throws MOSException
    {
        return new GetMetaHandler(this).handle(getDN(dn), name, isDnValid, transId);
    }

    @Override
    public JsonNode login(String role, String userName, String pass, JsonNode server) throws MOSException
    {
        return null;
    }

    @Override
    public void logout(String sessionId) throws MOSException
    {
    }

    @Override
    public JsonNode action(String sessionId, String dn, String actionName, JsonNode paras) throws MOSException
    {
        ArrayList<Pair<String, Object>> lst = new ArrayList<Pair<String, Object>>();
        Iterator<String> it = paras.fieldNames();
        while (it.hasNext())
        {
            String fieldName = it.next();
            lst.add(
                    new Pair<String, Object>(
                            fieldName, JsonUtil.toObject(
                            paras.get(fieldName).toString(), Object.class)));
        }
        Result<Result> res = this.send(new MoActionMsg(dn, new Maybe<Integer>(null), actionName, lst));
        return toNode(res.getMo().get(0));
    }

    public Result<Result> sendAck(int transId, MoAckMsg.Ack ack) throws MOSException
    {
        LinkedHashSet<String> lst = map.get(transId);
        if (lst != null)
        {
            return send(new MoAckMsg(new Maybe<Integer>(transId), ack, lst.toArray(new String[lst.size()])));
        }
        return new Successful<Result>();
    }

    private JsonNode handTransaction(AbstractCfgHandler handler, JsonNode moList, JsonNode transId) throws MOSException
    {
        Pair<String, JsonNode>[] nodes = handler.parseMoList(moList);
        if (nodes.length == 0)
            throw new EmptyRequestException();
        ConfResultSet result = new ConfResultSet();
        try
        {
            for (Pair<String, JsonNode> node : nodes)
            {
                Result res = handler.handleTransaction(node.first(), transId, node.second());
                transId = toNode(res.getTransId());
                if (res instanceof Failure)
                {
                    DistinguishedList<String> list = new DistinguishedList<String>();
                    list.addAll(Arrays.asList(new String[]{node.first()}));
                    result.add(
                            new ConfResult(
                                    res.exception() != null
                                            ? new MOSException(res.exception())
                                            : new MOSException(), list, res.getTransId()));
                }
                else
                {
                    ConfResult confResult = ((ConfResult) res);
                    confResult.setMo(new ArrayList<String>(Arrays.asList(new String[]{node.first()})));
                    result.add(confResult);
                }
            }
        }
        catch (MOSException ignored)
        {
            logger(this.getClass()).debug("config failed", ignored);
            return toNode(new Failure(1, ignored, toObject(transId.toString(), Maybe.class)));
        }
        return toNode(result);
    }

    @Override
    public Result<Result> send(MoMsg msg) throws MOSException
    {
        Result<Result> ret = new DeliverService(getName()).send(msg);
        if (msg instanceof MoAckMsg)
        {
            map.remove(msg.getTransactionID().getValue());
        }

        return ret;
    }

    public Result sendCfg(MoCmds cmd, String dn, Mo mo, JsonNode transIdNode) throws MOSException
    {
        MoMsg msg = new MoConfigMsg(cmd, new DN(getDN(dn)), mo);
        if (transIdNode.get("present").asText().equals("true"))
        {
            Maybe<Integer> transId = new Maybe<Integer>(transIdNode.get("value").asInt());
            msg.setTransId(transId);
        }

        Result<Result> ret = send(msg);

        Result<?> res = ret.getMo().get(0);
        if (res instanceof ConfResult)
        {
            ConfResult operResult = (ConfResult) res;
            int transId = operResult.getTransId().getValue();
            LinkedHashSet<String> dns = map.get(transId);
            if (dns == null)
            {
                dns = new LinkedHashSet<String>();
                map.put(transId, dns);
            }
            if (!dns.contains(msg.dn()))
            {
                dns.add(msg.dn());
            }
            dns.addAll(operResult.getMo());
        }

        return res;
    }

    @Override
    public void sendIndicate(MoCmds cmd, String dn, Mo mo) throws MOSException
    {
        // todo
        //        IndicateMsg msg = new IndicateMsg(cmd, dn, mo);
        //        send(msg);
    }

    protected String getDN(String dn)
    {
        return dn;
    }

    @Override
    public void stop() throws MOSException
    {
    }
}
