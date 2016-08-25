package com.zte.mos.persistent.database.connectionpool;

/**
 * Created by xy on 14-12-29.
 */
public class DBInfo {
    private String driverName;
    private String url;
    private String userName;
    private String password;
    private int minConnections = 1;
    private int maxConnections = 10;
    private int initConnections = 5;
    private long connTimeOut = 1000;
    private int maxActiveConnections = 100;
    private long connectionTimeOut = 1000 * 60 * 20;
    private boolean isCurrentConnection = true;
    private boolean isCheckPool = true;
    private long lazyCheck = 1000 * 60 * 60;
    private long periodCheck = 1000 * 60 * 60;

    public DBInfo() {
    }

    public DBInfo(String driverName, String url, String userName, String password) {
        super();
        this.driverName = driverName;
        this.url = url;
        this.userName = userName;
        this.password = password;
    }

    public long getConnTimeOut() {
        return connTimeOut;
    }

    public void setConnTimeOut(long connTimeOut) {
        this.connTimeOut = connTimeOut;
    }

    public String getDriverName() {
        if (driverName == null) {
            driverName = this.getDriverName() + "_" + this.getUrl();
        }
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMinConnections() {
        return minConnections;
    }

    public void setMinConnections(int minConnections) {
        this.minConnections = minConnections;
    }

    public int getMaxConnections() {
        return maxConnections;
    }

    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }

    public int getInitConnections() {
        return initConnections;
    }

    public void setInitConnections(int initConnections) {
        this.initConnections = initConnections;
    }

    public int getMaxActiveConnections() {
        return maxActiveConnections;
    }

    public void setMaxActiveConnections(int maxActiveConnections) {
        this.maxActiveConnections = maxActiveConnections;
    }

    public long getConnectionTimeOut() {
        return connectionTimeOut;
    }

    public void setConnectionTimeOut(long connectionTimeOut) {
        this.connectionTimeOut = connectionTimeOut;
    }

    public boolean isCurrentConnection() {
        return isCurrentConnection;
    }

    public void setCurrentConnection(boolean isCurrentConnection) {
        this.isCurrentConnection = isCurrentConnection;
    }

    public boolean isCheckPool() {
        return isCheckPool;
    }

    public void setCheckPool(boolean isCheakPool) {
        this.isCheckPool = isCheakPool;
    }

    public long getLazyCheck() {
        return lazyCheck;
    }

    public void setLazyCheck(long lazyCheck) {
        this.lazyCheck = lazyCheck;
    }

    public long getPeriodCheck() {
        return periodCheck;
    }

    public void setPeriodCheck(long periodCheck) {
        this.periodCheck = periodCheck;
    }
}
