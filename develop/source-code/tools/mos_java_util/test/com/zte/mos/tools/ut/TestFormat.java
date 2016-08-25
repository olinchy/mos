package com.zte.mos.tools.ut;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by olinchy on 3/19/15 for mosjava.
 */
public class TestFormat
{
    @Test
    public void test() throws Exception
    {
        String value = String.format("http://%1$s:54321", "localhost");

        assertThat(value, is("http://localhost:54321"));

    }
}
