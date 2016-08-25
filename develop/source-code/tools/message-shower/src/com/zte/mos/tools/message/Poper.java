package com.zte.mos.tools.message;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zte.mos.type.Pair;

import static com.zte.mos.util.tools.JsonUtil.newObjNode;

/**
 * Created by olinchy on 2/9/15 for mosjava.
 */
public class Poper
{
    private static PopUpScheduler scheduler;

    private static Poper self = new Poper();

    Poper()
    {
        this(new PopUpScheduler(new DefaultStyledPoper()));
    }

    Poper(PopUpScheduler scheduler)
    {
        Poper.scheduler = scheduler;
    }

    public void showMsg(MsgType level, String msg)
    {
        scheduler.add(new Pair<MsgType, String>(level, msg));
    }

    public static void show(MsgType level, String msg)
    {
        self.showMsg(level, msg);
    }

    public static void show(MsgType level, String title, String msg)
    {
        self.showMsg(level, title, msg);
    }

    private void showMsg(MsgType level, String title, String msg)
    {
        ObjectNode node = newObjNode();
        node.put("title", title);
        node.put("msg", msg);
        scheduler.add(new Pair<MsgType, String>(level, node.toString()));
    }


}
