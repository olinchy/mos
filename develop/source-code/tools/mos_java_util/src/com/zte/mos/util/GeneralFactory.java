package com.zte.mos.util;

public interface GeneralFactory<T1, T2>
{
    void put(T1 typeCode, T2 impl);
}
