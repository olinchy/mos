package com.zte.mos.type;

import com.zte.mos.util.tools.JsonUtil;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by olinchy on 15-10-14.
 */
public class TestRange
{
    @Test
    public void test() throws Exception
    {
        Range vlan = new Range("1-4", "6", "8-21");

        assertThat(vlan.toString(), is("[[1,4],[6],[8,21]]"));

        vlan.add("22");

        assertThat(vlan.toString(), is("[[1,4],[6],[8,22]]"));

        vlan.del("7-8", "11");

        assertThat(vlan.toString(), is("[[1,4],[6],[9,10],[12,22]]"));

        assertTrue(vlan.contains("21"));

        assertTrue(vlan.contains("21", "13-15"));

        assertFalse(vlan.contains("11-12"));

        Range range = new Range(new RangeExp("[[1,5],[7,21]]"));

        assertTrue(range.contains("20"));

        Range testPattern = new Range(new RangeExp("[[1,4],[8],[11,15]]"));

        assertTrue(!testPattern.contains("7"));
    }

    @Test
    public void test_serialize_and_deserialize() throws Exception
    {
        Range range = new Range("1-4", "6", "8-21");

        Range toCheck = JsonUtil.toObject(JsonUtil.toString(range), Range.class);

        assertTrue(range.toString().equals(toCheck.toString()));
    }

    @Test
    public void test_pattern() throws Exception
    {
        Pattern pattern = Pattern.compile("\\[([\\d])+(,)([\\d]+)\\]");
        Matcher matcher = pattern.matcher("[[1,4],[6],[7,21]]");

        String a;
        assertThat(a = matcher.replaceAll("$1-$3"), is("[1-4,[6],7-21]"));

        pattern = Pattern.compile("\\[([\\d]+)\\]");
        matcher = pattern.matcher(a);

        a = matcher.replaceAll("$1");

        assertThat(a, is("[1-4,6,7-21]"));
    }

    @Test
    public void test_range_exp() throws Exception
    {
        RangeExp rangeExp = new RangeExp("[[1, 4], [6], [7, 21]]");

        assertThat(rangeExp.toArray(), is(new String[]{"1-4", "6", "7-21"}));
    }

    @Test
    public void test_empty_range_exp() throws Exception
    {
        Range range = new Range(new RangeExp(null));

        assertThat(range.toString(), is(""));
    }

    @Test
    public void test_toArray() throws Exception
    {
        Range range = new Range("1-5");

        long[] array = range.toArray();

        assertThat(new long[]{1, 2, 3, 4, 5}, is(array));
    }

    @Test
    public void test_contains() throws Exception
    {
        Range range = new Range("3-4");

        Range range1 = new Range("3-6");

        assertTrue(range1.contains(range));

        assertFalse(range.contains(new Range("3-6")));
    }

    @Test
    public void test_add() throws Exception
    {
        Range range = new Range("1");
        range.add(new Range("3-5"));

        assertTrue(range.contains("4"));

        range.add(new Range("2-3"));

        assertTrue(range.contains("4"));
        assertTrue(range.contains("2"));
        assertTrue(range.contains("1"));
    }

    @Test
    public void test_intersects() throws Exception
    {
        assertTrue(new Range("3-4").intersects(new Range("2-6")));
        assertTrue(new Range("3-4").intersects(new Range("3-6")));
        assertTrue(new Range("3-4").intersects(new Range("1-5")));
        assertTrue(new Range("3-4").intersects(new Range("3-4")));

        assertFalse(new Range("3-4").intersects(new Range("6-7")));
    }
}
