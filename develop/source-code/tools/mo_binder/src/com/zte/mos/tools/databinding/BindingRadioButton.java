package com.zte.mos.tools.databinding;

import com.zte.mos.swinglib.binding.adapter.Bindings;

import javax.swing.*;

/**
 * Created by olinchy on 15-8-13.
 */
public class BindingRadioButton extends JRadioButton implements Bindable
{
    private final Object selectValue;
    private GeneralBinding binding = new GeneralBinding();

    public BindingRadioButton(Object selectValue)
    {
        this.selectValue = selectValue;
    }

    @Override
    public void active()
    {
        Bindings.bind(this, binding.adapter(), selectValue);
    }

    @Override
    public Binding bind()
    {
        return binding;
    }
}
