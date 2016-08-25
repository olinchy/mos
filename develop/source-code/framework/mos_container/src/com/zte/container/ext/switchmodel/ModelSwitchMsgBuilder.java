package com.zte.container.ext.switchmodel;

import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.Maybe;
import com.zte.mos.type.Pair;
import com.zte.mos.util.msg.action.ModelSwitchReq;
import com.zte.mos.util.msg.action.ModelSwitchAck;
import com.zte.mos.util.structure.tree.MoTree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ccy on 5/5/16.
 */
public class ModelSwitchMsgBuilder
{
    public static ModelSwitchReq newModelSwitchReq(ModelSwitchContext context) throws MOSException
    {
        return new ModelSwitchReq(context.getID(), new Maybe<Integer>(), packModelSwitchReqParas(context));
    }


    public static ModelSwitchAck newModelSwitchAck(ModelSwitchContext context, ModelSwitchAck.Ack ack)
    {
        List<Pair<String,Object>> list = new ArrayList<Pair<String, Object>>();
        list.add(new Pair<String, Object>("ack", ack));
        return new ModelSwitchAck(context.getID(), new Maybe<Integer>(), list);
    }

    private static List<Pair<String,Object>> packModelSwitchReqParas(ModelSwitchContext context) throws MOSException
    {
        List<Pair<String, Object>> list = new ArrayList<Pair<String, Object>>();
        list.add(new Pair<String, Object>("newModelName", context.getNewModelName()));
        list.add(new Pair<String, Object>("newModelVersion", context.getNewModelVersion()));
        list.add(new Pair<String, Object>("ip", context.getIpAddress()));
        list.add(new Pair<String, Object>("targetId", context.getID()));
        list.add(new Pair<String, Object>("moTree", getMoTree(context)));
        list.add(new Pair<String, Object>("refRepository",getRefRepistory(context)));
        list.add(new Pair<String, Object>("netype", context.getNeType()));
        list.add(new Pair<String, Object>("oldModelName", context.getOldModelName()));
        list.add(new Pair<String, Object>("oldModelVersion", context.getOldModelVersion()));
        
        return list;
    }

    private static Object getRefRepistory(ModelSwitchContext context) throws MOSException
    {
        return ReferenceObjectRepositoryBuilder.buildRefObjectsRepository(context.getReferenceDB());
    }

    private static MoTree getMoTree(ModelSwitchContext context) throws MOSException
    {
        return MoTreeBuilder.buildMoTree(context.getMos());
    }


}
