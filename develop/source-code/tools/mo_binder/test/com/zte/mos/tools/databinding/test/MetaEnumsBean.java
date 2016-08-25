package com.zte.mos.tools.databinding.test;

import com.zte.mos.message.MoMetaClass;
import com.zte.mos.swinglib.validation.ValidationResultModel;
import com.zte.mos.tools.databinding.Bean;

import java.beans.PropertyChangeListener;

/**
 * Created by olinchy on 15-8-10.
 */
public class MetaEnumsBean implements Bean
{
    private final MoMetaClass meta;

    public MetaEnumsBean(MoMetaClass moMetaClass)
    {
        this.meta = moMetaClass;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener handler)
    {
    }

    @Override
    public Object getValue(String propertyName)
    {
        return meta.enums.get(propertyName);
    }

    @Override
    public void setValue(String propertyName, Object newValue)
    {
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener x)
    {
    }

    @Override
    public ValidationResultModel getValidateResultModel(String propertyName)
    {
        return null;
    }

    @Override
    public boolean hasError()
    {
        return false;
    }
}
