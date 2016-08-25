package com.zte.mos.tools.databinding;

import com.zte.mos.swinglib.validation.ValidationResult;

/**
 * Created by olinchy on 15-8-14.
 */
public interface Validator
{
    ValidationResult validate(Object value);
}
