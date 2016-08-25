package com.zte.uep.stub;

import com.zte.mos.util.basic.Logger;
import com.zte.ums.uep.api.pfl.finterface.FIException;
import com.zte.ums.uep.api.pfl.log.*;
import com.zte.ums.uep.api.pfl.log.reserve.ReserveCond;
import com.zte.ums.uep.api.pfl.log.reserve.ReserveValueCond;

import java.sql.Timestamp;

/**
 * Created by pavel on 16-2-21.
 */
public class LogClientServiceMock implements LogClientService
{
    private static Logger logger = Logger.logger(LogClientServiceMock.class);

    @Override
    public long insertCmdLog(
            String s, String s1, String s2, String s3, String s4, int i, String s5, String s6, String[] strings,
            String[] strings1, Timestamp timestamp, Timestamp timestamp1, Timestamp timestamp2, long l, int i1, int i2,
            String[] strings2)
    {
        return 0;
    }

    private String toString(String[] strings){
        if (strings==null){
            return "null";
        }
        StringBuilder sb0 = new StringBuilder();
        for (String string:strings){
            sb0.append(string);
        }
        return sb0.toString();
    }

    @Override
    public long insertCmdLog(
            String s, String s1, String s2, String s3, String s4, int i, String s5, String s6, String[] strings,
            String[] strings1, String[] strings2, Timestamp timestamp, Timestamp timestamp1, Timestamp timestamp2,
            long l, int i1, int i2, String[] strings3)
    {
        LogStringBuffer sb = new LogStringBuffer();
        String sb0 = toString(strings);
        String sb1 = toString(strings1);
        String sb2 = toString(strings2);
        String sb3 = toString(strings3);
        sb.append(s).append(s1).append(s2).append(s3).append(s4).append(i).append(s5).append(s6).
                append(sb0).append(sb1).append(sb2).append(timestamp).append(timestamp1).append(timestamp2).
                append(l).append(i1).append(i2).append(sb3);
        logger.info(sb.toString());
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
    public LogMessage[] queryLog(LogCond logCond, int i, int i1) throws FIException
    {
        return new LogMessage[0];
    }

    @Override
    public CmdLogMessage[] queryCmdLog(
            CmdLogCond cmdLogCond, int i, int i1) throws FIException
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
    public long insertCmdLog(CmdLogMessage cmdLogMessage)
    {
        return 0;
    }

    @Override
    public long insertSysLog(SysLogMessage sysLogMessage)
    {
        return 0;
    }

    @Override
    public void updateCmdLog(CmdLogMessage cmdLogMessage)
    {

    }

    @Override
    public void updateSysLog(SysLogMessage sysLogMessage)
    {

    }

    @Override
    public ReserveValueCond getLogDefaultCond()
    {
        return null;
    }

    @Override
    public ReserveCond getReserveCond(String s)
    {
        return null;
    }

    @Override
    public LogRelateInfo queryCmdRelateLog(CmdLogCond cmdLogCond, int i, int i1)
    {
        return null;
    }

    @Override
    public LogRelateInfo querySysRelateLog(SysLogCond sysLogCond, int i, int i1) throws FIException
    {
        return null;
    }

    class LogStringBuffer{
        StringBuffer sb = new StringBuffer();
        public LogStringBuffer append(Object object){
            String string = object==null?"null":object.toString();
            sb.append(string+"/");
            return this;
        }
        public String toString(){
            return sb.toString();
        }
    }
}
