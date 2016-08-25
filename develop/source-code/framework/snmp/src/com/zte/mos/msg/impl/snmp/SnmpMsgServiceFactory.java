package com.zte.mos.msg.impl.snmp;

/**
 * Created by zhangbin10086509 on 15-6-6.
 */
public class SnmpMsgServiceFactory {
    private static SnmpMsgService service = new SnmpMsgServiceImp();

    public static SnmpMsgService getService(){
        return service;
    }

//    public static void setService(SnmpMsgService userServ){
//        service = userServ;
//    }
}
