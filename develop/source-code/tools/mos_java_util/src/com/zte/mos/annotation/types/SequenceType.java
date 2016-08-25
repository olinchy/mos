package com.zte.mos.annotation.types;

import com.zte.mos.annotation.Validator;
import com.zte.mos.annotation.Validators;

/**
 * Created by olinchy on 15-5-14.
 */
public class SequenceType extends SetType
{
    @Override
    public Validator validator()
    {
        return Validators.Seq;
    }
}
