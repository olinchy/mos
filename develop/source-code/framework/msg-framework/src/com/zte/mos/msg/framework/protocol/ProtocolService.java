package com.zte.mos.msg.framework.protocol;

import com.zte.mos.domain.IMosHead;
import com.zte.mos.exception.MOSException;
import com.zte.mos.msg.MsgMode;
import com.zte.mos.msg.SupportedProtocol;
import com.zte.mos.msg.framework.Protocol;
import com.zte.mos.msg.framework.ProtocolImp;
import com.zte.mos.msg.framework.protocol.configuration.ConfigurationFileGetter;
import com.zte.mos.msg.framework.protocol.configuration.RestfulCfgGetter;
import com.zte.mos.msg.framework.protocol.configuration.RpcCfgGetter;
import com.zte.mos.msg.framework.protocol.configuration.SnmpCfgGetter;
import com.zte.mos.msg.framework.session.ISessionConfiguration;
import com.zte.mos.msg.framework.session.SessionConfigFactory;
import com.zte.mos.util.basic.Logger;

import java.util.ArrayList;
import java.util.HashMap;

import static com.zte.mos.msg.MsgMode.Multi;
import static com.zte.mos.msg.SupportedProtocol.RESTFUL;
import static com.zte.mos.msg.SupportedProtocol.RPC;
import static com.zte.mos.msg.SupportedProtocol.SNMP;

/**
 * Created by luoqingkai on 15-9-24.
 *
 */
public class ProtocolService implements IProtocolService {

    Logger logger = Logger.logger(ProtocolService.class);
    private static IProtocolService sv = null;
    private String dn;
    private static HashMap<SupportedProtocol, ConfigurationFileGetter> converters = new HashMap<SupportedProtocol, ConfigurationFileGetter>();
    static {
        converters.put(SNMP, new SnmpCfgGetter());
        converters.put(RPC, new RpcCfgGetter());
        converters.put(RESTFUL, new RestfulCfgGetter());
    }
    public static IProtocolService getInstance() {
        if (sv == null) {
            sv = new ProtocolService();
        }
        return sv;
    }

    public static void setService(IProtocolService userService) {
        sv = userService;
    }

    @Override
    public Protocol[] getProtocols(IMosHead mosHeader, String dn) {
        this.dn = dn;
        SupportedProtocol pl = mosHeader.defaultProtocol();
        MsgMode model = mosHeader.msgMode();
        ArrayList<Protocol> protocols = new ArrayList<Protocol>();

        if (model == Multi){
            ProtocolImp snmp = buildSnmpProtocol();
            protocols.add(snmp);
        }
        Protocol defaultProtocol = getDefaultProtocol(pl);
        protocols.add(defaultProtocol);
        return protocols.toArray(new Protocol[protocols.size()]);
    }

    private Protocol getDefaultProtocol(SupportedProtocol pl) {
        switch (pl){
            case RPC:
                ISessionConfiguration config = SessionConfigFactory.getConfiguration("RPC", getConfigData(RPC));
                return new ProtocolImp("RPC", config);
            case RESTFUL:
                //ISessionConfiguration restfulconfig = SessionConfigFactory.getConfiguration("RESTFUL", getConfigData(RESTFUL));
                return new ProtocolImp("RESTFUL", null);
            default:
                return buildSnmpProtocol();
        }
    }

    private ProtocolImp buildSnmpProtocol() {
        ISessionConfiguration configSnmp = SessionConfigFactory.getConfiguration("SNMP", getConfigData(SNMP));
        return new ProtocolImp("SNMP", configSnmp);
    }

    protected HashMap<String, String> getConfigData(SupportedProtocol protocol){
        ConfigurationFileGetter converter = converters.get(protocol);
        try {
            return converter.getConfigData(dn);
        } catch (MOSException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}
