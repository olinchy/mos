package com.zte.mos.type;

import org.apache.commons.beanutils.Converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zw on 15-11-17.
 */
public class DateConvertor implements Converter
{
    @Override
    public <T> T convert(Class<T> aClass, Object o)
    {
        boolean isString = o.toString().contains("-");
        if (isString){
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                return (T)format.parse(o.toString());
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }

        return (T)new Date(Long.parseLong(String.valueOf(o)));
    }
}
