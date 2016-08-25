package com.zte.mos.annotation.types;

import com.zte.mos.annotation.FieldToAndFroWithValidator;
import com.zte.mos.annotation.MoAttribute;
import com.zte.mos.annotation.ValidateException;
import com.zte.mos.annotation.Validator;
import com.zte.mos.annotation.validators.BasicValidator;
import com.zte.mos.type.Range;
import com.zte.mos.type.RangeExp;

/**
 * Created by olinchy on 15-10-14.
 */
public class RangeType extends BasicValidator implements FieldToAndFroWithValidator
{
    @Override
    public Validator validator()
    {
        return this;
    }

    @Override
    public Object fro(String fieldValue)
    {
        return new Range(new RangeExp(fieldValue));
    }

    @Override
    public Object to(Object value)
    {
        return value;
    }

    @Override
    protected void validateData(MoAttribute attr, Object value) throws ValidateException
    {
    }
}
