package com.zte.mos.annotation;

/**
 * Created by olinchy on 2/3/15 for mosjava.
 */
public interface FieldToAndFroWithValidator extends FieldToAndFro
{
    Validator validator();
}
