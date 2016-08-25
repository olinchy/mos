package com.zte.mos.tools.databinding;

import com.zte.mos.message.Mo;
import com.zte.mos.swinglib.binding.beans.ExtendedPropertyChangeSupport;
import com.zte.mos.swinglib.validation.ValidationResult;
import com.zte.mos.swinglib.validation.ValidationResultModel;
import com.zte.mos.swinglib.validation.util.DefaultValidationResultModel;
import com.zte.mos.type.Pair;

import java.beans.PropertyChangeListener;
import java.util.HashMap;

/**
 * Created by olinchy on 15-8-4.
 */
public class MoBean implements Bean
{
    protected final ExtendedPropertyChangeSupport support = new ExtendedPropertyChangeSupport(
            this);
    private final Mo mo;
    private HashMap<String, ValidationResultModel> resModelMap = new HashMap<String, ValidationResultModel>();
    private HashMap<String, Validator> validatorHashMap = new HashMap<String, Validator>();

    public MoBean(Mo mo, Pair<String, ? extends Validator>... validators)
    {
        this.mo = mo;
        if (validators != null)
        {
            for (Pair<String, ? extends Validator> validator : validators)
            {
                validatorHashMap.put(validator.first(), validator.second());
            }
        }
    }

    public Object getValue(String propertyName)
    {
        return mo.get(propertyName);
    }


    public void setValue(String propertyName, Object value)
    {
        ValidationResult result = new ValidationResult();
        if (this.validatorHashMap.get(propertyName) != null)
        {
            result = validatorHashMap.get(propertyName).validate(value);
        }
        if (result.getErrors().size() == 0 && result.getWarnings().size() == 0)
        {
            Object oldValue = mo.get(propertyName);
            mo.setAttr(propertyName, value);
            support.firePropertyChange(propertyName, oldValue, value);
        }
        if (this.resModelMap.get(propertyName) != null)
            resModelMap.get(propertyName).setResult(result);
    }

    public void addPropertyChangeListener(PropertyChangeListener x)
    {
        support.addPropertyChangeListener(x);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener x)
    {
        support.removePropertyChangeListener(x);
    }

    @Override
    public ValidationResultModel getValidateResultModel(String propertyName)
    {
        if (resModelMap.get(propertyName) == null)
            resModelMap.put(propertyName, new DefaultValidationResultModel());
        return resModelMap.get(propertyName);
    }

    @Override
    public boolean hasError()
    {
        for (ValidationResultModel validationResultModel : resModelMap.values())
        {
            if (validationResultModel.hasErrors())
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString()
    {
        return "MoBean{" +
                "mo=" + mo +
                '}';
    }

    public Mo getMo()
    {
        return mo;
    }
}
