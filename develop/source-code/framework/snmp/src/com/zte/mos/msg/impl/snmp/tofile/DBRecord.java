package com.zte.mos.msg.impl.snmp.tofile;

import com.zte.mos.domain.or.ORField;
import com.zte.mos.domain.or.ORRow;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;

/**
 * Created by olinchy on 16-1-28.
 */
public class DBRecord
{
    @XmlAttribute(name = "table")
    public String table;
    @XmlAttribute(name = "opType")
    public String opType;
    @XmlElement(name = "FIELD")
    public ArrayList<Field> fields = new ArrayList<Field>();

    public void init(ORRow row)
    {
        for (ORField f : row.columns)
        {
            fields.add(new Field(f.name, f.value));
        }
    }
}
