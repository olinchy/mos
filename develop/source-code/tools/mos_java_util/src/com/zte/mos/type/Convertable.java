package com.zte.mos.type;

import org.apache.commons.beanutils.Converter;

/**
 * Created by luoqingkai on 15-4-23.
 */
public interface Convertable
{
    Converter getConverter();
}
