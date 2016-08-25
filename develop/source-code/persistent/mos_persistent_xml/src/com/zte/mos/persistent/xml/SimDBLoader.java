package com.zte.mos.persistent.xml;

import com.zte.mos.util.tools.ReadXML;

import java.io.*;
import java.util.HashMap;

public class SimDBLoader
{
    private final HashMap<String, Table> tables = new HashMap<String, Table>();
    private String storagePath;

    public SimDBLoader(
            String path)
    {
        setStoragePath(path);
    }

    public Table getTable(String tableName)
    {
        Table table = tables.get(tableName.toUpperCase());
        if (table == null)
        {
            table = new Table();
            table.name = tableName.toUpperCase();
            table.path = storagePath + File.separator + table.name + ".xml";
            tables.put(table.name, table);
        }
        return table;
    }

    public void loadAll() throws Exception
    {
        File[] files = new File(storagePath).listFiles(new FileFilter()
        {
            @Override
            public boolean accept(File pathname)
            {
                return pathname.isFile() && pathname.getName().endsWith(".xml");
            }

        });

        for (File file : files)
        {
            Table table = loadTable(file);
            tables.put(table.name, table);
        }
    }

    public void clear()
    {
        File storage = new File(storagePath);
        File[] files = storage.listFiles(new FileFilter()
        {
            @Override
            public boolean accept(File pathname)
            {
                return pathname.isFile() && pathname.getName().endsWith(".xml");
            }

        });

        for (File file : files)
        {
            file.delete();
        }
        storage.delete();

        this.tables.clear();
    }

    private void setStoragePath(String path)
    {
        File storagePath = new File(path);
        if (!storagePath.exists())
        {
            storagePath.mkdirs();
        }
        this.storagePath = storagePath.getAbsolutePath();
    }

    private Table loadTable(File file) throws FileNotFoundException
    {
        InputStream is = new FileInputStream(file);
        Table table = null;
        try
        {
            table = ReadXML.parseByJaxb(is, Table.class);
        }
        catch (Throwable throwable)
        {
            throwable.printStackTrace();
            return null;
        }

        table.name = file.getName().toUpperCase().replaceAll("\\.XML", "");
        table.path = file.getAbsolutePath();

        return table;

    }
}
