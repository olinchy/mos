package com.zte.mos.tools.ut;

import com.zte.mos.type.IPV4Address;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by zhangbin10086509 on 15-4-24.
 */
public class TestIPV4Addr {
    @Test
    public void test() throws Exception {
        IPV4Address ip = new IPV4Address("12.02.254.1");
        assertThat(ip.toString(), is("12.2.254.1"));
    }

    @Test(expected = AssertionError.class)
    public void test_exception_startWith_0() throws Exception {
        IPV4Address ip = new IPV4Address("0.0.0.1");
    }
    @Test(expected = AssertionError.class)
    public void test_exception_have_255() throws Exception {
        IPV4Address ip = new IPV4Address("1.255.02.1");
    }

    @Test(expected = AssertionError.class)
    public void test_exception_not_match_regex_shorter() throws Exception {
        IPV4Address ip = new IPV4Address("1.0.0");
    }

    @Test(expected = Exception.class)
    public void test_exception_not_match_regex_not_num() throws Exception {
        IPV4Address ip = new IPV4Address("1.a.0.1");
    }
}
