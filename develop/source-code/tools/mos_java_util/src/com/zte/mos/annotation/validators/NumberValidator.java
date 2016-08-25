package com.zte.mos.annotation.validators;

import com.zte.mos.annotation.MoAttribute;
import com.zte.mos.annotation.ValidateException;
import com.zte.mos.type.Range;
import com.zte.mos.type.RangeExp;

/**
 * Created by olinchy on 2/16/15 for mosjava.
 */
public class NumberValidator extends BasicValidator
{
    @Override
    protected void validateData(MoAttribute attr, Object value) throws ValidateException
    {
        Long longValue = Long.parseLong(String.valueOf(value));
        check(attr, longValue);
    }

    protected <T extends Comparable> void check(MoAttribute attr, T num) throws ValidateException
    {
        if (attr.range().equals(""))
        {
            if (num.compareTo(attr.maximum()) > 0 || num.compareTo(attr.minimum()) < 0)
                throw new ValidateException(
                        attr.field() + " value is " + String.valueOf(
                                num) + " now, " + " should between " + String.valueOf(
                                attr.minimum()) + "-" + String.valueOf(attr.maximum()));
        }
        else
        {
            Range range = new Range(new RangeExp(attr.range()));
            if (!range.contains(String.valueOf(num)))
            {
                throw new ValidateException(
                        attr.field() + " value is " + String.valueOf(
                                num) + " now, " + " should in range " + attr.range());
            }
        }
    }
}
