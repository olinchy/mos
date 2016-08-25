package com.zte.mos.msg.framework;

import com.zte.mos.domain.IMosHead;
import com.zte.mos.domain.TargetAddress;
import com.zte.mos.exception.MOSException;
import com.zte.mos.msg.framework.except.MsgFrameException;
import com.zte.mos.msg.framework.except.NotConnectException;
import com.zte.mos.msg.framework.except.UnregisteredException;
import com.zte.mos.msg.framework.except.UnsupportedProtocolException;
import com.zte.mos.msg.framework.operation.PingResponse;
import com.zte.mos.msg.framework.protocol.ProtocolService;
import com.zte.mos.msg.framework.session.SessionFactory;
import com.zte.mos.msg.impl.UTMosHead;
import com.zte.mos.msg.impl.UTProtocolService;
import com.zte.mos.msg.impl.UTTargetAddress;
import com.zte.mos.util.Scan;
import com.zte.mos.util.scaner.PreLoadScanner;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.zte.mos.domain.ConnectionState.OFFLINE;
import static com.zte.mos.domain.ConnectionState.ONLINE;
import static com.zte.mos.msg.MsgMode.Single;
import static com.zte.mos.msg.SupportedProtocol.RPC;
import static com.zte.mos.msg.SupportedProtocol.SNMP;
import static org.junit.Assert.*;

/**
 * Created by luoqingkai on 15-9-24.
 * This class can only test the framework.
 */
public class TestSouthFramework {

    @BeforeClass
    public static void setup() throws MOSException {
        preload("com.zte.mos.msg.impl");
    }

    private static void preload(String path) throws MOSException {
        PreLoadScanner scanner = new PreLoadScanner();
        scanner.scan(Scan.getClasses(path, Object.class));
    }

    @Before
    public void setupBeforeCase(){

    }

    private TargetAddress buildTarget(IMosHead mosHead){
        UTTargetAddress address = new UTTargetAddress();
        address.setIpAddress("127.0.0.1");
        address.setTargetID("/Ems/1/Ne/1/Product/1");
        address.setMosHead(mosHead);
        return address;
    }

    private void initProtocolService(String[] protocols){
        Protocol[] pArray = new ProtocolImp[protocols.length];
        for (int i = 0; i < protocols.length; i++){
            pArray[i] = new ProtocolImp(protocols[i], null);
        }
        UTProtocolService psv = new UTProtocolService();
        psv.setProtocolArray(pArray);
        ProtocolService.setService(psv);
    }

    @After
    public void teardownAfterCase(){
        ISouthService service = CommServiceFactory.getService();
        service.unRegister("/Ems/1/Ne/1/Product/1");
    }


    @Test
    public void Given_bundle_When_start_Then_serivces_reged() {
        boolean isReged = SessionFactory.isSupported(new ProtocolImp("RPC", null));
        assertTrue(isReged);
    }

    @Test
    public void Given_factory_When_get_service_Then_service_got() {
        ISouthService service = CommServiceFactory.getService();
        assertNotNull(service);
    }


    @Test(expected = UnsupportedProtocolException.class)
    public void Given_register_no_protocol_When_send_Then_exception_thrown()
            throws MsgFrameException {
        initProtocolService(new String[]{});
        TargetAddress address = buildTarget(new UTMosHead(1.1f, SNMP, Single));

        ISouthService service = CommServiceFactory.getService();
        service.register(address);
        service.setConnectSwitch(address.getTargetID(), ONLINE);
        service.ping(address.getTargetID());
    }

    @Test
    public void Given_supported_protocol_When_register_Then_done() throws MsgFrameException {
        initProtocolService(new String[]{"RPC"});
        TargetAddress address = buildTarget(new UTMosHead(1.2f, RPC, Single));

        ISouthService service = CommServiceFactory.getService();
        service.register(address);
        service.setConnectSwitch(address.getTargetID(), ONLINE);
        service.ping(address.getTargetID());

    }

    @Test(expected = UnregisteredException.class)
    public void Given_un_registered_protocol_When_send_msg_Then_exception_thrown() throws MsgFrameException {

        ISouthService service = CommServiceFactory.getService();
        service.ping("/Ems/1/Ne/1/Product/1");
    }

    @Test
    public void Given_registered_service_When_user_define_process_Then_send_with_the_process()
            throws MsgFrameException {
        initProtocolService(new String[]{"RPC"});
        TargetAddress address = buildTarget(new UTMosHead(1.2f, RPC, Single));

        ISouthService service = CommServiceFactory.getService();
        service.register(address);
        service.setConnectSwitch(address.getTargetID(), ONLINE);
        PingResponse response = service.ping(address.getTargetID());
        assertNull(response.getAddress());

    }

    @Test(expected = NotConnectException.class)
    public void Given_registered_service_When_conn_disconnected_Then_send_with_the_process()
            throws MsgFrameException {
        initProtocolService(new String[]{"RPC"});
        TargetAddress address = buildTarget(new UTMosHead(1.2f, RPC, Single));

        ISouthService service = CommServiceFactory.getService();
        service.register(address);
        service.setConnectSwitch(address.getTargetID(), OFFLINE);
        service.ping(address.getTargetID());

    }

}
