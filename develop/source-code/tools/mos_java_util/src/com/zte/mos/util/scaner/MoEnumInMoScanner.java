package com.zte.mos.util.scaner;

import com.zte.mos.annotation.MoEnum;
import com.zte.mos.exception.MOSException;

import java.lang.reflect.Field;

/**
 * Created by olinchy on 16-8-23.
 */
public class MoEnumInMoScanner extends AbstractScanner
{
    @Override
    public void handle(Class clazz) throws MOSException
    {
        for (Field field : clazz.getFields())
        {
            if (MoEnum.class.isAssignableFrom(field.getType()))
            {
                MoEnumScanner.register((Class<? extends MoEnum>) field.getType());
            }
        }
    }
}
