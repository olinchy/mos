package com.zte.mos.service;

import com.zte.mos.exception.MOSException;

/**
 * Created by luoqingkai on 15-3-31.
 */
public class NoSuchTransException extends MOSException
{
    public NoSuchTransException() {
    }

    public NoSuchTransException(Throwable cause) {
        super(cause);
    }

    public NoSuchTransException(String message) {
        super(message);
    }
}
