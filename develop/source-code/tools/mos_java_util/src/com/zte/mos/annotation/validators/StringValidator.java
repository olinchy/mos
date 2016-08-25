package com.zte.mos.annotation.validators;

import com.zte.mos.annotation.MoAttribute;
import com.zte.mos.annotation.ValidateException;

/**
 * Created by olinchy on 2/16/15 for mosjava.
 */
public class StringValidator extends BasicValidator
{
    @Override
    protected void validateData(MoAttribute attr, Object value) throws ValidateException
    {
        String strValue = String.valueOf(value);
        if (strValue.length() > attr.length())
            throw new ValidateException("field " + attr.field() + " is longer than limit " + attr.length() + " value is " + strValue);
    }

}
