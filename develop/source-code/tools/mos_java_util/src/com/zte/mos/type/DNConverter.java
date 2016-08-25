package com.zte.mos.type;

import com.zte.mos.domain.DN;
import org.apache.commons.beanutils.converters.AbstractConverter;

/**
 * Created by olinchy on 15-6-3.
 */
public class DNConverter extends AbstractConverter
{
    @Override
    protected <T> T convertToType(Class<T> aClass, Object o) throws Throwable
    {
        return (T) new DN(String.valueOf(o));
    }

    @Override
    protected Class<?> getDefaultType()
    {
        return DN.class;
    }
}
