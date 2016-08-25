package com.zte.container.ext.switchmodel;

import com.zte.mos.domain.DN;
import com.zte.mos.httpservice.MosService;
import com.zte.mos.httpservice.MosServiceHttp;
import com.zte.mos.message.ActionRsp;
import com.zte.mos.message.Result;
import com.zte.mos.type.Pair;

import java.util.ArrayList;
import java.util.List;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by ccy on 5/9/16.
 */
public class EmsRoutingRegister
{
    private static final String EMS_ROUTING = "/Ems/1/Routing/1";

    public EmsRoutingRegister()
    {
    }

    public void register(String actionName, String dn, String bundleId)
    {
        try
        {
            MosService http = new MosServiceHttp();
            Result<ActionRsp> result = http.act(new DN(EMS_ROUTING), actionName, paras(dn, bundleId));

            if (!result.isSuccess())
            {
                logger(EmsRoutingRegister.class).error(dn + " fail to reg ems routing, action: " + actionName);
            }
        }
        catch (Throwable e)
        {
            logger(EmsRoutingRegister.class).error(dn + " fail to reg ems routing, action: " + actionName, e);
        }
    }

    Pair<String, Object>[] paras(String dn, String bundleId)
    {
        List<Pair<String, Object>> list = new ArrayList<Pair<String, Object>>();
        list.add(new Pair<String, Object>("dn", dn));
        list.add(new Pair<String, Object>("bundleId", String.valueOf(bundleId)));
        return list.toArray(new Pair[list.size()]);
    }
}
