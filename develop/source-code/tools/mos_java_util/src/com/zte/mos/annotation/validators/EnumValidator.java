package com.zte.mos.annotation.validators;

import com.zte.mos.annotation.MoAttribute;
import com.zte.mos.annotation.ValidateException;

/**
 * Created by olinchy on 2/16/15 for mosjava.
 */
public class EnumValidator extends BasicValidator
{
    @Override
    public void validateData(MoAttribute attr, Object value) throws ValidateException
    {
        String strValue = String.valueOf(value);
        try
        {
            validateEnum(attr, strValue);
        }
        catch (IllegalArgumentException e)
        {
            try
            {
                validateEnum(attr, "_" + strValue);
            }
            catch (IllegalArgumentException e1)
            {
                throw new ValidateException(e1.getMessage());
            }
        }
        catch (Exception e)
        {
            throw new ValidateException("enum field check failed!");
        }
    }

    private void validateEnum(MoAttribute attr, String s)
    {
        Enum.valueOf((Class<? extends Enum>) attr.enums(), s);
    }
}
