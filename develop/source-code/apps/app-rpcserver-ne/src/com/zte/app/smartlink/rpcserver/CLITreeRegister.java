package com.zte.app.smartlink.rpcserver;

import com.zte.mos.annotation.PreLoad;
import com.zte.mos.exception.MOSException;
import com.zte.mos.util.OperationMask;
import com.zte.smartlink.NodeLink;
import com.zte.smartlink.SmartLink;
import com.zte.smartlink.SmartLinkRepository;

import static com.zte.mos.util.Singleton.getInstance;

@PreLoad
public class CLITreeRegister
{
    private final NodeLink node_Ems___Ne__ = new NodeLink("/**");
    private final SmartLink tree = new SmartLink();

    public CLITreeRegister() throws MOSException
    {
        tree.addLink(node_Ems___Ne__);
        node_Ems___Ne__.addNode("MOS", OperationMask.all);
        getInstance(SmartLinkRepository.class).add("CLI", tree);

        SmartLink tree1 = new SmartLink();
        tree1.addLink(node_Ems___Ne__);
        getInstance(SmartLinkRepository.class).add("MOSSERVICE", tree1);
    }
}