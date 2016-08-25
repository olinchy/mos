package com.odb.database.trigger;

import com.odb.database.Odb;
import com.zte.mos.domain.Group;
import com.zte.mos.exception.MOSException;

import java.util.TreeMap;

/**
 * Created by olinchy on 5/21/14 for MO_JAVA.
 */
@SuppressWarnings("unchecked")
public class TriggerHolder
{
    private TreeMap<TriggerTiming, TriggerGroup> map = new TreeMap<TriggerTiming, TriggerGroup>();
    private Trigger nullTrigger = new NullTriggerGroup();

    public Trigger trigger(TriggerTiming timming)
    {
        if (map.get(timming) == null)
        {
            return nullTrigger;
        }
        return map.get(timming);
    }

    public <T extends Group> void registerUniversal(TriggerTiming timming, Trigger<T> trigger)
    {
        TriggerGroup group = createGroup(timming);
        group.addWorldWide(trigger);

    }

    public <T extends Group> void register(Class<?> objClass, TriggerTiming timming, Trigger<T> trigger)
    {
        TriggerGroup group = createGroup(timming);

        group.put(objClass, trigger);
    }

    public void clear()
    {
        map.clear();
    }

    private TriggerGroup createGroup(TriggerTiming timming)
    {
        TriggerGroup group = map.get(timming);
        if (group == null)
        {
            group = new TriggerGroup();
            map.put(timming, group);
        }
        return group;
    }

    private class NullTriggerGroup<T extends Group> implements Trigger<T>
    {
        @Override
        public void activate(T t, Odb<T> odb) throws MOSException
        {
            // null object
        }

        @Override
        public void register(Odb<T> odb)
        {

        }
    }
}
