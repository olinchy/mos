package com.zte.mos.app.alarm.stub;

import com.zte.ums.api.common.fm.ppu.FmUtil;
import com.zte.ums.uep.api.pfl.embmml.ParseMMLUtil;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ccy on 8/14/15.
 */
public class AlarmSimulatorDB
{
    HashMap<String, ParseMMLUtil> map = new HashMap<String, ParseMMLUtil>();

    private AlarmSimulatorDB()
    {

    }

    public void add(List<ParseMMLUtil> lst)
    {
        for(ParseMMLUtil mml : lst)
        {
            if (mml.getCommandCode().equals(String.valueOf(FmUtil.RPT_ALARM_CLEAR)))
            {
                map.remove(mml.getParaValue(0, "AlarmKey").toString());
            }
            else {
                map.put(mml.getParaValue(0, "AlarmKey").toString(), mml);
            }
        }
    }

    public void add(ParseMMLUtil[] mmls)
    {
        for(ParseMMLUtil mml : mmls)
        {
            if (mml.getCommandCode().equals(String.valueOf(FmUtil.RPT_ALARM_CLEAR)))
            {
                map.remove(mml.getParaValue(0, "AlarmKey").toString());
            }
            else {
                map.put(mml.getParaValue(0, "AlarmKey").toString(), mml);
            }
        }
    }

    public ParseMMLUtil get(String key)
    {
        return map.get(key);
    }

    public HashMap<String, ParseMMLUtil> getAll()
    {
        return map;
    }

    public void clear()
    {
        map.clear();
    }
}
