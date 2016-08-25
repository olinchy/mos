package com.zte.mos.annotation.types;

import com.zte.mos.annotation.FieldToAndFroWithValidator;
import com.zte.mos.annotation.Validator;
import com.zte.mos.type.IPV4Address;

/**
 * Created by olinchy on 15-5-14.
 */
public class IPV4Type implements FieldToAndFroWithValidator
{
    @Override
    public Object fro(String fieldValue)
    {
        if (fieldValue == null || fieldValue.equalsIgnoreCase("NULL"))
            return null;
        return new IPV4Address(fieldValue);
    }

    @Override
    public Object to(Object value)
    {
        return java.lang.String.valueOf(value);
    }

    @Override
    public Validator validator()
    {
        return IPV4Address.validator();
    }
}
