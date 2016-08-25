package com.zte.mos.msg;

import com.zte.mos.inf.Maybe;
import com.zte.mos.message.LsResult;
import com.zte.mos.util.tools.JsonUtil;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by olinchy on 5/5/15.
 */
public class TestLSResult2Json
{
    @Test
    public void test() throws Exception
    {
        ArrayList<String> stringArraylist = new ArrayList<String>();
        LsResult res = new LsResult(new Maybe<Integer>(1), stringArraylist);
        stringArraylist.add("Shelf");
        stringArraylist.add("P2pRoute");

        res.setResult(0);

        String json = JsonUtil.toString(res);

        System.out.println(json);

        assertThat(json, is("{\"transId\":{\"value\":1,\"present\":true},\"result\":0," +
                "\"children\":[\"Shelf\",\"P2pRoute\"]}"));

    }
}
