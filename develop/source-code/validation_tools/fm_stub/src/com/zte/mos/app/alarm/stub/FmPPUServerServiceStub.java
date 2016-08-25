package com.zte.mos.app.alarm.stub;

import com.zte.mos.util.Singleton;
import com.zte.ums.api.common.fm.ppu.entity.*;
import com.zte.ums.api.common.fm.ppu.service.FmPPUServerService;
import com.zte.ums.uep.api.pfl.embmml.ParseMMLUtil;

import java.util.*;

/**
 * Created by ccy on 8/13/15.
 */
public class FmPPUServerServiceStub implements FmPPUServerService {
    @Override
    public AlarmData[] getAlarmList(QueryAlarmCond queryAlarmCond) throws FMException {
        return new AlarmData[0];
    }

    @Override
    public AlarmData[] getAlarmList(QueryAlarmCond queryAlarmCond, boolean b) throws FMException {
        return new AlarmData[0];
    }

    @Override
    public AlarmData[] getCurrentAlarm(QueryAlarmCond cond) throws FMException
    {
        if (cond.getLink() != null && !cond.getLink().equals("")) {
            return getCurrentAlarmByLinkId(cond.getLink());
        }

        if (cond.getPosition1() != null && cond.getPosition1().length > 0)
        {
            return getCurrentAlarmByPosition(cond.getPosition1()[0]);
        }
        return new AlarmData[0];
    }

    private AlarmData[] getCurrentAlarmByLinkId(String link)
    {
        List<AlarmDataStub> lst = new ArrayList<AlarmDataStub>();

        try
        {
            HashMap<String, ParseMMLUtil> map =  Singleton.getInstance(AlarmSimulatorDB.class).getAll();
            Iterator iter = map.entrySet().iterator();
            while (iter.hasNext())
            {
                Map.Entry entry = (Map.Entry) iter.next();
                Object key = entry.getKey();
                ParseMMLUtil val = (ParseMMLUtil)entry.getValue();

                if (val.getParaValue(0, "Link") != null
                    && val.getParaValue(0, "Link").toString().equals(link))
                {
                    lst.add(new AlarmDataStub(val));
                }
            }
        }
        catch(Throwable e)
        {

        }

        return lst.toArray(new AlarmData[lst.size()]);
    }
    private AlarmData[] getCurrentAlarmByPosition(String neOid)
    {
        List<AlarmDataStub> lst = new ArrayList<AlarmDataStub>();


        try
        {
            HashMap<String, ParseMMLUtil> map =  Singleton.getInstance(AlarmSimulatorDB.class).getAll();
            Iterator iter = map.entrySet().iterator();
            while (iter.hasNext())
            {
                Map.Entry entry = (Map.Entry) iter.next();
                Object key = entry.getKey();
                ParseMMLUtil val = (ParseMMLUtil)entry.getValue();

                if (val.getParaValue(0, "Position1") != null
                        && val.getParaValue(0, "Position1").toString().equals(neOid))
                {
                    lst.add(new AlarmDataStub(val));
                }
            }
        }
        catch(Throwable e)
        {

        }
        return lst.toArray(new AlarmData[lst.size()]);
    }


    @Override
    public QueryResultAlarmDataWrapper getWrapperedCurrentAlarm(QueryAlarmCond queryAlarmCond) throws FMException {
        return null;
    }

    @Override
    public long getCurrentAlarmCount(QueryAlarmCond queryAlarmCond) throws FMException {
        return 0;
    }

    @Override
    public AlarmData[] getHistoryAlarm(QueryAlarmCond queryAlarmCond) throws FMException {
        return new AlarmData[0];
    }

    @Override
    public void ackAlarm(long l, String s, byte b, String s1, String s2) throws FMException {

    }

    @Override
    public void ackAlarm(long l, String s, byte b, String s1, String s2, String s3) throws FMException {

    }

    @Override
    public void ackAllAlarm(long l, byte b, String s, String s1) throws FMException {

    }

    @Override
    public void clearAlarm(long l, String s, String s1, String s2, byte b) throws FMException {

    }

    @Override
    public void commentAlarm(long l, String s, String s1, String s2, String s3) throws FMException {

    }

    @Override
    public void commentAllAlarm(long l, String s, String s1, String s2) throws FMException {

    }

    @Override
    public void modifyAdditionalText(long l, String s, String s1) throws FMException {

    }

    @Override
    public String getCodeName(short i, long l) throws FMException {
        return null;
    }

    @Override
    public ProbableCause getProbableCause(short i, long l) throws FMException {
        return null;
    }

    @Override
    public String getRepairAction(short i, long l) throws FMException {
        return null;
    }

    @Override
    public ProbableCause[] getProbableCause(short i) throws FMException {
        return new ProbableCause[0];
    }

    @Override
    public ProbableCause[] getModifiableProbableCause(short i) throws FMException {
        return new ProbableCause[0];
    }

    @Override
    public SystemType[] getSystemType(String s) throws FMException {
        return new SystemType[0];
    }

    @Override
    public SystemType[] getAllSystemType() throws FMException {
        return new SystemType[0];
    }

    @Override
    public AlarmType[] getAllAlarmType() throws FMException {
        return new AlarmType[0];
    }

    @Override
    public void setAlarmVisibility(QueryAlarmCond queryAlarmCond, boolean b, String s) throws FMException {

    }

    @Override
    public long[] getReasonCodsBySystemType(short i) throws FMException {
        return new long[0];
    }

    @Override
    public boolean isIntermittenceMainAlarm(AlarmData alarmData) {
        return false;
    }

    @Override
    public void setNafAlarmMaskRuleCond(NafAlarmMaskRuleCond nafAlarmMaskRuleCond, boolean b) throws FMException {

    }

    @Override
    public void regradeProbableCauseSeverity(short i, long l, byte b) throws FMException {

    }

    @Override
    public HistoryAlarmSeverityCount getHistorytAlarmLevelCount(AlarmStatisticCond alarmStatisticCond) throws FMException {
        return null;
    }

    @Override
    public CurAlarmSeverityCount getCurAlarmLevelCount(AlarmStatisticCond alarmStatisticCond) throws FMException {
        return null;
    }

    @Override
    public void asynQueryCurrentAlarm(
            QueryAlarmCond queryAlarmCond, AsynQueryAlarmListener asynQueryAlarmListener)
    {

    }
}
