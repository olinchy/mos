package com.zte.mos.service.cmd;

import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoCmds;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.Result;
import com.zte.mos.service.MOS;

import java.util.HashMap;

import static com.zte.mos.inf.MoCmds.*;

/**
 * Created by root on 14-12-1.
 */
public class MoCommandExecutor
{
    private static final HashMap<MoCmds, MoCmdHandler> map = new HashMap<MoCmds, MoCmdHandler>();

    static
    {
        map.put(MoLsRequest, new LS());
        map.put(MoGetMetaRequest, new GET_META());
        map.put(MoGetRequest, new GET_MO());
        map.put(MoGetConfigRequest, new GET_MO_CONFIG());
        map.put(MoFindRequest, new FIND());

        //map.put(MoVersionRequest, new GET_VERSION());

        map.put(MoCreateRequest, new CREATE_MO());
        map.put(MoDeleteRequest, new DELETE_MO());
        map.put(MoSetRequest, new SET_MO());

        map.put(MoRef, new REF_MO());
        map.put(MoDeRef, new DEREF_MO());
        map.put(MoDestroyRef, new REF_KILLED_OF_THIS_MO());

        map.put(MoAck, new ACK());

        map.put(MoCreateInd, new IND_MO_CREATION());
        map.put(MoDeleteInd, new IND_MO_DEL());
        map.put(MoSetInd, new IND_MO_SET());
        map.put(MoAction, new ACTION());
    }

    public static final Result<?> execute(MoMsg msg, MOS mos) throws MOSException
    {
        MoCmdHandler handler = map.get(msg.getCmd());
        return handler.execute(msg, mos);
    }

    public static final void setUserHandler(MoCmdHandler userHandler)
    {
        map.put(userHandler.getCmd(), userHandler);
    }
}
