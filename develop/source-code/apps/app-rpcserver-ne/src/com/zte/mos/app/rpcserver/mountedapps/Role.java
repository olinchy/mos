package com.zte.mos.app.rpcserver.mountedapps;

import java.lang.annotation.*;

/**
 * Created by olinchy on 7/15/14 for MO_JAVA.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Role
{
    String value();
}
