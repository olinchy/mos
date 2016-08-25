package com.zte.mos.type;

import java.io.Serializable;

/**
 * Created by olinchy on 16-3-17.
 */
public class ObjHasIpV4 implements Serializable
{
    public ObjHasIpV4()
    {
    }

    public ObjHasIpV4(String s)
    {
        this.ip = new IPV4Address(s);
    }

    public IPV4Address ip;
}
