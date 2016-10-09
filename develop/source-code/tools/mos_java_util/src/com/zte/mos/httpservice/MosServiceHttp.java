package com.zte.mos.httpservice;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import com.zte.mos.domain.DN;
import com.zte.mos.exception.LoginFailedException;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.Maybe;
import com.zte.mos.message.*;
import com.zte.mos.type.Pair;
import com.zte.mos.util.basic.Logger;
import com.zte.mos.util.tools.JsonUtil;
import com.zte.mos.util.tools.Prop;
import org.eclipse.jetty.server.Server;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static com.zte.mos.util.tools.JsonUtil.*;

/**
 * Created by olinchy on 3/17/15 for mosjava.
 * <p/>
 * make the interface better
 * not thread-safe
 */
public class MosServiceHttp implements MosService
{
    public MosServiceHttp() throws MOSException
    {
        this(Prop.get("serverIP") != null ? Prop.get("serverIP") : "localhost");
    }

    public MosServiceHttp(String rpcServer) throws MOSException
    {
        this(rpcServer, 54321);
    }

    public MosServiceHttp(URL url) throws LoginFailedException
    {
        try
        {
            if (sessionId == null)
            {
                client = JsonRpcHttpClientCreator.create(url);
                login();
            }
        }
        catch (Throwable throwable)
        {
            client = null;
            logger.error("start rpc client and login failed!", throwable);
            throw new LoginFailedException(throwable, url.toString());
        }
    }

    public MosServiceHttp(String rpcServer, int port) throws MOSException
    {
        try
        {
            if (sessionId == null)
            {
                initClient(rpcServer, port);
                login();
            }
        }
        catch (Throwable throwable)
        {
            client = null;
            logger.error("start rpc client and login failed!", throwable);
            throw new LoginFailedException(throwable, rpcServer);
        }
    }

    protected void initClient(String rpcServer, int port) throws MalformedURLException
    {
        String protocol = Prop.get("protocol");
        if (protocol == null)
            protocol = "http";
        rpcServer = String.format(protocol + "://%1$s:%2$-9d", rpcServer, port);
        client = JsonRpcHttpClientCreator.create(new URL(rpcServer));
    }

    protected void login()
            throws MOSException
    {
        JsonNode node;
        try
        {
            node = client.invoke(
                    "login",
                    new Object[]{"MOSSERVICE", "ems", "ems", JsonUtil.toNode(new Maybe<Server>(null))},
                    JsonNode.class);
        }
        catch (Throwable throwable)
        {
            throw new MOSException(throwable);
        }
        if (node.findValue("result").intValue() != 1)
        {
            sessionId = node.findValue("sessionId").textValue();
        }
        else
        {
            throw new MOSException("login failed!");
        }
    }

    protected static JsonRpcHttpClient client;
    protected static String sessionId = null;
    protected static Logger logger = Logger.logger(MosServiceHttp.class);
    protected boolean isInTransaction = false;
    protected Maybe transId = new Maybe<Integer>(null);

    @Override
    public void startTransaction() throws MOSException
    {
        if (!isInTransaction)
            this.isInTransaction = true;
        else
            throw new MOSException("transaction started already!");
    }

    @Override
    public Result autoCommit(Result result) throws MOSException
    {
        if (!isInTransaction)
        {
            if (result.isSuccess())
                return commit();
            else
            {
                return rollback();
            }
        }
        return result;
    }

    @Override
    public Result<Mo> get(final DN... dns) throws MOSException
    {
        return methodShell(
                new GetExecutor()
                {
                    @Override
                    public JsonNode exec() throws MOSException
                    {
                        ArrayNode node = newArrayNode();
                        for (DN dn : dns)
                        {
                            try
                            {
                                ObjectNode mo = client.invoke(
                                        "get",
                                        new Object[]{sessionId, dn.toString(), transId()},
                                        ObjectNode.class);
                                node.add(mo);
                            }
                            catch (MOSException e)
                            {
                                throw e;
                            }
                            catch (Throwable throwable)
                            {
                                throw new MOSException(throwable);
                            }
                        }
                        return node;
                    }
                });
    }

    @Override
    public Result<Mo> getConfig(final DN... dns) throws MOSException
    {
        return methodShell(
                new GetExecutor()
                {
                    @Override
                    public JsonNode exec() throws MOSException
                    {
                        ArrayNode node = newArrayNode();
                        for (DN dn : dns)
                        {
                            try
                            {
                                ObjectNode mo = client.invoke(
                                        "getConfig",
                                        new Object[]{sessionId, dn.toString(), transId()},
                                        ObjectNode.class);
                                node.add(mo);
                            }
                            catch (MOSException e)
                            {
                                throw e;
                            }
                            catch (Throwable throwable)
                            {
                                throw new MOSException(throwable);
                            }
                        }
                        return node;
                    }
                });
    }

    @Override
    public Result<String> ls(final DN dn) throws MOSException
    {
        return methodShell(
                new Executor<String>()
                {
                    @Override
                    public Result<String> post(JsonNode result, MosService service)
                            throws MOSException
                    {
                        if (result.findValue("result").intValue() == 0)
                        {
                            return new Successful<String>(
                                    toObject(result.findValue("children").toString(), ArrayList.class));
                        }
                        else
                        {
                            return toObject(result.toString(), Failure.class);
                        }
                    }

                    @Override
                    public Result postException(Throwable throwable, MosService service)
                    {
                        return new Failure(throwable);
                    }

                    @Override
                    public JsonNode exec() throws MOSException
                    {
                        try
                        {
                            return client.invoke(
                                    "ls", new Object[]{sessionId, dn.toString(), transId()},
                                    JsonNode.class);
                        }
                        catch (MOSException e)
                        {
                            throw e;
                        }
                        catch (Throwable throwable)
                        {
                            throw new MOSException(throwable);
                        }
                    }
                });
    }

    @Override
    public Result add(final Mo... moes) throws MOSException
    {
        return methodShell(
                new ConfigExecutor()
                {
                    @Override
                    public JsonNode exec() throws MOSException
                    {
                        try
                        {
                            return handle(
                                    client.invoke(
                                            "add",
                                            new Object[]{sessionId, Serializer.serialize(moes),
                                                    transId()},
                                            JsonNode.class));
                        }
                        catch (MOSException e)
                        {
                            throw e;
                        }
                        catch (Throwable throwable)
                        {
                            throw new MOSException(throwable);
                        }
                    }
                });
    }

    @Override
    public Result set(final Mo... moes) throws MOSException
    {
        return methodShell(
                new ConfigExecutor()
                {
                    @Override
                    public JsonNode exec() throws MOSException
                    {
                        try
                        {
                            return handle(
                                    client.invoke(
                                            "set",
                                            new Object[]{sessionId, Serializer.serialize(moes),
                                                    transId()},
                                            JsonNode.class));
                        }
                        catch (MOSException e)
                        {
                            throw e;
                        }
                        catch (Throwable throwable)
                        {
                            throw new MOSException(throwable);
                        }
                    }
                });
    }

    @Override
    public Result del(final DN... dns) throws MOSException
    {
        return methodShell(
                new ConfigExecutor()
                {
                    @Override
                    public JsonNode exec() throws MOSException
                    {
                        try
                        {
                            return handle(
                                    client.invoke(
                                            "del",
                                            new Object[]{sessionId, dnArrayToString(dns), transId()},
                                            JsonNode.class));
                        }
                        catch (MOSException e)
                        {
                            throw e;
                        }
                        catch (Throwable throwable)
                        {
                            throw new MOSException(throwable);
                        }
                    }
                });
    }

    @Override
    public Result commit() throws MOSException
    {
        return methodShell(
                new AckExecutor()
                {
                    @Override
                    public JsonNode exec() throws MOSException
                    {
                        try
                        {
                            return client.invoke("commit", new Object[]{sessionId, transId.getValue()}, JsonNode.class);
                        }
                        catch (MOSException e)
                        {
                            throw e;
                        }
                        catch (Throwable throwable)
                        {
                            throw new MOSException(throwable);
                        }
                        finally
                        {
                            isInTransaction = false;
                        }
                    }
                });
    }

    @Override
    public Result rollback() throws MOSException
    {
        return methodShell(
                new AckExecutor()
                {
                    @Override
                    public JsonNode exec() throws MOSException
                    {
                        try
                        {
                            return client.invoke(
                                    "rollback", new Object[]{sessionId, transId.getValue()}, JsonNode.class);
                        }
                        catch (MOSException e)
                        {
                            throw e;
                        }
                        catch (Throwable throwable)
                        {
                            throw new MOSException(throwable);
                        }
                        finally
                        {
                            isInTransaction = false;
                        }
                    }
                });
    }

    @Override
    public Result<MoMetaClass> get_meta(final DN dn, final String name, final boolean isDnValid)
            throws MOSException
    {
        return methodShell(
                new Executor<MoMetaClass>()
                {
                    @Override
                    public Result<MoMetaClass> post(JsonNode result, MosService service)
                            throws MOSException
                    {
                        if (result.findValue("result").intValue() == 0)
                        {
                            JsonNode meta = result.get("meta");
                            MoMetaClass metaClass = toObject(meta.asText(), MoMetaClass.class);
                            return new Successful<MoMetaClass>(metaClass);
                        }
                        return new Failure<MoMetaClass>();
                    }

                    @Override
                    public Result postException(Throwable throwable, MosService service)
                    {
                        return new Failure(throwable);
                    }

                    @Override
                    public JsonNode exec() throws MOSException
                    {
                        try
                        {
                            return client.invoke(
                                    "get_meta",
                                    new Object[]{sessionId, dn.toString(), name, isDnValid, transId()},
                                    JsonNode.class);
                        }
                        catch (MOSException e)
                        {
                            throw e;
                        }
                        catch (Throwable throwable)
                        {
                            throw new MOSException(throwable);
                        }
                    }
                });
    }

    @Override
    public Result<Mo> find(final String exp, final String dnExp) throws MOSException
    {
        return methodShell(
                new FindExecutor()
                {
                    @Override
                    public JsonNode exec() throws MOSException
                    {
                        try
                        {
                            return client.invoke(
                                    "find",
                                    new Object[]{sessionId, exp, dnExp, transId()},
                                    JsonNode.class);
                        }
                        catch (MOSException e)
                        {
                            throw e;
                        }
                        catch (Throwable throwable)
                        {
                            throw new MOSException(throwable);
                        }
                    }
                });
    }

    @Override
    public Result<Mo> find(
            final String exp, final String dnExp, final DN under, final int startIndex,
            final int count) throws MOSException
    {
        return methodShell(
                new FindExecutor()
                {
                    @Override
                    public JsonNode exec() throws MOSException
                    {
                        try
                        {
                            return client.invoke(
                                    "find",
                                    new Object[]{sessionId, exp, dnExp + ";" + under.toString(), transId(), startIndex, count},
                                    JsonNode.class);
                        }
                        catch (MOSException e)
                        {
                            throw e;
                        }
                        catch (Throwable throwable)
                        {
                            throw new MOSException(throwable);
                        }
                    }
                });
    }

    @Override
    public void logout() throws MOSException
    {
        try
        {
            client.invoke("logout", new Object[]{sessionId});
        }
        catch (Throwable throwable)
        {
            throw new MOSException(throwable);
        }
    }

    @Override
    public Result<Mo> find(final String exp, final String dnExp, final DN dn)
            throws MOSException
    {
        return methodShell(
                new FindExecutor()
                {
                    @Override
                    public JsonNode exec() throws MOSException
                    {
                        try
                        {
                            return client.invoke(
                                    "find",
                                    new Object[]{sessionId, exp, dnExp + ";" + dn.toString(), transId()},
                                    JsonNode.class);
                        }
                        catch (MOSException e)
                        {
                            throw e;
                        }
                        catch (Throwable throwable)
                        {
                            throw new MOSException(throwable);
                        }
                    }
                });
    }

    @Override
    public Result<ActionRsp> act(
            final DN dn, final String actionName, final Pair<String, Object>... paras)
            throws MOSException
    {
        return methodShell(
                new Executor<ActionRsp>()
                {
                    @Override
                    public JsonNode exec() throws MOSException
                    {
                        try
                        {
                            ObjectNode node = newObjNode();
                            for (Pair<String, Object> para : paras)
                            {
                                node.put(para.first(), JsonUtil.toNode(para.second()));
                            }
                            return client.invoke(
                                    "action", new Object[]{sessionId, dn.toString(), actionName, node}, JsonNode.class);
                        }
                        catch (MOSException e)
                        {
                            throw e;
                        }
                        catch (Throwable throwable)
                        {
                            throw new MOSException(throwable);
                        }
                    }

                    @Override
                    public Result<ActionRsp> post(JsonNode result, MosService service) throws MOSException
                    {
                        ActionRsp rsp = null;
                        if (result.has("mo"))
                        {
                            rsp = JsonUtil.toObject(result.get("mo").get(0).toString(), ActionRsp.class);
                        }
                        if (result.get("result").intValue() == 0)
                        {
                            return new Successful<ActionRsp>(rsp);
                        }
                        Result fail = toObject(result.toString(), Failure.class);
                        fail.exception();

                        return fail;
//                         return new Failure(fail.exception());

//                        return new Failure<ActionRsp>();
                    }

                    @Override
                    public Result<ActionRsp> postException(Throwable throwable, MosService service)
                    {
                        logger.error(
                                "fail to do action , dn: " + dn.toString() + "actionName:" + actionName, throwable);
                        return new Failure<ActionRsp>(1, throwable);
                    }
                });
    }

    @Override
    public ConfResultSet configs(Config... configs) throws MOSException
    {
        ConfResultSet set = new ConfResultSet();
        for (Config config : configs)
        {
            set.merge(config.doConfig(this));
        }
        return set;
    }

    private String[] dnArrayToString(DN[] dns)
    {
        StringBuilder builder = new StringBuilder();
        for (DN dn : dns)
        {
            builder.append(dn.toString()).append(",");
        }
        return builder.deleteCharAt(builder.length() - 1).toString().split(",");
    }

    private JsonNode handle(JsonNode ret) throws MOSException
    {
        JsonNode transIdNode = ret.findValue("transId");
        if (transIdNode == null)
        {
            logger.test(" transid is null, result is " + ret.toString());
        }
        if (transIdNode != null && !transIdNode.has("present"))
        {
            this.transId = new Maybe<Integer>(ret.findValue("transId").intValue());
        }
        return ret;
    }

    protected <T> Result<T> methodShell(Executor<T> executor) throws MOSException
    {
        StackTraceElement[] stackTraceElements = new Throwable().getStackTrace();
        logger.debug(stackTraceElements[2] + " is calling " + stackTraceElements[1]);
        try
        {
            return executor.post(executor.exec(), this);
        }
        catch (SessionNotFoundException e)
        {
            this.login();
            return executor.post(executor.exec(), this);
        }
        catch (Throwable e)
        {
            return executor.postException(e, this);
        }
    }

    private Maybe transId()
    {
        return isInTransaction ? transId : new Maybe<Integer>(null);
    }
}
