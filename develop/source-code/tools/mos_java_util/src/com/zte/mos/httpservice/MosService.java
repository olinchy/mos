package com.zte.mos.httpservice;

import com.zte.mos.domain.DN;
import com.zte.mos.exception.MOSException;
import com.zte.mos.message.*;
import com.zte.mos.type.Pair;

public interface MosService
{
    void startTransaction() throws MOSException;

    Result autoCommit(Result result) throws MOSException;

    Result<Mo> get(DN... dns) throws MOSException;

    Result<Mo> getConfig(DN... dns) throws MOSException;

    Result<String> ls(DN dn) throws MOSException;

    Result add(Mo... moes)
            throws MOSException;

    Result set(Mo... moes)
            throws MOSException;

    Result del(DN... dns)
            throws MOSException;

    Result commit() throws MOSException;

    Result rollback() throws MOSException;

    Result<MoMetaClass> get_meta(DN dn, String name, boolean isDnValid)
            throws MOSException;

    Result<Mo> find(String exp, String dnExp) throws MOSException;

    Result<Mo> find(String exp, String dnExp, DN under, int startIndex, int count) throws MOSException;

    void logout() throws MOSException;

    Result<Mo> find(String exp, String dnExp, DN dn) throws MOSException;

    Result<ActionRsp> act(DN dn, String actionName, Pair<String, Object>... paras) throws MOSException;

    ConfResultSet configs(Config... configs) throws MOSException;
}
