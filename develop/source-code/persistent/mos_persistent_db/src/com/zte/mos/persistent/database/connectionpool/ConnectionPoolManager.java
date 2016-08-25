package com.zte.mos.persistent.database.connectionpool;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by xy on 14-12-29.
 */
public class ConnectionPoolManager {

    private static IConnectionPool pool = null;

    private ConnectionPoolManager() throws Exception {
        //init();
    }


    public static void setPool(IConnectionPool userPool){
        pool = userPool;
    }

    public static Connection getConnection() throws Exception {
        if (pool == null){
            pool = new ConnectionPool();
        }
        return pool.getConnection();
    }

    public static void close(Connection conn) {
        try {
            if (pool != null) {
                pool.releaseConnection(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void destroy() {
        if (pool != null) {
            pool.destroy();
        }
    }
}
