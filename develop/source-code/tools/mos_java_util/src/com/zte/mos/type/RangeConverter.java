package com.zte.mos.type;

import org.apache.commons.beanutils.converters.AbstractConverter;

/**
 * Created by olinchy on 15-10-14.
 */
public class RangeConverter extends AbstractConverter
{
    @Override
    protected <T> T convertToType(Class<T> aClass, Object o) throws Throwable
    {
        return (T) new Range(new RangeExp(String.valueOf(o)));
    }

    @Override
    protected Class<?> getDefaultType()
    {
        return Range.class;
    }
}
