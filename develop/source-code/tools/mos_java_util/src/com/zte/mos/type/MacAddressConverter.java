package com.zte.mos.type;

import org.apache.commons.beanutils.converters.AbstractConverter;

/**
 * Created by ccy on 12/16/15.
 */
public class MacAddressConverter extends AbstractConverter
{
    @Override
    protected <T> T convertToType(Class<T> aClass, Object o) throws Throwable
    {
        return (T) new MacAddress(String.valueOf(o));
    }

    @Override
    protected Class<?> getDefaultType()
    {
        return MacAddress.class;
    }

}
