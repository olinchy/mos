package com.zte.container.ext.exception;

import com.zte.mos.exception.MOSException;

/**
 * Created by ccy on 3/24/16.
 */
public class InvalidReferenceException extends MOSException
{
    public InvalidReferenceException(String keyword)
    {
        super(keyword);
    }

    public InvalidReferenceException()
    {

    }

}
