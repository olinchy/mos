package com.odb.database.trigger;

import com.odb.database.Odb;
import com.zte.mos.domain.Group;
import com.zte.mos.exception.MOSException;

import java.util.*;

/**
 * Created by olinchy on 5/21/14 for MO_JAVA.
 */
public class TriggerGroup<T extends Group> implements Trigger<T>
{
    private HashMap<Class<?>, List<Trigger<T>>> map = new HashMap<Class<?>, List<Trigger<T>>>();
    private ArrayList<Trigger<T>> world = new ArrayList<Trigger<T>>();

    public void put(Class<?> objClass, Trigger<T> trigger)
    {
        List<Trigger<T>> triggers = map.get(objClass);
        if (triggers == null)
        {
            triggers = new ArrayList<Trigger<T>>();
            map.put(objClass, triggers);
        }
        if (!triggers.contains(trigger))
        {
            triggers.add(trigger);
        }
    }

    public void remove(Class<?> aClass, Trigger<T> trigger)
    {
        List<Trigger<T>> triggers = map.get(aClass);
        triggers.remove(trigger);
    }

    public void addWorldWide(Trigger<T> trigger)
    {
        world.add(trigger);
    }

    @Override
    public void activate(T t, Odb<T> odb) throws MOSException
    {
        activateWorld(t, odb);

        activateObject(t, odb);
    }

    @Override
    public void register(Odb<T> odb)
    {

    }

    private void activateWorld(T t, Odb<T> odb) throws MOSException
    {
        for (Trigger<T> trigger : world)
        {
            trigger.activate(t, odb);
        }
    }

    public void activateObject(T t, Odb<T> odb) throws MOSException
    {
        List<Trigger<T>> triggers = map.get(t.getClass());
        if (triggers == null)
        {
            for (Map.Entry<Class<?>, List<Trigger<T>>> entry : map.entrySet())
            {
                if (entry.getKey().isAssignableFrom(t.getClass()))
                {
                    triggers = entry.getValue();
                }
            }
        }
        if (triggers == null)
        {
            return;
        }
        for (Trigger<T> trigger : triggers)
        {
            trigger.activate(t, odb);
        }
    }
}
