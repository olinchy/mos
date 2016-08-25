package com.zte.mos.annotation.types;

import com.zte.mos.annotation.*;

/**
 * Created by olinchy on 15-5-14.
 */
public class Word32Type implements FieldToAndFroWithValidator
{
    @Override
    public Object fro(String fieldValue)
    {
        if (fieldValue == null || fieldValue.equalsIgnoreCase("null"))
        {
            return null;
        }
        return new Long(fieldValue);
    }

    @Override
    public Object to(Object value)
    {
        return value;
    }

    @Override
    public Validator validator()
    {
        return Validators.Number;
    }
}
