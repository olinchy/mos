package com.zte.mos.annotation;

/**
 * Created by ccy on 6/4/15.
 */
public class CharType implements FieldToAndFroWithValidator
{
    @Override
    public Object fro(String fieldValue)
    {
        if (fieldValue == null || fieldValue.equalsIgnoreCase("null"))
        {
            return null;
        }
        else if (!fieldValue.matches("[\\d]+|-[\\d]+"))
        {
            return fieldValue;
        }
        return new Byte(fieldValue);
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
