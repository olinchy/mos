package com.zte.mos.msg.impl.snmp.tofile;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by olinchy on 16-1-28.
 */
public class Field
{
    public Field()
    {
    }

    public Field(String name, String value)
    {
        this.name = name;
        this.value = value;
    }

    @XmlAttribute(name = "name")
    public String name;
    @XmlAttribute(name = "value")
    public String value;
}
