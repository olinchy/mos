package com.zte.mos.util;

import com.zte.mos.exception.MOSException;

public interface Visitor<T>
{
    void visit(T t) throws MOSException;
}
