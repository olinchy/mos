package com.zte.mos.annotation.types;

import com.zte.mos.annotation.FieldToAndFroWithValidator;
import com.zte.mos.annotation.Validator;

/**
 * Created by olinchy on 15-10-30.
 */
public class UserDefineType implements FieldToAndFroWithValidator
{
    @Override
    public Validator validator()
    {
        return null;
    }

    @Override
    public Object fro(String fieldValue)
    {
        return fieldValue;
    }

    @Override
    public Object to(Object value)
    {
        return value;
    }
}
