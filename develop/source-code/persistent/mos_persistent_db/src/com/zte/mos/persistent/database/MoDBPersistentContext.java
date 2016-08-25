package com.zte.mos.persistent.database;

import com.zte.mos.exception.*;
import com.zte.mos.persistent.*;
import com.zte.mos.persistent.database.connectionpool.ConnectionPoolManager;
import org.apache.commons.beanutils.ConvertUtils;

import java.sql.*;
import java.util.*;
import java.util.Map.Entry;

import static com.zte.mos.util.Singleton.getInstance;
import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by xy on 14-12-26.
 */
public class MoDBPersistentContext implements PersistentContext
{

    private String keyword = null;

    MoDBPersistentContext(String keyWords)
    {
        this.keyword = keyWords;
    }

    public MoDBPersistentContext()
    {

    }

    @Override
    public List<Record> readRecord(String entityName, String filter) throws PersistentException
    {
        LinkedList<Record> fits;
        try
        {
            logger(this.getClass()).info("reading " + entityName + " of " + keyword);
            fits = new LinkedList<Record>();
            Connection connection = getInstance(ConnectionPoolManager.class).getConnection();
            Statement statement = connection.createStatement();
            String sql = null;
            try
            {
                entityName = entityName.toUpperCase();
                Table table = getInstance(TableMetas.class).get(entityName, connection);
                sql = "select * from " + table.name() + " where dn like " + "'%" + keyword + "%'";

                if (filter != null && !filter.equals(""))
                {
                    sql += " and " + filter;
                }
                ResultSet resultSet = statement.executeQuery(sql);
                while (resultSet.next())
                {
                    fits.add(formatRecord(table.readLine(resultSet)));
                }
            }
            catch (SQLException e)
            {
                logger(this.getClass()).error("execute sql : " + sql + " failed!", e);
            }
            finally
            {
                if (statement != null)
                {
                    statement.close();
                }
                getInstance(ConnectionPoolManager.class).close(connection);
            }
        }
        catch (Exception e)
        {
            throw new ReadPersistentException(entityName, e);
        }
        return fits;
    }

    @Override
    public void clear()
    {
        try
        {
            Connection connection = getInstance(ConnectionPoolManager.class).getConnection();
            // delete from table.name() where neid = neid
            getInstance(TableMetas.class).clear(keyword, connection);
            connection.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public PersistentContext spawn(String keyword)
    {
        return new MoDBPersistentContext(keyword);
    }

    /////////////////////////////////////////////////// no need for now
    @Override
    public Record createRecord(String entityName, String[] keyColumns)
    {
        return new Record(entityName, keyColumns);
    }

    @Override
    public boolean delete(Record record) throws PersistentException
    {
        return execute(mkDeleteSql(record), record.getTableName());
    }

    @Override
    public boolean create(Record record) throws PersistentException
    {
        return execute(mkCreateSql(record), record.getTableName());
    }

    @Override
    public boolean update(Record record) throws PersistentException
    {
        return execute(mkUpdateSql(record), record.getTableName());
    }

    private String mkCreateSql(Record record)
    {
        StringBuffer buffer = new StringBuffer(1024);
        String columns = "";
        String values = "";
        buffer.append("insert into " + record.getTableName() + "(");


        for (Entry<String, Object> entry : record.all())
        {
            if (entry.getValue() == null)
            {
                continue;
            }
            columns += entry.getKey() + ",";
            if (entry.getKey().equalsIgnoreCase("dn"))
            {
                values += ("'" + dnToDb(String.valueOf(entry.getValue())) + "'");
            }
            else
            {
                values += format(entry.getValue());
            }
            values += ",";
        }

        buffer.append(columns.substring(0, columns.length() - 1) + " )" + " values( " + values
                .substring(0, values.length() - 1) + " )");

        return buffer.toString();
    }

    private String mkUpdateSql(Record record)
    {
        StringBuffer buffer = new StringBuffer(1024);
        buffer.append("update " + record.getTableName() + " set ");

        for (Entry<String, Object> entry : record.all())
        {
            if (!entry.getKey().equalsIgnoreCase("dn"))
                buffer.append(entry.getKey() + " = " + format(entry.getValue()) + " ,");
        }
        buffer.deleteCharAt(buffer.length() - 1);

        buffer.append(" where ");

        for (String key : record.primaryKeys())
        {
            buffer.append(key + " = ");
            if (key.equalsIgnoreCase("dn"))
            {
                buffer.append("'" + dnToDb(String.valueOf(record.get(key))) + "'");
            }
            else
            {
                buffer.append(format(record.get(key)));
            }
            buffer.append(",");
        }
        buffer.deleteCharAt(buffer.length() - 1);
        return buffer.toString();
    }

    private String mkDeleteSql(Record record)
    {
        StringBuffer buffer = new StringBuffer(1024);
        buffer.append("delete from " + record.getTableName() + " where ");

        for (String key : record.primaryKeys())
        {
            buffer.append(key + " = ");
            if (key.equalsIgnoreCase("dn"))
            {
                buffer.append("'" + dnToDb(String.valueOf(record.get(key))) + "'");
            }
            else
            {
                buffer.append(format(record.get(key)));
            }
            buffer.append(",");
        }
        buffer.deleteCharAt(buffer.length() - 1);
        return buffer.toString();
    }

    private boolean execute(String sql, String tableName) throws PersistentException
    {
        try
        {
            Connection connection = getInstance(ConnectionPoolManager.class).getConnection();
            Statement statement = connection.createStatement();
            getInstance(TableMetas.class).addTable(tableName, connection);
            try
            {
                statement.executeUpdate(sql);
            }
            catch (SQLException ex)
            {
                logger(this.getClass()).error("execute sql : " + sql + " failed!", ex);
            }
            finally
            {
                if (statement != null)
                {
                    statement.close();
                }
                getInstance(ConnectionPoolManager.class).close(connection);
            }
        }
        catch (Exception e)
        {
            throw new ExecuteSqlException(sql, e);
        }

        return true;

    }

    private String format(Object value)
    {
        if (is_digit(value))
        {
            return String.valueOf(value);
        }
        return "'" + ConvertUtils.convert(value) + "'";
    }

    private String dnToDb(String value)
    {
        if (!keyword.equalsIgnoreCase("/"))
        {
            return this.keyword + value;
        }
        return value;
    }

    private String dnFromDb(String value)
    {
        if (!keyword.equalsIgnoreCase("/"))
        {
            return value.replace(this.keyword, "");
        }
        return value;
    }

    private Record formatRecord(Record record)
    {
        record.put("dn", dnFromDb(String.valueOf(record.get("DN"))));
        return record;
    }

    private boolean is_digit(Object value)
    {
        try
        {
            Number num = (Number) value;
            return true;
        }
        catch(Throwable ex)
        {
            return false;
        }
    }
}
