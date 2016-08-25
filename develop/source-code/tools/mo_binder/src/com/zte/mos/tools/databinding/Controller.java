package com.zte.mos.tools.databinding;

/**
 * Created by olinchy on 15-8-6.
 */
public interface Controller
{
    Bean getModel(String modelName);

    void commit();

}
