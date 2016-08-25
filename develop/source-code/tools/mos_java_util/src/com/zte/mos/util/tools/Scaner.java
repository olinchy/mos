package com.zte.mos.util.tools;

import com.zte.mos.exception.MOSException;

public interface Scaner<T>
{

    void handle(Class<T> clazz) throws MOSException;

}
