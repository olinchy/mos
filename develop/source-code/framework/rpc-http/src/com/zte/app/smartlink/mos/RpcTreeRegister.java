package com.zte.app.smartlink.mos;

import com.zte.mos.annotation.PreLoad;
import com.zte.mos.exception.MOSException;
import com.zte.smartlink.NodeLink;
import com.zte.smartlink.SmartLink;
import com.zte.smartlink.SmartLinkRepository;

import static com.zte.mos.util.OperationMask.event;
import static com.zte.mos.util.Singleton.getInstance;

/**
 * Created by dongyue on 16-3-31.
 */
@PreLoad
public class RpcTreeRegister {
    private final SmartLink tree = new SmartLink();

    public RpcTreeRegister() throws MOSException {
        NodeLink node_ne_event = new NodeLink("/Ems/1/Ne/*");
        node_ne_event.addNode("APP_MW_POLL", event);
        tree.addLink(node_ne_event);
        getInstance(SmartLinkRepository.class).add("RPC_HTTP", tree);
    }
}
