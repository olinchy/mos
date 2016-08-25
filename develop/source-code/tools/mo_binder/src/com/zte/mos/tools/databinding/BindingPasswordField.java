package com.zte.mos.tools.databinding;

import com.zte.mos.swinglib.binding.adapter.Bindings;

import javax.swing.*;

public class BindingPasswordField extends JPasswordField implements Bindable
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
