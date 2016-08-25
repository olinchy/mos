package com.zte.mos.app.alarm.stub;

import com.zte.app.common.fm.AlarmDispatcherService;
import com.zte.mos.exception.MOSException;
import com.zte.mos.util.Singleton;
import com.zte.ums.uep.api.pfl.embmml.ParseMMLUtil;

import java.util.List;

/**
 * Created by ccy on 8/14/15.
 */
public class AlarmDispatcherStub implements AlarmDispatcherService
{
    @Override
    public void sendAlarm(List<ParseMMLUtil> listMML, boolean highPriority)
    {
        try
        {
            Singleton.getInstance(AlarmSimulatorDB.class).add(listMML);
        }
        catch (MOSException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void sendAlarm(ParseMMLUtil[] mmls, boolean highPriority)
    {
        try
        {
            Singleton.getInstance(AlarmSimulatorDB.class).add(mmls);
        } catch (MOSException e)
        {
            e.printStackTrace();
        }
    }
}
