package com.zte.mos.persistent.xml;

import com.zte.mos.exception.PersistentException;
import com.zte.mos.persistent.PersistentContext;
import com.zte.mos.persistent.Record;
import com.zte.mos.tools.Expression;
import com.zte.mos.tools.LiteMosListener;
import com.zte.mos.util.tools.Prop;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.zte.mos.util.basic.Logger.logger;

public class MoXmlPersistentContext implements PersistentContext
{
    SimDBLoader simDB = null;

    public MoXmlPersistentContext()
    {
    }

    MoXmlPersistentContext(String keyword)
    {
        simDB = new SimDBLoader(keyword);
        try
        {
            simDB.loadAll();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public Record createRecord(String entityName, String[] keyColumns)
    {
        return new Record(entityName, keyColumns);
    }

    @Override
    public List<Record> readRecord(String entityName, String filter) throws PersistentException
    {
        LinkedList<Record> fits = new LinkedList<Record>();

        Expression exp = new Expression(filter);

        List<Record> records = readAll(entityName);

        // todo
        logger(this.getClass()).info("reading " + entityName);

        if (records != null)
        {
            for (Record record : records)
            {
                if (exp.evaluate(new LiteMosListener(record)))
                {
                    fits.add(record);
                }
            }
        }
        return fits;
    }

    @Override
    public synchronized boolean delete(Record record) throws PersistentException
    {
        Table table = simDB.getTable(record.getTableName());
        table.remove(record);
        return table.write();
    }

    @Override
    public synchronized boolean create(Record record) throws PersistentException
    {
        Table table = simDB.getTable(record.getTableName());
        table.add(record);
        return table.write();
    }

    @Override
    public synchronized boolean update(Record record) throws PersistentException
    {
        Table table = simDB.getTable(record.getTableName());
        table.remove(record);
        table.add(record);
        return table.write();
    }

    @Override
    public PersistentContext spawn(String mosDN)
    {
        String storageKeyword = Prop.get("storage_keyword");
        String storagePath = Prop.get("storage_path");
        String storage = null;
        if (storageKeyword.contains("$dn"))
        {
            if (mosDN.equals("/"))
            {
                storage = storagePath;
            }
            else
            {
                storage = storagePath + File.separator + storageKeyword.replace("$dn", mosDN.replace("/", "_"));
            }
        }
        return new MoXmlPersistentContext(storage);
    }

    @Override
    public void clear()
    {
        simDB.clear();
    }

    private List<Record> readAll(String entityName) throws PersistentException
    {
        List<Record> list = new ArrayList<Record>();

        Table table = simDB.getTable(entityName.toUpperCase());

        if (table != null)
        {
            list = table.allRecords();
        }

        return list;
    }

}
