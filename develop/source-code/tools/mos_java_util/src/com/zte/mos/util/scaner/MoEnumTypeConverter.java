package com.zte.mos.util.scaner;

import com.zte.mos.annotation.MoEnum;
import com.zte.mos.exception.MOSException;
import org.apache.commons.beanutils.converters.AbstractConverter;

import java.lang.reflect.Field;

/**
 * Created by luoqingkai on 14-10-23.
 */
public final class MoEnumTypeConverter<T extends MoEnum> extends
        AbstractConverter
{
    public MoEnumTypeConverter()
    {

    }

    public MoEnumTypeConverter(Object defaultValue)
    {
        super(defaultValue);
    }

    @SuppressWarnings({"unchecked", "hiding"})
    @Override
    protected <T> T convertToType(Class<T> type, Object value) throws MOSException
    {

        for (Field f : type.getFields())
        {
            try
            {
                MoEnum enumObj = (MoEnum) f.get(type.getClass());
                try
                {
                    MoEnum enu = enumObj.valuesOf(String.valueOf(value).trim());
                    if (enu != null)
                    {
                        return (T) enu;
                    }
                }
                catch (Exception e)
                {
                    if (enumObj.value().equals(value.toString().trim()))
                    {
                        return (T) enumObj;
                    }
                }
            }
            catch (IllegalAccessException e)
            {
                throw new MOSException(e);
            }
        }

        return null;
    }

    @Override
    protected Class<?> getDefaultType()
    {
        Class<?> clazz = null;
        return clazz;
    }
}
