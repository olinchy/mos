package com.zte.uep.stub;

import com.zte.ums.uep.api.pfl.finterface.FIException;
import com.zte.ums.uep.api.pfl.log.*;

import java.sql.Timestamp;
import java.util.HashMap;

/**
 * Created by pavel on 16-2-21.
 */
public class LogServerServiceMock implements LogServerService
{
    @Override
    public long insertCmdLog(
            String s, String s1, String s2, String s3, String s4, int i, String s5, String s6, String[] strings,
            String[] strings1, Timestamp timestamp, Timestamp timestamp1, Timestamp timestamp2, long l, int i1, int i2,
            String[] strings2)
    {
        return 0;
    }

    @Override
    public long insertCmdLog(
            String s, String s1, String s2, String s3, String s4, int i, String s5, String s6, String[] strings,
            String[] strings1, String[] strings2, Timestamp timestamp, Timestamp timestamp1, Timestamp timestamp2,
            long l, int i1, int i2, String[] strings3)
    {
        return 0;
    }

    @Override
    public void updateCmdLog(long l, String s, int i, String s1, Timestamp timestamp, String[] strings)
    {

    }

    @Override
    public long insertSysLog(
            int i, String s, String s1, String s2, String s3, Timestamp timestamp, Timestamp timestamp1, int i1, long l,
            String[] strings)
    {
        return 0;
    }

    @Override
    public void updateSysLog(long l, int i, String s, Timestamp timestamp, String[] strings)
    {

    }

    @Override
    public void updateSecLog(long l, String s, String s1, String[] strings)
    {

    }

    @Override
    public long insertSecLog(
            String s, String s1, String s2, String s3, Timestamp timestamp, String s4, String[] strings)
    {
        return 0;
    }

    @Override
    public long insertCmdLog(CmdLogMessage cmdLogMessage)
    {
        return 0;
    }

    @Override
    public void updateCmdLog(CmdLogMessage cmdLogMessage)
    {

    }

    @Override
    public long insertSysLog(SysLogMessage sysLogMessage)
    {
        return 0;
    }

    @Override
    public void updateSysLog(SysLogMessage sysLogMessage)
    {

    }

    @Override
    public long insertSecLog(SecLogMessage secLogMessage)
    {
        return 0;
    }

    @Override
    public void updateSecLog(SecLogMessage secLogMessage)
    {

    }

    @Override
    public LogMessage[] queryLog(LogCond logCond, int i, int i1) throws FIException
    {
        return new LogMessage[0];
    }

    @Override
    public CmdLogMessage[] queryCmdLog(CmdLogCond cmdLogCond, int i, int i1) throws FIException
    {
        return new CmdLogMessage[0];
    }

    @Override
    public SysLogMessage[] querySysLog(SysLogCond sysLogCond, int i, int i1) throws FIException
    {
        return new SysLogMessage[0];
    }

    @Override
    public SecLogMessage[] querySecLog(SecLogCond secLogCond, int i, int i1) throws FIException
    {
        return new SecLogMessage[0];
    }

    @Override
    public HashMap<String, Object> getPageinfo(
            LogCond logCond, HashMap<String, String> hashMap) throws FIException
    {
        return null;
    }
}
