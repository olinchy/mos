package com.zte.mos.util.scaner;

import com.zte.mos.exception.MOSException;
import com.zte.mos.util.tools.JsonUtil;
import org.apache.commons.beanutils.Converter;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by olinchy on 16-8-3.
 */
public class MoEnumArrayConverter<T> implements Converter
{
    @Override
    public <T> T convert(Class<T> aClass, Object o)
    {
        try
        {
            return JsonUtil.toObject(JsonUtil.toString(o), aClass);
        }
        catch (Throwable e)
        {
            logger(this.getClass()).warn("converting " + String.valueOf(o) + " to " + aClass + " caught exception!", e);
        }
        return null;
    }
}
