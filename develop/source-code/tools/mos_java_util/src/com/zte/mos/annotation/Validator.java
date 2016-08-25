package com.zte.mos.annotation;

/**
 * Created by olinchy on 2/16/15 for mosjava.
 */
public interface Validator
{
    void validate(MoAttribute attr, Object value, boolean isSet) throws ValidateException;
}
