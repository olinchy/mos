package com.zte.mos.service.cmd;

import com.zte.mos.annotation.MoAttribute;
import com.zte.mos.annotation.ValidateException;
import com.zte.mos.annotation.Validator;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.message.Mo;

import java.lang.reflect.Field;

/**
 * Created by olinchy on 2/16/15 for mosjava.
 */
public class MOValidator
{
    public static void validate(Class<? extends ManagementObject> meta, Mo mo, boolean isSet) throws ValidateException
    {
        for (Field field : meta.getFields())
        {
            if (field.isAnnotationPresent(MoAttribute.class))
            {
                if (mo.get(field.getName()) != null)
                {
                    MoAttribute attr = field.getAnnotation(MoAttribute.class);
                    Validator validator;
                    if ((validator = attr.type().validator()) != null)
                        validator.validate(attr, mo.get(field.getName()), isSet);
                }
            }
        }
    }
}
