package com.zte.mos.msg.framework.session;

import com.zte.mos.domain.TargetAddress;
import com.zte.mos.msg.framework.Protocol;
import com.zte.mos.msg.framework.except.InvalidUrlException;
import com.zte.mos.msg.framework.except.SessionConfigNotCastException;
import com.zte.mos.msg.framework.except.UnsupportedProtocolException;

import java.lang.reflect.Constructor;
import java.util.HashMap;

/**
 * Created by luoqingkai on 15-9-23.
 */
public class SessionFactory {

    private static HashMap<String, ISessionService> sessionSvMap
            = new HashMap<String, ISessionService>();

    public static ISession create(TargetAddress address, Protocol protocol)
            throws InvalidUrlException, SessionConfigNotCastException, UnsupportedProtocolException {
        ISessionService sv = getSessionService(protocol);
        return sv.createSession(address, protocol.configurations());
    }

    public static ISession recover(TargetAddress address, Protocol protocol) throws UnsupportedProtocolException {
        ISessionService sv = getSessionService(protocol);
        return sv.recover(address);
    }

    public static void unRegister(ISession session) {
        ISessionService sv = sessionSvMap.get(session.protocol());
        sv.deleteSession(session);
    }

    public static boolean isSupported(Protocol protocol) {
        return sessionSvMap.get(protocol.name()) != null;
    }

    private static ISessionService getSessionService(Protocol protocol) throws UnsupportedProtocolException {
        ISessionService sv = sessionSvMap.get(protocol.name());
        if (sv == null) {
            throw new UnsupportedProtocolException(protocol.name());
        }
        return sv;
    }

    public static void register(ISessionService implClazz) {
        Constructor<? extends ISessionService> constructor = null;
        try {
//            constructor = implClazz.getDeclaredConstructor();
//            constructor.setAccessible(true);
//            ISessionService pluginService = constructor.newInstance();
//            sessionSvMap.put(pluginService.getSupportedProtocol(), pluginService);
            sessionSvMap.put(implClazz.getSupportedProtocol(), implClazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private static void regSessionService(){
//        Set<Class<Object>> set = Scan.getClasses("com.zte.mos.msg.impl");
//        if (!set.isEmpty()){
//            for (Class<Object> clazz : set){
//                if (clazz.isAnnotationPresent(SessionService.class)){
//                    Class<? extends ISessionService> implClazz = clazz.asSubclass(ISessionService.class);
//                    Constructor<? extends ISessionService> constructor = null;
//                    try
//                    {
//                        constructor = implClazz.getDeclaredConstructor();
//                        constructor.setAccessible(true);
//                        ISessionService pluginService = constructor.newInstance();
//                        sessionSvMap.put(pluginService.getSupportedProtocol(), pluginService);
//                    }
//                    catch (Exception e)
//                    {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//    }
}
