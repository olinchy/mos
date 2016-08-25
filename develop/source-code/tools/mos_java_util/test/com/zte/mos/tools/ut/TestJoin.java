package com.zte.mos.tools.ut;

import com.zte.mos.util.tools.joiner.HashMapJoiner;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.zte.mos.type.Pair.pair;
import static com.zte.mos.util.tools.joiner.At.at;
import static com.zte.mos.util.tools.joiner.Select.select;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by olinchy on 16-2-24.
 */
public class TestJoin
{
    @Test
    public void test_inner_join() throws Exception
    {
        List<HashMap<String, Object>> table1 = new ArrayList<HashMap<String, Object>>();
        {
            for (int i = 0; i < 10; i++)
            {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("index", i);
                map.put("name", String.valueOf(i));
                map.put("xxx", "test");
                table1.add(map);
            }
        }
        List<HashMap<String, Object>> table2 = new ArrayList<HashMap<String, Object>>();
        {
            for (int i = 0; i < 3; i++)
            {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("id", i);
                map.put("name", String.valueOf(i));
                map.put("ppl", "ppl" + i);
                table2.add(map);
            }
        }

        List<HashMap<String, Object>> map = HashMapJoiner.join(
                select(table1, "*"), select(table2, "ppl"), at(pair("index", "id")).and(pair("name", "name")));

        assertThat(map.size(), is(3));

        for (HashMap<String, Object> obj : map)
        {
            assertTrue(obj.containsKey("index"));
            assertTrue(obj.containsKey("name"));
            assertTrue(obj.containsKey("ppl"));
            assertTrue(obj.containsKey("xxx"));
            assertThat(obj.keySet().size(), is(4));
            assertThat(String.valueOf(obj.get("ppl")), is("ppl" + obj.get("index")));
        }
    }

    @Test
    public void test_leftJoin() throws Exception
    {
        List<HashMap<String, Object>> table1 = new ArrayList<HashMap<String, Object>>();
        {
            for (int i = 0; i < 10; i++)
            {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("index", i);
                map.put("name", String.valueOf(i));
                map.put("xxx", "test");
                table1.add(map);
            }
        }
        List<HashMap<String, Object>> table2 = new ArrayList<HashMap<String, Object>>();
        {
            for (int i = 0; i < 3; i++)
            {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("id", i);
                map.put("name", String.valueOf(i));
                map.put("ppl", "ppl" + i);
                table2.add(map);
            }
        }

        List<HashMap<String, Object>> map = HashMapJoiner.leftOuterJoin(
                select(table1, "*"), select(table2, "ppl"), at(pair("index", "id")).and(pair("name", "name")));

        assertThat(map.size(), is(10));

        for (HashMap<String, Object> obj : map)
        {
            assertTrue(obj.containsKey("index"));
            assertTrue(obj.containsKey("name"));
            assertTrue(obj.containsKey("ppl"));
            assertTrue(obj.containsKey("xxx"));
            assertThat(obj.keySet().size(), is(4));
        }
    }
}
