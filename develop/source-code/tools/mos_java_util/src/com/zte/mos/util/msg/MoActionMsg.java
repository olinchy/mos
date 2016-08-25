package com.zte.mos.util.msg;

import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.Maybe;
import com.zte.mos.inf.MoCmds;
import com.zte.mos.type.Pair;
import com.zte.mos.util.tools.JsonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 15-1-12.
 */
@SuppressWarnings("serial")
public class MoActionMsg extends MoMsgAdapter {

    private Maybe<Integer> transId;
    private String actionName = "";


    private List<Pair<String, Object>> parameters = new ArrayList<Pair<String, Object>>();

    public MoActionMsg(String dn, Maybe<Integer> transId) {
        super(dn);
        this.transId = transId;
    }

    public MoActionMsg(String dn, Maybe<Integer> transId, String actionName, List<Pair<String, Object>> paras){
        super(dn);
        this.transId = transId;
        this.actionName = actionName;
        this.parameters = paras;
    }

    @Override
    public MoCmds getCmd() {
        return MoCmds.MoAction;
    }

    @Override
    public Maybe<Integer> getTransactionID() {
        return this.transId;
    }

    @Override
    public void setTransId(Maybe<Integer> transId) {
        this.transId = transId;
    }

    public String actionName(){
        return actionName;
    }

    @SuppressWarnings("unchecked")
    public Pair<String, Object>[] paras(){
        Pair<String, Object>[] p = (Pair<String, Object>[]) new Pair[parameters.size()];
        return parameters.toArray(p);
    }

    public List<Pair<String, Object>> getParameters() {
        return parameters;
    }

    public String toString()
    {
        try
        {
            return JsonUtil.toString(this);
        }
        catch (MOSException e)
        {
            return this.getClass().getSimpleName() + "{" + "dn: " + String.valueOf(dn) +  " actionName: " +  String.valueOf(actionName) + "" + "}";
        }
    }

}
