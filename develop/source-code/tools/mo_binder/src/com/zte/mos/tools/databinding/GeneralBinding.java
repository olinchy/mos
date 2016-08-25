package com.zte.mos.tools.databinding;

import com.zte.mos.annotation.FieldToAndFro;
import com.zte.mos.annotation.FieldToAndFroWithValidator;
import com.zte.mos.swinglib.binding.value.ValueModel;

/**
 * Created by olinchy on 15-8-10.
 */
public class GeneralBinding implements Binding
{
    private MoAdaptor adapter;

    public Binding toAndFro(FieldToAndFro fieldToAndFro)
    {
        adapter.setToAndFro(fieldToAndFro);
        return this;
    }

    @Override
    public Binding at(Bean model, String propertyName)
    {
        this.adapter = new MoAdaptor(propertyName, model);
        return this;
    }

    public ValueModel adapter()
    {
        return this.adapter;
    }

}
