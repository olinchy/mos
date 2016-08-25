package com.zte.mos.domain;

import com.zte.mos.annotation.MoAction;
import com.zte.mos.exception.MOSException;
import com.zte.mos.util.basic.Logger;
import com.zte.mos.util.scaner.AbstractScanner;

import java.util.HashMap;

/**
 * Created by olinchy on 4/23/15 for ems-mos.
 */
public class ActionRepo extends AbstractScanner<Action>
{
    private HashMap<Class<?>, HashMap<String, Action>> map = new HashMap<Class<?>, HashMap<String, Action>>();

    private ActionRepo()
    {
    }

    public Action get(Class<?> actionDesc, String actionName)
    {
        try
        {
            return map.get(actionDesc).get(actionName);
        }
        catch (NullPointerException e)
        {
            return null;
        }
    }

    @Override
    public void handle(Class<Action> clazz) throws MOSException
    {
        if (clazz.isAnnotationPresent(MoAction.class))
        {
            MoAction moAction = clazz.getAnnotation(MoAction.class);
            for (Class<?> aClass : moAction.moClass())
            {
                this.put(aClass, moAction.name(), clazz);
            }
        }
    }

    private void put(
            Class<?> actionDescClass, String actionName,
            Class<?> actionClass)
    {
        HashMap<String, Action> actionMap = map.get(actionDescClass);
        if (actionMap == null)
        {
            actionMap = new HashMap<String, Action>();
            map.put(actionDescClass, actionMap);
        }
        try
        {
            actionMap.put(actionName, (Action) actionClass.newInstance());
        }
        catch (Throwable e)
        {
            Logger.logger(this.getClass()).warn("put action " + actionName + " into action repository failed!");
        }
    }
}
