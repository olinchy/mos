package com.zte.mos.annotation;

import com.zte.mos.msg.MsgMode;
import com.zte.mos.msg.SupportedProtocol;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.zte.mos.msg.MsgMode.Single;
import static com.zte.mos.msg.SupportedProtocol.RPC;

/**
 * Created by luoqingkai on 14-10-18.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Imaged
{
    SupportedProtocol protocol() default RPC;
    MsgMode msgMode() default Single;
    float mosVersion() default 1.2f;
}
