package com.zte.mos.service;

import com.zte.mos.domain.DN;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.IndicationSender;
import com.zte.mos.message.Result;
import com.zte.mos.persistent.Log;
import com.zte.mos.service.indication.Indicators;
import com.zte.mos.util.msg.IndicateMsg;
import com.zte.smartlink.Address;
import com.zte.smartlink.deliver.DeliverService;
import com.zte.smartlink.deliver.SmartLinkMsgService;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by root on 14-12-12.
 */
public class SmartLinkRouter implements IndicationSender<ManagementObject>
{
    private final String rootExternalDN;
    private String senderName;

    //private Map<String, Host> hostMap = new ConcurrentHashMap<String, Host>();

    public SmartLinkRouter(String rootExteranlDN, String senderName) throws MOSException
    {
        this.rootExternalDN = rootExteranlDN;
        this.senderName = senderName;
    }

    public String getMoDN(String dn)
    {
        DN newDn = new DN(rootExternalDN + dn);
        return newDn.toString();
    }

    private boolean isValidHop(String dn, String route)
    {
        String tempRoute = route + "/";
        return dn.startsWith(tempRoute) || dn.endsWith(route);
    }

    public Result onMsg(MoMsg msg) throws MOSException
    {
        msg.setDN(getMoDN(msg.dn()));
        return SmartLinkMsgService.send(senderName, msg);
    }

    public Result onMsg(MoMsg msg, Address... exclude) throws MOSException
    {
        msg.setDN(getMoDN(msg.dn()));
        return SmartLinkMsgService.send(senderName, msg, exclude);
    }

    @Override
    public void noti(List<Log<ManagementObject>> logList) throws MOSException
    {
        LinkedList<IndicateMsg> msgLst = new LinkedList<IndicateMsg>();
        for (int i = logList.size() - 1; i >= 0; i--)
        {
            Log<ManagementObject> tLog = logList.get(i);
            msgLst.add(Indicators.valueOf(tLog.type()).createMsg(tLog, rootExternalDN));
        }
        new DeliverService(senderName).indicate(msgLst);
    }
}
