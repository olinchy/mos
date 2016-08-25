package com.zte.mos.persistent.xml;

import com.zte.mos.persistent.Record;
import com.zte.mos.util.XmlJaxbHelper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement(name = "ROWDATA")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Table
{

    @XmlElement(name = "ROW")
    public List<Row> rows;

    public String name;
    public String path = "./";

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        if (rows != null)
        {
            for (Row row : rows)
            {
                builder.append(row.toString()).append("\n");
                builder.append("\n");
            }
        }

        return builder.toString();
    }

    public List<Record> allRecords()
    {
        List<Record> lst = new LinkedList<Record>();
        if (rows != null)
        {
            for (Row row : rows)
            {
                lst.add(row.toRecord(name));
            }
        }

        return lst;
    }

    public void remove(Record record)
    {
        if (this.rows != null)
        {
            this.rows.remove(new Row(record));
        }
    }

    public boolean write()
    {
        try
        {
            OutputStream out = new FileOutputStream(new File(path));
            XmlJaxbHelper.exportXmlJAXB(this, out);
        }
        catch (Exception e)
        {
            if (path.equals("./"))
            {
                path = path + name + ".xml";
                write();
            }
            else
            {
                return false;
            }
        }
        return true;
    }

    public void add(Record record)
    {
        if (this.rows == null)
        {
            rows = new ArrayList<Row>();
        }
        Row row = new Row(record);

        if (rows.contains(row))
        {
            this.remove(record);
        }
        this.rows.add(row);
    }

}
