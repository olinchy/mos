package com.zte.mos.msg.framework.operation;

import com.zte.mos.domain.ManagementObject;
import com.zte.mos.type.Pair;

import java.util.List;

/**
 * Created by ccy on 7/21/15.
 */
public class Action extends MoOperation
{
    private String actionName;
    private List<Pair<String, Object>> paras;

    public Action(ManagementObject mo, String actionName, List<Pair<String, Object>> paras)
    {
        super(mo);
        this.actionName = actionName;
        this.paras = paras;
    }


    public String getActionName()
    {
        return OperEnum.Action + "_" + actionName;
    }

    public List<Pair<String, Object>> getParas()
    {
        return paras;
    }

    @Override
    public String getOperation()
    {
        return actionName;
    }


}
