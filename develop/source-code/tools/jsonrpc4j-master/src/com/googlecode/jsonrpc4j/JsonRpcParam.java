package com.googlecode.jsonrpc4j;

import java.lang.annotation.*;

/**
 * Annotation for annotating service parameters as
 * JsonRpc params by name.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface JsonRpcParam
{

    /**
     * The parameter's name.
     */
    String value();

}
