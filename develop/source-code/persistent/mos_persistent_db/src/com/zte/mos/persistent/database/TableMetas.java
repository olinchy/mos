package com.zte.mos.persistent.database;

import java.sql.Connection;
import java.sql.Statement;
import java.util.HashMap;

/**
 * Created by xy on 14-12-30.
 */
public class TableMetas {
    private HashMap<String, Table> tables = new HashMap<String, Table>();

    private TableMetas() {
    }

    public void addTable(String tableName, Connection connection) throws Exception {
        if (!tables.containsKey(tableName)){
            Table table = new Table(tableName, connection);
            tables.put(tableName, table);
        }
    }


    public synchronized Table get(String tableName, Connection connection) throws Exception {
        Table table = tables.get(tableName);
        if (table == null) {
            table = new Table(tableName, connection);
            tables.put(tableName, table);
        }
        return table;
    }

    public void clear(String neid, Connection connection) throws Exception {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            for (String tableName : tables.keySet()) {
                Table table = tables.get(tableName);
                String sql = "delete from " + table.name() + " where DN like '" + neid + "%'";
                statement.execute(sql);
            }
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }
}
