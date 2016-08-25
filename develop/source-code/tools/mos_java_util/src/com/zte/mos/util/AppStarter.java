package com.zte.mos.util;

import com.zte.mos.util.tools.Prop;

import java.rmi.registry.LocateRegistry;

/**
 * Created by olinchy on 6/4/14 for MO_JAVA.
 */
public class AppStarter
{
    public static void start(String[] args)
            throws Exception
    {
        if (args.length == 0)
        {
            LocateRegistry.createRegistry(Integer.parseInt(Prop.get("app_port")));
        }
        else
        {
            LocateRegistry.createRegistry(Integer.parseInt(Prop.get(args[0])));
        }
    }
}
