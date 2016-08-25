package com.zte.mos.message;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by olinchy on 15-5-8.
 */
public class ConfigData implements Serializable
{
    private HashMap<String, Object> mo = new HashMap<String, Object>();

    public HashMap<String, Object> getMo()
    {
        return mo;
    }

    public void setMo(HashMap<String, Object> mo)
    {
        this.mo = mo;
    }
}
