package com.zte.mos.domain;

import com.zte.mos.type.Pair;

/**
 * Created by olinchy on 15-10-29.
 */
public class AfterGetters
{
    private AfterGetters()
    {
    }

    private MappingHolder<AfterGetter> mappingHolder = new MappingHolder<AfterGetter>();

    public void add(Class clazz, AfterGetter getter)
    {
        mappingHolder.mapping(clazz, getter);
    }

//    public <T> AfterGetter get(Class<T> clazz)
//    {
//        return this.mappingHolder.get(clazz);
//    }

    public <T> AfterGetter get(Class<T> clazz, Pair<String, String> forWhat)
    {
        return this.mappingHolder.get(clazz, forWhat);
    }
}
