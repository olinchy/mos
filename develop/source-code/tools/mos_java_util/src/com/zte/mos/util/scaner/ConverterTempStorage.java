package com.zte.mos.util.scaner;

import com.zte.mos.type.IPV4Address;
import com.zte.mos.type.Pair;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

import java.util.ArrayList;

/**
 * Created by olinchy on 15-6-9.
 */
public class ConverterTempStorage
{
    private ArrayList<Pair<Converter, Class<?>>> lst = new ArrayList<Pair<Converter, Class<?>>>();

    private ConverterTempStorage()
    {
    }

    public void register(Converter converter, Class<?> clazz)
    {
        ConvertUtils.register(converter, clazz);
        this.lst.add(new Pair<Converter, Class<?>>(converter, clazz));
    }

    public synchronized void registerAgain()
    {
        for (Pair<Converter, Class<?>> pair : lst)
        {
            ConvertUtils.register(pair.first(), pair.second());
        }
        this.lst.clear();
    }

    public <T> T convert(Object fieldValue, Class<T> type)
    {
        if (ConvertUtils.lookup(IPV4Address.class) == null)
        {
            registerAgain();
        }
        try
        {
            Object res = ConvertUtils.convert(fieldValue, type);
            return (T) res;
        }
        catch (Throwable e)
        {
            return (T) fieldValue;
        }
    }
}
