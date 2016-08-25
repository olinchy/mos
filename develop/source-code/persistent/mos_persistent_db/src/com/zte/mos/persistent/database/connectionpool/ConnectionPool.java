package com.zte.mos.persistent.database.connectionpool;

import com.zte.mos.util.basic.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

/**
 * Created by xy on 14-12-29.
 */
public class ConnectionPool implements IConnectionPool
{

    private boolean isActive = false;
    private int contActive = 0;
    private List<Connection> freeConnection = new Vector<Connection>();
    private List<Connection> activeConnection = new Vector<Connection>();
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();
    private DBInfo dbBean;

    ConnectionPool() throws Exception {
        DBInit dbInit = new DBInit();
        dbInit.initConnectionPool(this);
    }

    void init(DBInfo dbBean)
    {
        this.dbBean = dbBean;
        try
        {
            Class.forName(dbBean.getDriverName());
            for (int i = 0; i < dbBean.getInitConnections(); i++)
            {
                Connection conn;
                conn = newConnection();
                if (conn != null)
                {
                    freeConnection.add(conn);
                    contActive++;
                }
            }
            isActive = true;
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        checkPool();
    }

    public synchronized Connection getConnection()
    {
        Connection conn = null;
        try
        {
            if (contActive < this.dbBean.getMaxActiveConnections())
            {
                if (freeConnection.size() > 0)
                {
                    conn = freeConnection.get(0);
                    if (conn != null)
                    {
                        threadLocal.set(conn);
                    }
                    freeConnection.remove(0);
                }
                else
                {
                    conn = newConnection();
                }

            }
            else
            {
                wait(this.dbBean.getConnTimeOut());
                conn = getConnection();
            }
            if (isValid(conn))
            {
                activeConnection.add(conn);
                contActive++;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        return conn;
    }

    private synchronized Connection newConnection() throws ClassNotFoundException, SQLException
    {
        Connection conn = null;
        if (dbBean != null)
        {
            Class.forName(dbBean.getDriverName());
            conn = DriverManager.getConnection(dbBean.getUrl(),
                    dbBean.getUserName(), dbBean.getPassword());
        }
        return conn;
    }

    public synchronized void releaseConnection(Connection conn) throws SQLException
    {
        if (isValid(conn) && !(freeConnection.size() > dbBean.getMaxConnections()))
        {
            freeConnection.add(conn);
            activeConnection.remove(conn);
            contActive--;
            threadLocal.remove();
            notifyAll();
        }
    }

    private boolean isValid(Connection conn)
    {
        try
        {
            if (conn == null || conn.isClosed())
            {
                return false;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return true;
    }

    public synchronized void destroy()
    {
        for (Connection conn : freeConnection)
        {
            try
            {
                if (isValid(conn))
                {
                    conn.close();
                }
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        for (Connection conn : activeConnection)
        {
            try
            {
                if (isValid(conn))
                {
                    conn.close();
                }
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        isActive = false;
        contActive = 0;
    }

    public void checkPool()
    {
        if (dbBean.isCheckPool())
        {
            new Timer("Timer-"+ConnectionPool.class.getSimpleName()).schedule(new TimerTask()
            {
                public Logger logger = Logger.logger(this.getClass());

                @Override
                public void run()
                {
                    logger.info("free connection" + freeConnection.size());
                    logger.info("active connection" + activeConnection.size());
                    logger.info("" + contActive);
                }
            }, dbBean.getLazyCheck(), dbBean.getPeriodCheck());
        }
    }

    public boolean isActive()
    {
        return isActive;
    }
}
