package com.zte.mos.annotation.types;

import com.zte.mos.annotation.FieldToAndFroWithValidator;
import com.zte.mos.annotation.MoAttribute;
import com.zte.mos.annotation.ValidateException;
import com.zte.mos.annotation.Validator;
import com.zte.mos.annotation.validators.BasicValidator;
import com.zte.mos.type.MacAddress;

/**
 * Created by olinchy on 15-5-14.
 */
public class MacAddrType extends BasicValidator implements FieldToAndFroWithValidator
{
    @Override
    public Object fro(String fieldValue)
    {
        return new MacAddress(fieldValue);
    }

    @Override
    public Object to(Object value)
    {
        return value;
    }

    @Override
    public Validator validator()
    {
        return this;
    }

    @Override
    protected void validateData(MoAttribute attr, Object value) throws ValidateException
    {

    }
}
