package com.zte.mos.app.rpcserver.mountedapps;

import com.fasterxml.jackson.databind.JsonNode;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.Maybe;
import com.zte.mos.util.tools.JsonUtil;

import java.lang.reflect.Constructor;
import java.util.UUID;

import static com.zte.mos.util.Singleton.getInstance;
import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by olinchy on 7/16/14 for MO_JAVA.
 */
public class AppMounter
{
    public synchronized static String mount(String role, String userName, String pass,
                                            JsonNode server)
            throws MOSException
    {
        String forRemote = UUID.randomUUID().toString();

        getInstance(MountedAppStore.class).put(forRemote,
                mountApp(role, userName, pass, JsonUtil.toObject(server.toString(), Maybe.class)));
        logger(AppMounter.class).info("app " + role + " login with session " + forRemote);
        return forRemote;
    }

    private static Class<? extends MountedApp> findRole(String role) throws MOSException
    {
        return getInstance(MountedAppStore.class).find(role);
    }

    private static MountedApp mountApp(String role, String userName, String pass, Maybe<Server> ser)
            throws MOSException
    {
        MountedApp app;
        try
        {
            Class<? extends MountedApp> clazz = findRole(role);
            Constructor<? extends MountedApp> con =
                    clazz.getConstructor(String.class, String.class, Maybe.class);
            app = con.newInstance(userName, pass, ser);
            app.start();
        }
        catch (Exception e)
        {
            throw new MOSException(e);
        }
        return app;
    }
}
