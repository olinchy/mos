package com.zte.mos.msg.framework;

import com.zte.mos.domain.TargetAddress;
import com.zte.mos.msg.framework.except.UserDefineException;
import com.zte.mos.msg.framework.operation.IOperation;
import com.zte.mos.util.basic.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by luoqingkai on 15-5-14.
 */
public class MsgProcessPool {
    private static final Logger log = logger(MsgProcessPool.class);
    private static ConcurrentHashMap<String, List<MsgProcess>> pool =
            new ConcurrentHashMap<String, List<MsgProcess>>();

    //private static IMsgProcess default = new

    public static void register(Class<? extends MsgProcess> implClazz){
        Constructor<? extends MsgProcess> constructor = null;
        try
        {
            if (Modifier.isAbstract(implClazz.getModifiers())) {
                return;
            }
            constructor = implClazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            MsgProcess process = constructor.newInstance();

            String[] moTypes = process.moTypes();
            if (moTypes == null) {
                log.info("supported mo types is null:" + implClazz.getName());
                return;
            }

            for (String type : moTypes) {
                String key = buildKey(type, process.operation());
                List<MsgProcess> list = getProcessList(key);
                list.add(process);
                pool.put(key, list);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static List<MsgProcess> getProcessList(String key) {
        List<MsgProcess> list = pool.get(key);
        if (list == null) {
            list = new LinkedList<MsgProcess>();
        }
        return list;
    }

    private static String buildKey(String moType, String oper) {
        return oper + "_" + moType;
    }

    public static void unRegister(String key) {
        pool.remove(key);
    }

    public static MsgProcess getMsgProcess(TargetAddress address, IOperation oper) throws UserDefineException {
        String key = buildKey(oper.getMoType(), oper.getOperation());
        List<MsgProcess> list = pool.get(key);
        if (list != null) {
            for (MsgProcess process : list) {
                if (process.isSupported(address)) {
                    try {
                        return process.getClass().newInstance();
                    } catch (Exception e) {
                        throw new UserDefineException(e);
                    }
                }
            }
        }
        return null;
    }

}
