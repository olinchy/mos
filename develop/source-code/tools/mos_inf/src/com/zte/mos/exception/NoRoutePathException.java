package com.zte.mos.exception;

/**
 * Created by olinchy on 15-6-26.
 */
public class NoRoutePathException extends MOSException
{
    public NoRoutePathException(String dn)
    {
        super(new ErrorCode(ErrorCodeGetter.NO_ROUTE_PATH_ERROR), "no route path: " + dn);
    }
}
