/**
 *
 */
package com.zte.mos.util;

import com.zte.mos.exception.MOSException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Administrator
 */
public abstract class Singleton
{
    private static final ConcurrentHashMap<Class<?>, Object> INSTANCES = new ConcurrentHashMap<Class<?>, Object>();

    private Singleton()
    {
    }

    private static Object createNewInstance(Class<?> claxx) throws MOSException
    {
        boolean isAccessible;
        Constructor<?> constructor = null;

        try
        {
            constructor = claxx.getDeclaredConstructor();
            isAccessible = constructor.isAccessible();
            if (isAccessible || Modifier.isPublic(constructor.getModifiers()))
            {
                throw new NONPrivateDefaultConstructorException(claxx.getName());
            }
            constructor.setAccessible(true);
            return constructor.newInstance();
        }
        catch (Exception e)
        {
            throw new MOSException(e);
        }
        finally
        {
            if (constructor != null)
            {
                constructor.setAccessible(false);
            }
        }
    }

    public static <T> T getInstance(Class<T> claxx) throws MOSException
    {
        synchronized (claxx)
        {
            Object instance = INSTANCES.get(claxx);
            if (instance == null)
            {
                instance = createNewInstance(claxx);
                INSTANCES.put(claxx, instance);
            }
            return claxx.cast(instance);
        }
    }

    public static <T> void reset(Class<T> claxx) throws Exception
    {
        INSTANCES.put(claxx, createNewInstance(claxx));
    }

    //FT
    public static <T> void resetForFT(Class<T> claxx, Object objFT) throws MOSException
    {
        INSTANCES.put(claxx, objFT);
    }
}
