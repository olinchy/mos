package com.zte.mos.exception;

/**
 * Created by olinchy on 6/22/14 for MO_JAVA.
 */
public class NullMoException extends MOSException
{
    public NullMoException(String s)
    {
        //message = s;
        super(new ErrorCode(ErrorCodeGetter.NULL_MO_ERROR), s);
    }
}
