package com.zte.mos.msg.framework;

import com.zte.mos.msg.framework.session.ISessionConfigBuilder;
import com.zte.mos.msg.framework.session.ISessionService;
import com.zte.mos.util.Scan;

import java.lang.reflect.Constructor;
import java.util.Set;


public class CommServiceFactory
{
    private static ISouthService service = new SouthService();

    public static ISouthService getService() {
        return service;
    }

    public static void setService(ISouthService userService) {
        service = userService;
    }

    public static void stop(){

    }

    public static void initService() {

        Class[] filters = new Class[3];
        filters[0] = MsgProcess.class;
        filters[1] = ISessionService.class;
        filters[2] = ISessionConfigBuilder.class;
        Set<Class> set = Scan.getClasses("com.zte.mos.msg.impl", filters);
        if (!set.isEmpty()) {
            for (Class clazz : set) {
                if (MsgProcess.class.isAssignableFrom(clazz)){
                    Class<? extends MsgProcess> implClazz = clazz.asSubclass(MsgProcess.class);
                    try {
                        MsgProcessPool.register(implClazz);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else if(ISessionService.class.isAssignableFrom(clazz)
                        || ISessionConfigBuilder.class.isAssignableFrom(clazz)){
                    try
                    {
                        Constructor<?> constructor = clazz.getDeclaredConstructor();
                        constructor.setAccessible(true);
                        constructor.newInstance();
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }

//        Set<Class<LocalOnly>> increamentalSet =
//                Scan.getClasses("com.zte.mos.domain.model.autogen.nr8120.v241.local", LocalOnly.class);
//        if (!increamentalSet.isEmpty()) {
//            for (Class<LocalOnly> clazz : increamentalSet) {
//                Class<? extends LocalOnly> implClazz = clazz.asSubclass(LocalOnly.class);
//                try {
//                    LocalOnlyPool.register(implClazz);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }


}
