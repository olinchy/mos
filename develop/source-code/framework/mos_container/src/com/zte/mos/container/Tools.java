package com.zte.mos.container;

import com.zte.mos.inf.MoMsg;
import com.zte.mos.service.MOS;
import com.zte.mos.util.msg.MoAckMsg;

/**
 * Created by luoqingkai on 15-8-18.
 */
public class Tools {

    public static void cutMsgHead(MoMsg msg, String head){
        String[] dns = msg.dns();
        if (dns.length == 1){
            cutSingleDnHead(msg, head);
        }else{
            cutMultiDnHead(msg, head);
        }
    }

    private static void cutSingleDnHead(MoMsg msg, String head)
    {
        msg.setDN(cutDnHead(msg.dn(), head));
    }

    private static void cutMultiDnHead(MoMsg msg, String head)
    {
        MoAckMsg ackMsg = (MoAckMsg) msg;
        String[] dns = ackMsg.dns();
        ackMsg.setDn(cutMsgHead(dns, head));

    }

    private static String[] cutMsgHead(String[] dns, String head)
    {
        String[] res = new String[dns.length];
        for (int i = 0; i < dns.length; i++)
        {
            res[i] = cutDnHead(dns[i], head);
        }
        return res;
    }

    private static String cutDnHead(String dn, String head)
    {
        if (!head.equals(MOS.ROOT_INTERNAL_DN) && dn.startsWith(head))
        {
            String noHeadDn = dn.replace(head, "");
            if (noHeadDn.isEmpty())
            {
                noHeadDn = MOS.ROOT_INTERNAL_DN;
            }
            return noHeadDn;
        }
        else
        {
            return dn;
        }
    }
}
