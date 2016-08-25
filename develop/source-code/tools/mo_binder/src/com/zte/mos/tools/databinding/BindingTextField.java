package com.zte.mos.tools.databinding;

import com.zte.mos.swinglib.binding.adapter.Bindings;

import javax.swing.*;

/**
 * Created by olinchy on 15-8-4.
 */
public class BindingTextField extends JTextField implements Bindable
{
    GeneralBinding binding = new GeneralBinding();

    public void active()
    {
        Bindings.bind(this, binding.adapter(), false);
    }

    @Override
    public Binding bind()
    {
        return this.binding;
    }

}
