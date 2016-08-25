package com.zte.mos.app.rpcserver.mountedapps;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by olinchy on 7/15/14 for MO_JAVA.
 */
public class MountedAppStore
{
    private static List<Class<? extends MountedApp>> lst = new LinkedList<Class<? extends MountedApp>>();

    static
    {
        lst.add(CliAgent.class);
        lst.add(MOSSERVICEAgent.class);
    }

    private ConcurrentHashMap<String, MountedApp> map = new ConcurrentHashMap<String, MountedApp>();

    private MountedAppStore()
    {
    }

    @Override
    public String toString()
    {
        return "MountedAppStore{" +
                "map=" + map +
                '}';
    }

    public MountedApp get(String sessionId)
    {
        return map.get(sessionId);
    }

    public void put(String sessionId, MountedApp app)
    {
        map.put(sessionId, app);
    }

    public Class<? extends MountedApp> find(String role)
    {
        for (Class<? extends MountedApp> clazz : lst)
        {

            if (clazz.getAnnotation(Role.class).value().equals(role))
            {
                return clazz;
            }
        }
        throw new IllegalArgumentException("role:" + role + " is not valid");
    }

    public void remove(String sessionId)
    {
        map.remove(sessionId);
    }
}
