package com.zte.mos.tools.databinding;

import com.zte.mos.swinglib.validation.ValidationResultModel;

import java.beans.PropertyChangeListener;

/**
 * Created by olinchy on 15-8-4.
 */
public interface Bean
{
    void addPropertyChangeListener(PropertyChangeListener handler);

    Object getValue(String propertyName);

    void setValue(String propertyName, Object newValue);

    void removePropertyChangeListener(PropertyChangeListener x);

    ValidationResultModel getValidateResultModel(String propertyName);

    boolean hasError();
}
