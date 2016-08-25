package com.zte.mos.util.scaner;

import com.zte.mos.annotation.PreLoad;

import java.lang.reflect.Constructor;

public class PreLoadScanner extends AbstractScanner<Object>
{

    @Override
    public void handle(Class<Object> clazz)
    {
        Constructor<?> constructor = null;
        if (clazz.isAnnotationPresent(PreLoad.class))
        {
            try
            {
                constructor = clazz.getDeclaredConstructor();
                constructor.setAccessible(true);
                constructor.newInstance();
            } catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }

}
