package com.zte.mos.modb.stub;

import com.zte.mos.exception.MOSException;
import com.zte.mos.util.OperationMask;
import com.zte.smartlink.NodeLink;
import com.zte.smartlink.SmartLink;
import com.zte.smartlink.SmartLinkRepository;

import static com.zte.mos.util.Singleton.getInstance;

public class MOSTreeRegister
{
    private final NodeLink node_root_1 = new NodeLink(
            "/root/1**");
    private final NodeLink node_root_1_p2pRoute_1 = new NodeLink(
            "/root/1/p2pRoute/1");
    private final NodeLink node_root_1_p2pRoute__ = new NodeLink(
            "/root/*/p2pRoute/*");

    private final SmartLink smartLink = new SmartLink();

    public MOSTreeRegister() throws MOSException
    {
        smartLink.addLink(node_root_1);
        smartLink.addLink(node_root_1_p2pRoute_1);
        smartLink.addLink(node_root_1_p2pRoute__);

        node_root_1_p2pRoute_1.addNode("TTM1", OperationMask.configChecking);
        node_root_1_p2pRoute_1.addNode("ETM", OperationMask.configChecking);
        node_root_1_p2pRoute_1.addNode("RPM", OperationMask.configIndication);
        node_root_1.addNode("Board", OperationMask.configChecking);
        node_root_1_p2pRoute__.addNode("TTM2",
                OperationMask.configChecking.or(OperationMask.get));
        node_root_1_p2pRoute__.addNode("TTM1", OperationMask.get);

        getInstance(SmartLinkRepository.class).add("MOS", smartLink);
    }
}
