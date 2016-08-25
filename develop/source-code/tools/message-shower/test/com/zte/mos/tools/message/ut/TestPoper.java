package com.zte.mos.tools.message.ut;

import com.zte.mos.tools.message.MsgType;
import com.zte.mos.tools.message.Poper;
import org.junit.Test;

import java.util.Date;

/**
 * Created by olinchy on 2/11/15 for mosjava.
 */
public class TestPoper
{
    @Test
    public void test() throws Exception
    {
        for (int i = 0; i < 100; i++)
        {
            Poper.show(MsgType.values()[i % MsgType.values().length],
                    "add /P2pRoute/" + i + " failed! in CESService management operated by admin on "
                            + new Date().toString());
            Thread.sleep(1000);
        }
        //        Poper.show(MsgType.error, "add /P2pRoute/1 failed!");
        //        Thread.sleep(1000);
        //        Poper.show(MsgType.info, "/P2pRoute/1 added!");
        //        Thread.sleep(1000);
        //        Poper.show(MsgType.warning, "add /P2pRoute/1 failed!");
        //        Thread.sleep(1000);
        //        Poper.show(MsgType.error, "add /P2pRoute/1 failed!");
        //        Thread.sleep(1000);
        //        Poper.show(MsgType.info, "/P2pRoute/1 added!");
        //        Thread.sleep(1000);
        //        Poper.show(MsgType.warning, "add /P2pRoute/1 failed!");
        Thread.sleep(20000);
    }
}
