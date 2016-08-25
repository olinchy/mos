package com.zte.mos.tools.databinding;

import com.zte.mos.annotation.FieldToAndFro;
import com.zte.mos.annotation.FieldToAndFroWithValidator;
import com.zte.mos.annotation.Validator;
import com.zte.mos.swinglib.binding.beans.Model;
import com.zte.mos.swinglib.binding.value.ValueModel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created by olinchy on 15-8-4.
 */
public class MoAdaptor extends Model implements ValueModel
{
    protected String propertyName;
    protected Bean bean;
    private FieldToAndFro toAndFro = new FieldToAndFroWithValidator()
    {
        @Override
        public Object fro(String fieldValue)
        {
            return fieldValue;
        }

        @Override
        public Object to(Object value)
        {
            return value;
        }

        @Override
        public Validator validator()
        {
            return null;
        }
    };

    public MoAdaptor(String propertyName, Bean bean)
    {
        this.propertyName = propertyName;
        this.bean = bean;

        addPropertyHandlerToBean(bean);
    }

    protected void addPropertyHandlerToBean(Bean bean)
    {
        bean.addPropertyChangeListener(new MoPropertyHandler());
    }

    @Override
    public Object getValue()
    {
        return toAndFro.fro(bean.getValue(propertyName) == null ? "" : bean.getValue(propertyName).toString());
    }

    @Override
    public void setValue(Object newValue)
    {
        bean.setValue(propertyName, toAndFro.to(newValue));
    }

    @Override
    public void addValueChangeListener(PropertyChangeListener listener)
    {
        this.addPropertyChangeListener(this.propertyName, listener);
    }

    @Override
    public void removeValueChangeListener(PropertyChangeListener listener)
    {
        this.removePropertyChangeListener(this.propertyName, listener);
    }

    public final void fireValueChange(Object oldValue, Object newValue, boolean isCheckIdentity)
    {
        firePropertyChange(this.propertyName, oldValue, newValue, isCheckIdentity);
    }

    public void setToAndFro(FieldToAndFro toAndFro)
    {
        if (toAndFro != null)
            this.toAndFro = toAndFro;
    }

    private class MoPropertyHandler implements PropertyChangeListener
    {

        @Override
        public void propertyChange(PropertyChangeEvent evt)
        {
            if (evt.getPropertyName().equals(propertyName))
            {
                fireValueChange(evt.getOldValue(), evt.getNewValue(), true);
            }
        }

    }

}
