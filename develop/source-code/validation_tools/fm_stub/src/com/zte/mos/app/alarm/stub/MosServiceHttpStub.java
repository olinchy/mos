package com.zte.mos.app.alarm.stub;

import com.zte.mos.domain.DN;
import com.zte.mos.exception.MOSException;
import com.zte.mos.httpservice.Config;
import com.zte.mos.httpservice.MosService;
import com.zte.mos.message.*;
import com.zte.mos.type.Pair;

/**
 * Created by ccy on 8/13/15.
 */
public class MosServiceHttpStub implements MosService {
    @Override
    public void startTransaction() throws MOSException {

    }

    @Override
    public Result autoCommit(Result result) throws MOSException {

        return result;
    }

    @Override
    public Result<Mo> get(DN... dns) throws MOSException {
        return null;
    }

    @Override
    public Result<Mo> getConfig(DN... dns) throws MOSException
    {
        return null;
    }

    @Override
    public Result<String> ls(DN dn) throws MOSException {
        return null;
    }

    @Override
    public Result add(Mo... moes) throws MOSException {
        return null;
    }

    @Override
    public Result set(Mo... moes) throws MOSException {
        return null;
    }

    @Override
    public Result del(DN... dns) throws MOSException {
        return null;
    }

    @Override
    public Result commit() throws MOSException {

        return null;
    }

    @Override
    public Result rollback() throws MOSException {

        return null;
    }

    @Override
    public Result<MoMetaClass> get_meta(DN dn, String name, boolean isDnValid) throws MOSException {
        return null;
    }

    @Override
    public Result<Mo> find(String exp, String dnExp) throws MOSException {
        return null;
    }

    @Override
    public Result<Mo> find(String exp, String dnExp, DN under, int startIndex, int count) throws MOSException
    {
        return null;
    }

    @Override
    public void logout() throws MOSException
    {

    }

    @Override
    public Result<Mo> find(String exp, String dnExp, DN dn) throws MOSException {
        return null;
    }

    @Override
    public Result<ActionRsp> act(DN dn, String actionName, Pair<String, Object>... paras) throws MOSException {
        return new Successful<ActionRsp>(new ActionRsp(null, null));
    }

    @Override
    public ConfResultSet configs(Config... configs) throws MOSException
    {
        return null;
    }
}
