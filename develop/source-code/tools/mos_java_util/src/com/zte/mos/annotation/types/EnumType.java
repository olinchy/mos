package com.zte.mos.annotation.types;

import com.zte.mos.annotation.FieldToAndFroWithValidator;
import com.zte.mos.annotation.Validator;
import com.zte.mos.annotation.Validators;

/**
 * Created by olinchy on 15-5-14.
 */
public class EnumType implements FieldToAndFroWithValidator
{
    @Override
    public Object fro(String fieldValue)
    {
        if (fieldValue == null || fieldValue.equalsIgnoreCase("NULL"))
            return null;
        return fieldValue;
    }

    @Override
    public Object to(Object value)
    {
        return String.valueOf(value);
    }

    @Override
    public Validator validator()
    {
        return Validators.ENUM;
    }
}
