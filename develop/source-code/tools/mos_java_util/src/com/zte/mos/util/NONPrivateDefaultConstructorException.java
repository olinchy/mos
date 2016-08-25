package com.zte.mos.util;

public class NONPrivateDefaultConstructorException extends Exception
{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private final String message;

    public NONPrivateDefaultConstructorException(String message)
    {
        this.message = message;
    }

    @Override
    public String getMessage()
    {
        return message;
    }

}
