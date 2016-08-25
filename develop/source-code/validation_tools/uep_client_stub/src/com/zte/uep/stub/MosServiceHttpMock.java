package com.zte.uep.stub;

import com.zte.mos.domain.DN;
import com.zte.mos.exception.MOSException;
import com.zte.mos.httpservice.Config;
import com.zte.mos.httpservice.MosService;
import com.zte.mos.message.*;
import com.zte.mos.type.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pavel on 15-9-3.
 */
public class MosServiceHttpMock implements MosService
{
    private Map<String, Result<ActionRsp>> actResultMap = new HashMap<String, Result<ActionRsp>>();
    private Map<String, Mo> getResultMap = new HashMap<String, Mo>();

    public void setActResult(String actName, Result result)
    {
        actResultMap.put(actName, result);
    }

    public void setGetResultMap(String dn, Mo mo)
    {
        getResultMap.put(dn, mo);
    }

    @Override
    public void startTransaction() throws MOSException
    {

    }

    @Override
    public Result autoCommit(Result result) throws MOSException
    {

        return result;
    }

    private String replaceNeidWith(String dn, String s)
    {
        String preNe = "/Ems/1/Ne/";
        String remain = dn.substring(10);
        String postNe = remain.substring(remain.indexOf('/'));
        return preNe + s + postNe;
    }

    @Override
    public Result<Mo> get(DN... dns) throws MOSException
    {
        ArrayList<Mo> mos = new ArrayList<Mo>();
        for (DN dn : dns)
        {
            Mo mo = getResultMap.get(dn.toString());
            if (mo == null)
            {
                String retryDn = replaceNeidWith(dn.toString(), "*");
                mo = getResultMap.get(retryDn);
                if (mo == null)
                {
                    return new Failure<Mo>();
                }
            }
            mos.add(mo);
        }
        if (mos.isEmpty())
        {
            return new Failure<Mo>();
        }
        return new Successful<Mo>(mos);
//        new DN("/Ems/1/Ne/" + neid + "/Vmp/1/DownloadResult/1")
//        return null;
    }

    @Override
    public Result<Mo> getConfig(DN... dns) throws MOSException
    {
        return get(dns);
    }

    @Override
    public Result<String> ls(DN dn) throws MOSException
    {
        return null;
    }

    @Override
    public Result add(Mo... moes) throws MOSException
    {
        return null;
    }

    @Override
    public Result set(Mo... moes) throws MOSException
    {
        return null;
    }

    @Override
    public Result del(DN... dns) throws MOSException
    {
        return null;
    }

    @Override
    public Result commit() throws MOSException
    {

        return null;
    }

    @Override
    public Result rollback() throws MOSException
    {

        return null;
    }

    @Override
    public Result<MoMetaClass> get_meta(DN dn, String name, boolean isDnValid) throws MOSException
    {
        return null;
    }

    @Override
    public Result<Mo> find(String exp, String dnExp) throws MOSException
    {
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
    public Result<Mo> find(String exp, String dnExp, DN dn) throws MOSException
    {
        return null;
    }

    @Override
    public Result<ActionRsp> act(DN dn, String actionName, Pair<String, Object>... paras) throws MOSException
    {
        return actResultMap.get(actionName);
    }

    @Override
    public ConfResultSet configs(Config... configs) throws MOSException
    {
        return null;
    }
}
