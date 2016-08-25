package com.zte.mos.type;

import com.zte.mos.exception.MOSException;
import org.apache.commons.beanutils.converters.AbstractConverter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by olinchy on 3/10/15 for mosjava.
 */
public class SetConverter extends AbstractConverter
{
    static final String SYMBOL = ",";

    @Override
    protected <T> T convertToType(Class<T> type, Object value) throws MOSException
    {
        Set set = new HashSet();
        set.addAll(Arrays.asList(String.valueOf(value).split(SYMBOL)));
        return type.cast(set);
    }

    @Override
    protected Class<?> getDefaultType()
    {
        return Set.class;
    }
}
