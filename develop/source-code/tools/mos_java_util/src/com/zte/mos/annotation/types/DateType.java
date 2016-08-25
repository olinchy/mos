package com.zte.mos.annotation.types;

import com.zte.mos.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by olinchy on 15-5-14.
 */
public class DateType implements FieldToAndFroWithValidator
{
    @Override
    public Object fro(String fieldValue)
    {
        if (fieldValue != null && !fieldValue.equals("") && !fieldValue.equals("null"))
        {
            if (!fieldValue.matches("([\\d]+)"))
            {
                try {
                    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fieldValue);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            return new Date(Long.parseLong(fieldValue));
        }
        else
            throw new RuntimeException("the input value " + fieldValue + " is null.");
    }

    @Override
    public Object to(Object value)
    {
        return ((java.util.Date) value).getTime();
    }

    @Override
    public Validator validator()
    {
        return Validators.Nothing;
    }
}
