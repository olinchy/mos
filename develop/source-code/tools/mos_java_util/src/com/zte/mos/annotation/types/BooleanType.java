package com.zte.mos.annotation.types;

import com.zte.mos.annotation.FieldToAndFroWithValidator;
import com.zte.mos.annotation.Validator;

/**
 * Created by olinchy on 15-9-23.
 */
public class BooleanType implements FieldToAndFroWithValidator
{
    @Override
    public Validator validator()
    {
        return null;
    }

    @Override
    public Object fro(String fieldValue)
    {
        return Boolean.valueOf(fieldValue);
    }

    @Override
    public Object to(Object value)
    {
        return value;
    }
}
