package com.zte.container.ext.switchmodel.log;

import com.zte.container.ext.switchmodel.service.MoIndicators;
import com.zte.mos.domain.DN;
import com.zte.mos.exception.MOSException;
import com.zte.mos.message.Mo;
import com.zte.mos.persistent.Log;
import com.zte.mos.service.RawMos;
import com.zte.mos.util.basic.Logger;
import com.zte.mos.util.msg.IndicateMsg;
import com.zte.smartlink.deliver.DeliverService;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by ccy on 5/6/16.
 */
public class LogDispatcher
{
    private static Logger log = logger(LogDispatcher.class);

    public LogDispatcher(LogMerger logMerger, RawMos mos)
    {
        this.logMerger = logMerger;
        this.mos = mos;
    }

    private final LogMerger logMerger;
    private final RawMos mos;

    public void dispatch() throws MOSException
    {
        new Thread(
                new Runnable()
                {
                    @Override
                    public void run()
                    {
                        List<Log<Mo>> list = new ArrayList<Log<Mo>>();

                        try
                        {
                            list.addAll(logMerger.getDelete());
                            list.addAll(logMerger.getInsert());
                            list.addAll(logMerger.getUpdate());
                            dispatchLogs(list);
                        }
                        catch (MOSException e)
                        {
                            logger(LogDispatcher.class).error("failed in dispatch logs", e);
                        }
                    }
                }).start();
    }

    private void dispatchLogs(List<Log<Mo>> list) throws MOSException
    {
        log.debug(this.mos.getRootExternalDN() + " start dispatch logs");
        LinkedList<IndicateMsg> msgLst = new LinkedList<IndicateMsg>();
        for (Log<Mo> tLog : list)
        {
            logLog(tLog);
            msgLst.add(MoIndicators.valueOf(tLog.type()).createMsg(tLog, this.mos.getRootExternalDN()));
        }
        new DeliverService(mos.smartlinkName()).indicate(msgLst);
        log.debug( this.mos.getRootExternalDN() + " end dispatch logs");
    }

    private void logLog(Log<Mo> moLog)
    {
        log.debug("logType: " + moLog.type() + " mo dn: " + dn(moLog));
    }

    private String dn(Log<Mo> moLog)
    {
        Log.LogType type = Log.LogType.valueOf(moLog.type());
        DN dn = null;
        if (type == Log.LogType.Insert)
        {
            dn = moLog.newValue().getDn();
        }
        else
        {
            dn = moLog.oldValue().getDn();
        }
        return dn.toString();
    }
}
