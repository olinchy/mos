package com.zte.app.smartlink;

import com.zte.mos.annotation.PreLoad;
import com.zte.mos.exception.MOSException;
import com.zte.mos.node.app.ft.modelswitch.ModelSwitchApp;
import com.zte.mos.util.basic.Logger;
import com.zte.smartlink.NodeLink;
import com.zte.smartlink.SmartLink;
import com.zte.smartlink.SmartLinkRepository;

import static com.zte.mos.util.OperationMask.event;
import static com.zte.mos.util.Singleton.getInstance;
import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by ccy on 5/24/16.
 */

@PreLoad
public class ModelSwitchAppRegister
{
    private static Logger log = logger(ModelSwitchAppRegister.class);
    private final SmartLink tree = new SmartLink();

    public ModelSwitchAppRegister() throws MOSException
    {
        NodeLink neModel_change_event = new NodeLink("NeStateChangeEvent");
        neModel_change_event.addNode("BUNDLE", event);
        tree.addLink(neModel_change_event);

        getInstance(SmartLinkRepository.class).add(ModelSwitchApp.NAME, tree);
    }
}
