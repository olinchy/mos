package com.zte.mos.annotation.types;

import com.zte.mos.annotation.FieldToAndFroWithValidator;
import com.zte.mos.annotation.Validator;
import com.zte.mos.annotation.Validators;

/**
 * Created by olinchy on 15-10-22.
 */
public class FloatType implements FieldToAndFroWithValidator
{
    @Override
    public Validator validator()
    {
        return Validators.Float;
    }

    @Override
    public Object fro(String fieldValue)
    {
        return Float.valueOf(fieldValue);
    }

    @Override
    public Object to(Object value)
    {
        return value;
    }
}
