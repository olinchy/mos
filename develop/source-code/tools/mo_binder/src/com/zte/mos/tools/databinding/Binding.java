package com.zte.mos.tools.databinding;

import com.zte.mos.annotation.FieldToAndFro;

/**
 * Created by olinchy on 15-8-6.
 */
public interface Binding
{
    Binding toAndFro(FieldToAndFro fieldToAndFro);

    Binding at(Bean model, String propertyName);
}
