package com.zte.mos.msg.impl.rpc;

import com.zte.mos.exception.MOSException;
import com.zte.mos.util.Scan;
import com.zte.mos.util.scaner.PreLoadScanner;

/**
 * Created by luoqingkai on 15-11-9.
 */
public class TestConnect {

    private static void preload(String path) throws MOSException {
        PreLoadScanner scanner = new PreLoadScanner();
        scanner.scan(Scan.getClasses(path, Object.class));

    }
/*
    @Test
    public void Given_session_When_switch_closed_Then_no_future_got() throws MsgFrameException, MOSException {
        preload("com.zte.mos.msg.impl");
        ISouthService service = CommServiceFactory.getService();
        TargetAddress address = new UTTargetAddress();
        Protocol[] protocols = {new ProtocolImp("RPC", new RpcUserConfiguration("","", 1000))};
        service.register(address, protocols);
        try {
            Thread.sleep(6);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void Given_session_When_switch_open_Then_future_got()throws MsgFrameException, MOSException{
        TargetAddress address = new UTTargetAddress();

        preload("com.zte.mos.msg.impl");
        ISouthService service = CommServiceFactory.getService();

        RpcUserConfiguration userConfiguration =  new RpcUserConfiguration("","", 1000);
        Protocol[] protocols = {new ProtocolImp("RPC", userConfiguration)};
        service.register(address, protocols);
        service.setConnectSwitch(address.getTargetID(), true);
        //JsonNode node = RpcUtil.toJson(new RpcSysConfiguration(), userConfiguration);
        try {
            Thread.sleep(9*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void Given_multi_sessions_When_connect_all_Then_time_cosume_got() throws Exception {
        preload("com.zte.mos.msg.impl");
        RpcUserConfiguration userConfiguration =  new RpcUserConfiguration("","", 1000);
        Protocol[] protocols = {new ProtocolImp("RPC", userConfiguration)};
        ISouthService service = CommServiceFactory.getService();

        UTTargetAddress[] targets = new UTTargetAddress[100000];
        int i=0;
        for ( i = 0; i < 100000; i++){
            targets[i] = new UTTargetAddress();
            targets[i].setTargetID(String.valueOf(i));
            targets[i].setIpAddress("10."+i/250/250+"." + (i/250)%250 + "." +i%250);
            service.register(targets[i], protocols);
            service.setConnectSwitch(targets[i].getTargetID(), true);
        }

        try {
            for (int j=0;j<200;j++) {
                Thread.sleep(3 * 1000);
                //System.out.println(j+"----------------------"+RpcSession.count+"------------------------");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
*/

}
