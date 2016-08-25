package com.zte.uep.stub;

import com.zte.ums.uep.api.psl.systemmonitor.*;

/**
 * Created by pavel on 16-3-25.
 */
public class SystemMonitorServiceMock implements SystemMonitorService
{
    @Override
    public OSInfo getOSInfo()
    {
        return null;
    }

    @Override
    public JCASupport[] getJCAInfos() throws UmsSysMonitorException
    {
        return new JCASupport[0];
    }

    @Override
    public DBInfo[] getDBInfos() throws UmsSysMonitorException
    {
        return new DBInfo[0];
    }

    @Override
    public DBInfo[] merge(DBInfo[] dbInfos)
    {
        return new DBInfo[0];
    }

    @Override
    public JCASupport getJCAInfo(String s) throws UmsSysMonitorException
    {
        return new JCASupport(s,"user","password","databaseType");
    }

    @Override
    public JCASupport getJCAInfo(String s, String s1) throws UmsSysMonitorException
    {
        return null;
    }

    @Override
    public JCASupport getJCAInfo(String s, String s1, String s2) throws UmsSysMonitorException
    {
        return null;
    }

    @Override
    public ParDeploymentInfo getParDeploymentInfo(String s) throws UmsSysMonitorException
    {
        return null;
    }

    @Override
    public ParThreadInfo getParThreadInfo(String s) throws UmsSysMonitorException
    {
        return null;
    }

    @Override
    public Boolean getParRuntimestate(String s) throws UmsSysMonitorException
    {
        return null;
    }
}
