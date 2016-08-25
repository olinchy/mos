package com.zte.mos.persistent.xml;

import com.zte.mos.persistent.Record;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

public class Row
{
    private String[] primaryKeys;

    @XmlElement(name = "column")
    public List<Column> columns;

    @SuppressWarnings("unused")
    public Row()
    {
        primaryKeys = new String[0];
    }

    public Row(Record record)
    {
        columns = new ArrayList<Column>();
        for (Entry<String, Object> entry : record.all())
        {
            Column ele = new Column(entry.getKey(), String.valueOf(entry.getValue()));

            columns.add(ele);
        }
        this.primaryKeys = record.primaryKeys();
    }

    @Override
    public String toString()
    {
        StringBuilder buffer = new StringBuilder();
        for (Column ele : columns)
            buffer.append("    ").append(ele.name).append(":").append(ele.value).append("\n");
        return buffer.toString();
    }

    public Record toRecord(String name)
    {
        Record rec = new Record(name, primaryKeys);
        for (Column ele : this.columns)
        {
            rec.add(ele.name, ele.value);
        }
        return rec;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Row)
        {
            Row that = (Row) obj;
            if (this.primaryKeys == null || this.primaryKeys.length == 0)
            {
                this.primaryKeys = that.primaryKeys == null ? new String[0] : that.primaryKeys;
            }

            for (String key : this.primaryKeys)
            {
                Column tarCol = that.getColumn(key);
                Column thisCol = this.getColumn(key);

                if (tarCol != null && thisCol != null && !thisCol.value.equals(tarCol.value))
                {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        return columns != null ? columns.hashCode() : 0;
    }

    private Column getColumn(String name)
    {
        for (Column column : columns)
        {
            if (column.name.equalsIgnoreCase(name))
            {
                return column;
            }
        }
        return null;
    }

}
