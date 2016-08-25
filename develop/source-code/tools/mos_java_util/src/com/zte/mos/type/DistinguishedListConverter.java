package com.zte.mos.type;

import com.zte.mos.exception.MOSException;
import com.zte.mos.util.DistinguishedList;

import java.util.Arrays;

/**
 * Created by olinchy on 15-6-2.
 */
public class DistinguishedListConverter extends SetConverter
{
    @Override
    protected <T> T convertToType(Class<T> type, Object value) throws MOSException
    {
        DistinguishedList lst = new DistinguishedList();
        lst.addAll(Arrays.asList(String.valueOf(value).split(SYMBOL)));
        return (T) lst;
    }

    @Override
    protected Class<?> getDefaultType()
    {
        return DistinguishedList.class;
    }
}
