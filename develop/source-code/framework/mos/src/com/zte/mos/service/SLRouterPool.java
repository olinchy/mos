package com.zte.mos.service;

import java.util.HashMap;

/**
 * Created by luoqingkai on 15-6-2.
 */
public class SLRouterPool
{
    private static HashMap<String, SmartLinkRouter> map
            = new HashMap<String, SmartLinkRouter>();

    public static void reg(String externalDN, SmartLinkRouter router)
    {
        map.put(externalDN, router);

    }

    public static void unReg(String externalDN)
    {
        map.remove(externalDN);
    }

    public static SmartLinkRouter getRouter(String externalDN)
    {
        return map.get(externalDN);
    }
}
