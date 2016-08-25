package com.zte.mos.tools.ut;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static com.zte.mos.util.tools.StringUtil.fnmatch;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by olinchy on 8/29/14.
 */
@RunWith(Parameterized.class)
public class TestPathWithAsterisk
{

    private boolean expect;
    private String define;
    private String dn;

    public TestPathWithAsterisk(boolean expect, String define, String dn)
    {
        this.expect = expect;
        this.define = define;
        this.dn = dn;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data()
    {
        return Arrays.asList(new Object[][]{
                {true, "**/Board/**", "/Ems/1/Ne/2/Board/3/E1Port/1"},
                {true, "/**", "/"},
                {true, "/**", "/Ems/1/Ne/2"},
                {false, "/*", "/Ems/1/Ne/2"},
                {true, "/Ems/1/Ne/*", "/Ems/1/Ne/2"},
                {false, "/Ems/**/2", "/Ems/1/Ne/3"},
                {true, "/Ems/1/Ne/*/*", "/Ems/1/Ne/2/Shelf"},
                {false, "/Ems/1/Ne/*/*", "/Ems/1/Ne/2"},
                {true, "/Ems/1/Ne/[0-9]", "/Ems/1/Ne/2"},
                {true, "/Ems/1/Ne/?", "/Ems/1/Ne/a"},
                {true, "/Ems/1/Ne/?", "/Ems/1/Ne/2"},
                {false, "/Ems/1/Ne/?", "/Ems/1/Ne/21"},
        });

    }

    @Test
    public void test()
    {
        assertThat(expect, is(fnmatch(define, dn)));
    }
}
