package com.zte.mos.annotation.validators;

import com.zte.mos.annotation.MoAttribute;
import com.zte.mos.annotation.ValidateException;
import com.zte.mos.annotation.Validator;

/**
 * Created by olinchy on 2/16/15 for mosjava.
 */
public abstract class BasicValidator implements Validator
{
    @Override
    public void validate(MoAttribute attr, Object value, boolean isSet) throws ValidateException
    {
        if (isSet)
        {
            isMutable(attr);
        }
        validateData(attr, value);
    }

    protected abstract void validateData(MoAttribute attr, Object value) throws ValidateException;

    private void isMutable(MoAttribute attr) throws ValidateException
    {
        if (!attr.mutable())
            throw new ValidateException("field " + attr.field() + " is not editable");
    }
}
