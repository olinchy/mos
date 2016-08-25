package com.zte.mos.msg.framework.protocol.configuration;

import com.zte.mos.domain.DN;
import com.zte.mos.exception.MOSException;
import com.zte.mos.exception.NonvalidKeywordException;
import com.zte.mos.httpservice.MosServiceHttp;
import com.zte.mos.message.Mo;
import com.zte.mos.util.basic.Logger;

import java.util.HashMap;

/**
 * Created by dongyue on 16-7-21.
 */
public abstract class ConfigurationFileGetter {
    protected Logger logger = Logger.logger(ConfigurationFileGetter.class);
    public HashMap<String, String> getConfigData(String dn) throws MOSException {
        Mo mo = getMoFromEmsMos(dn);
        return convert2CfgValue(mo);
    }

    private Mo getMoFromEmsMos(String dn) throws MOSException {
        MosServiceHttp service = new MosServiceHttp();
        DN neDN = getFullDn(dn);
        Mo mo = service.get(neDN).getMo().get(0);
        logger.info(mo);
        return mo;
    }

    protected abstract DN getFullDn(String dn) throws NonvalidKeywordException;

    protected abstract HashMap<String, String> convert2CfgValue(Mo mo) throws MOSException;
}
