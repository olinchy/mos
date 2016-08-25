package com.zte.mos.tools.ut;

import com.zte.mos.inf.Maybe;
import com.zte.mos.type.Pair;
import com.zte.mos.util.tools.JsonUtil;
import org.junit.Test;

/**
 * Created by olinchy on 7/16/14 for MO_JAVA.
 */
public class TestMaybe
{
    @Test
    public void test() throws Exception
    {
        Maybe<String> remote = new Maybe<String>("15");
        Maybe<String> local = new Maybe<String>("20");

        Maybe<String> test = JsonUtil.toObject("{\"value\":\"15\",\"present\":true}", Maybe.class);
        Pair<Maybe<String>, Maybe<String>> sessionId = new Pair<Maybe<String>, Maybe<String>>(remote, local);

        System.out.println(JsonUtil.toString(remote));
    }
}
