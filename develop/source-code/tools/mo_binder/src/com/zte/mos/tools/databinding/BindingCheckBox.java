package com.zte.mos.tools.databinding;

import com.zte.mos.annotation.FieldToAndFroWithValidator;
import com.zte.mos.annotation.Validator;
import com.zte.mos.swinglib.binding.adapter.Bindings;

import javax.swing.*;

/**
 * Created by olinchy on 15-8-13.
 */
public class BindingCheckBox extends JCheckBox implements Bindable
{
    private String selectValue;
    private String deselectValue;
    private GeneralBinding binding = new GeneralBinding();

    public BindingCheckBox(String selectValue, String deselectValue)
    {
        this.selectValue = selectValue;
        this.deselectValue = deselectValue;
    }

    public BindingCheckBox()
    {
    }

    @Override
    public void active()
    {
        if (selectValue != null && deselectValue != null)
        {
            Bindings.bind(this, binding.adapter(), selectValue, deselectValue);
        }
        else
        {
            binding.toAndFro(
                    new FieldToAndFroWithValidator()
                    {
                        @Override
                        public Object fro(String fieldValue)
                        {
                            if (fieldValue.equals("true") || fieldValue.equals("false"))
                                return Boolean.valueOf(fieldValue);
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
                    });
            Bindings.bind(this, binding.adapter());
        }
    }

    @Override
    public Binding bind()
    {
        return binding;
    }
}
