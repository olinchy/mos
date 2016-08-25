package com.zte.mos.util.scaner;

import com.zte.mos.annotation.MoEnum;
import com.zte.mos.exception.MOSException;

import java.lang.reflect.Array;

import static com.zte.mos.util.Singleton.getInstance;
import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by luoqingkai on 14-10-23.
 */
public class MoEnumScanner extends AbstractScanner<MoEnum>
{
    public static <T extends MoEnum> void register(Class<T> clazz)
    {
        try
        {
            getInstance(ConverterTempStorage.class).register(new MoEnumTypeConverter<T>(), clazz);
            getInstance(ConverterTempStorage.class).register(
                    new MoEnumArrayConverter<T>(), Array.newInstance(clazz, 0).getClass());
        }
        catch (MOSException e)
        {
            logger(MoEnumScanner.class).warn("", e);
        }
    }

    public void handle(Class<MoEnum> clazz)
    {
        if (MoEnum.class.isAssignableFrom(clazz))
        {
            register(clazz);
        }
    }
}
