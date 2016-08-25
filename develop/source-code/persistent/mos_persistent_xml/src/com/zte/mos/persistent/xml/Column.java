package com.zte.mos.persistent.xml;

import javax.xml.bind.annotation.XmlAttribute;

public class Column
{
    public Column()
    {
    }

    public Column(String name, String value)
    {
        this.name = name;
        this.value = value;
    }

    @XmlAttribute(name = "name")
    public String name;
    @XmlAttribute(name = "value")
    public String value;
}
