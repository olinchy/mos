package com.zte.mos.app.rpcserver.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoCmds;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.Mo;
import com.zte.mos.message.Result;

/**
 * Created by olinchy on 6/18/14 for MO_JAVA.
 */
public interface Invoker
{
    Result<Result> send(MoMsg msg) throws MOSException;

    Result sendCfg(MoCmds cmd, String dn, Mo mo, JsonNode transIdNode)
            throws MOSException;

    void sendIndicate(MoCmds cmd, String dn, Mo mo) throws MOSException;
}
