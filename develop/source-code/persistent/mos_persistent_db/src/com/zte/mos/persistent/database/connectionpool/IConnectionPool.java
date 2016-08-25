package com.zte.mos.persistent.database.connectionpool;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by ccy on 15-4-15.
 */
public interface IConnectionPool {
    Connection getConnection();

    void destroy();

    void releaseConnection(Connection conn) throws SQLException;

    void checkPool();
}
