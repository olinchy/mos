package com.zte.mos.service.impl;

import com.zte.mos.domain.ManagementObject;
import com.zte.mos.domain.ReservedMoChecker;

import java.util.HashMap;

/**
 * Created by ccy on 2/25/16.
 */
public class ReservedMoService
{
    private static HashMap<String, ReservedMoChecker> reservedMoMap = new HashMap<String, ReservedMoChecker>();

    public static void register(ReservedMoChecker reservedMoChecker)
    {
        for(String name : reservedMoChecker.modelNames())
        {
            reservedMoMap.put(toKey(name, reservedMoChecker.modelVersion()), reservedMoChecker);
        }

    }

    private static String toKey(String name, String version)
    {
        return name + "_" + version;
    }

    public static boolean isReserved(String modelName, String modelVersion, ManagementObject mo)
    {
        String key = toKey(modelName, modelVersion);
        ReservedMoChecker reservedMoChecker = reservedMoMap.get(key);
        return reservedMoChecker != null ? reservedMoChecker.isReserved(mo) : false;
    }
}
