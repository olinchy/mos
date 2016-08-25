package com.zte.mos.storage.stub;

import com.zte.mos.exception.PersistentException;
import com.zte.mos.persistent.PersistentContext;
import com.zte.mos.persistent.Record;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by olinchy on 1/6/15 for mosjava.
 */
public class StubContext implements PersistentContext
{
    public StubContext()
    {
    }

    @Override
    public Record createRecord(String entityName, String[] keyColumns)
    {
        return new Record(entityName, keyColumns);
    }

    @Override
    public List<Record> readRecord(String entityName, String filter) throws PersistentException
    {
        Record record = new Record(entityName, "");
        LinkedList<Record> records = new LinkedList<Record>();
        records.add(record);
        if (entityName.equalsIgnoreCase("Root"))
        {
            record.put("DN", "/");
        }
        else if (entityName.equalsIgnoreCase("R_Board"))
        {
            record.put("WBOARDTYPE", "rtua");
            record.put("UCSLOTNO", "1");
            record.put("UCSHELFNO", "1");
            record.put("UCFUNCTION", "0");
            record.put("DWSTATUS", "1");
        }
        else if (entityName.equalsIgnoreCase("R_Shelf"))
        {
            record.put("UCSHELFNO", "1");
            record.put("refShelf", "1");
        }
        else if (entityName.equalsIgnoreCase("Revision"))
        {
            record.put("revision", "0");
        }
        else if (entityName.equalsIgnoreCase("R_E1Port"))
        {
            records.clear();
            for (int i = 1; i < 17; i++)
            {
                Record temp = new Record(entityName, "PORTID");
                temp.put("UCSLOTNO", "1");
                temp.put("UCSHELFNO", "1");
                temp.put("UCPORTID", i);
                temp.put("PORTSTATE", "2");
                records.add(temp);
            }
        }
        return records;
    }

    @Override
    public boolean delete(Record record) throws PersistentException
    {
        return false;
    }

    @Override
    public boolean create(Record record) throws PersistentException
    {
        return false;
    }

    @Override
    public boolean update(Record record) throws PersistentException
    {
        return false;
    }

    @Override
    public PersistentContext spawn(String mosDN)
    {
        return new StubContext();
    }

    @Override
    public void clear()
    {

    }
}
