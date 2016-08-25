package com.zte.mos.persistent.database;

import com.zte.mos.persistent.Record;

import java.sql.*;
import java.util.HashMap;
import java.util.Vector;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by xy on 14-12-30.
 */
public class Table
{
    private static String DEFAULT_TABLE_OWNER = "MW_MOS";

    private String name;
    private String[] keys;
    private String tableOwner = DEFAULT_TABLE_OWNER;

    private final Vector<Column> columns = new Vector<Column>();

    public Table(String tableName, Connection connection) throws Exception
    {
        if (tableName.contains("."))
        {
            // sqlserver MW_CM.dbo.XXXX  MW_CM..XXXX
            // oracle MW_CM.XXXX
            String[] names = tableName.split("\\.");
            this.name = names[names.length - 1];
            this.tableOwner = names[0];
        }
        else
        {
            this.name = tableName;
        }
        initMeta(connection);
    }

    public String[] getKeys()
    {
        return keys;
    }

    private void initMeta(Connection connection)
    {
        StringBuffer buffer = new StringBuffer();

        try
        {
            DatabaseMetaData dbMetaData = connection.getMetaData();
            String schema = dbMetaData.getURL().contains("sqlserver") ? null : tableOwner;
            ResultSet resultSet = dbMetaData.getPrimaryKeys(null, schema, name);
            while (resultSet.next())
            {
                buffer.append(resultSet.getString("COLUMN_NAME").toUpperCase() + ";");
            }
            this.keys = buffer.toString().split(";");
            ResultSet columnRes = dbMetaData.getColumns(null, schema, name, null);
            while (columnRes.next())
            {
                this.columns.add(new Column(columnRes.getString("COLUMN_NAME").toUpperCase()));
            }
        }
        catch (SQLException e)
        {
            logger(this.getClass()).warn("", e);
        }
    }

    public Record readLine(ResultSet resultSet) throws Exception
    {
        HashMap<String, Object> valueMap = new HashMap<String, Object>();

        for (Column column : columns) {
            valueMap.put(column.name(), column.read(resultSet));
        }
        return new Record(name, valueMap);
    }

    public String name()
    {
        // todo return tableOwner + ".."  + name
        return name;
    }

}
