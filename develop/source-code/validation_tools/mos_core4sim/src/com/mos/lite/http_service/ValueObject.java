package com.mos.lite.http_service;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by ruitao on 16-7-28.
 */
public class ValueObject implements Serializable
{
    public ValueObject(HashMap<String, Object> mo)
    {
        this.mo = mo;
    }

    public HashMap<String, Object> mo;
    public int result = 0;
}
