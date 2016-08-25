package com.zte.mos.type;

import com.zte.mos.exception.MOSException;
import org.apache.commons.beanutils.converters.AbstractConverter;

import java.lang.reflect.Array;

/**
 * Created by luoqingkai on 15-4-22.
 */
public class IPV4AddressConvertor extends AbstractConverter
{
    @Override
    protected <T> T convertToType(Class<T> aClass, Object value) throws MOSException
    {
        if (value.toString().isEmpty()){
            return (T)new IPV4Address(value.toString());
        }
        Short[] ip = new Short[4];
        IPV4Address resIP;
        if (value.getClass().isArray())
        {
            for (int i = 0; i < 4; i++)
            {
                ip[i] = Array.getShort(value, i);
            }
            resIP = new IPV4Address(ip);
        } else
        {
            resIP = new IPV4Address(String.valueOf(value));
        }
        return (T) resIP;
    }

    @Override
    protected Class<?> getDefaultType()
    {
        return IPV4Address.class;
    }

    /**
     * Override this method to return the array directly,
     * because method in super class just return the 0's element
     *
     * @param value
     * @return
     */
    protected Object convertArray(Object value)
    {
        return value;
    }

}
