package com.zte.mos.persistent.database.connectionpool;

import com.zte.mos.util.tools.Prop;
import com.zte.ums.usf.bsf.system.server.DataSourceInfo;
import com.zte.ums.usf.bsf.system.server.DatabaseInfoTool;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * Created by xy on 14-12-29.
 */
public class DBInit {

    private DBInfo bean = new DBInfo();

    public DBInit() throws Exception {

        initDBInfo(getCmDatabaseInfoFromEms());
    }

    public void initConnectionPool(ConnectionPool pool)
    {
        pool.init(bean);
    }

    private DataSourceInfo getCmDatabaseInfoFromEms() throws Exception {

        String dsName = Prop.get("ds_name");

        DatabaseInfoTool toolIns = DatabaseInfoTool.getInstance(getEmsInstallRoot().getAbsolutePath());
        for (DataSourceInfo info : toolIns.listDataSourceInfo()) {
            if (info.getJndiName().equals(dsName)) {
                return info;
            }
        }

        throw new SQLException("data source not found by name: " + dsName);
    }

    private File getEmsInstallRoot() throws URISyntaxException, UnsupportedEncodingException {
        String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        String utfPath = java.net.URLDecoder.decode(path, "UTF-8");
        File jarDir = new File(utfPath);
        while (!jarDir.getName().equalsIgnoreCase("ums-server"))
        {
            jarDir = jarDir.getParentFile();
        }
        return jarDir.getParentFile();
    }

    private void initDBInfo(DataSourceInfo info){
        bean.setUserName(info.getUser());
        bean.setPassword(info.getPassword());
        bean.setDriverName(Prop.get("driver_" + info.getDbType()));
        bean.setUrl(info.getUrl());
    }

}
