package com.zte.mos.tools.databinding;

import com.zte.mos.swinglib.binding.adapter.Bindings;
import com.zte.mos.swinglib.binding.list.SelectionInList;
import com.zte.mos.swinglib.binding.value.ValueModel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by olinchy on 15-8-6.
 */
public class BindingComboBox extends JComboBox implements Bindable, From
{
    private ArrayList from = new ArrayList();
    private GeneralBinding binding = new GeneralBinding();

    public BindingComboBox() {
    }



    @Override
    public void active()
    {
        ValueModel selectItemModel = binding.adapter();

        SelectionInList selectionInList = new SelectionInList(
                new ValueModelAdaptor()
                {
                    @Override
                    public Object getValue()
                    {
                        return from;
                    }
                },
                selectItemModel);

        Bindings.bind(this, selectionInList);

        try
        {
            if ((selectItemModel.getValue() != null)
                    && (selectItemModel.getValue().toString().trim()
                    .equals("")))
            {
                if (from != null && from.size() != 0)
                {
                    this.setSelectedIndex(0);
                }
            }

        }
        catch (Exception ex)
        {
            logger(this.getClass()).warn("", ex);
        }
    }

    @Override
    public Binding bind()
    {
        return binding;
    }

    public Binding from(Bean fromBean, String fromPropertyName)
    {
        Object obj = fromBean.getValue(fromPropertyName);
        if (obj instanceof Iterable)
        {
            for (Object o : (Iterable) obj)
            {
                from.add(o);
            }
        }
        else if (obj.getClass().isArray())
        {
            from.addAll(Arrays.asList(obj));
            logger(this.getClass()).debug(obj.getClass() + " is array, need to put it into list");
        }

        return binding;
    }

    public void removeItem(Object o){
        from.remove(o);
        this.active();
    }

    public void addItem(Object o){
        from.remove(o);
        this.active();
    }

    public void insertItemAt(Object o, int index){
        from.add(index, o);
        this.active();
    }

    @Override
    public void removeAllItems()
    {
        from.clear();
        this.active();
    }
}
