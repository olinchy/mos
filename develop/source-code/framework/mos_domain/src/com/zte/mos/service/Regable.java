package com.zte.mos.service;

import com.zte.mos.exception.MOSException;

/**
 * Created by love on 16-1-23.
 */
public interface Regable
{
    String getID();
    void setID(String id)throws MOSException;
}
