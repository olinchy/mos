package com.zte.mos.tools.ut;

import com.zte.mos.domain.DN;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

public class TestDN
{

    @Test
    public void test() throws Exception
    {
        DN dn = new DN("/Ems/1/Ne/mw.nr8250=4/Shelf/1/TrafficUnit/0/VE1Port/2");

        assertThat(dn.to("Ne").toString(), is("/Ems/1/Ne/mw.nr8250=4"));
        assertThat(dn.to("Shelf").toString(), is("/Ems/1/Ne/mw.nr8250=4/Shelf/1"));
        assertThat(dn.to("TrafficUnit").toString(),
                is("/Ems/1/Ne/mw.nr8250=4/Shelf/1/TrafficUnit/0"));
        assertThat(dn.to("Ems").toString(), is("/Ems/1"));
        assertThat(dn.to("VE1Port").toString(),
                is("/Ems/1/Ne/mw.nr8250=4/Shelf/1/TrafficUnit/0/VE1Port/2"));
    }

    @Test
    public void test_parent() throws Exception
    {
        DN dn = new DN("/Ems");
        assertThat(dn.parent().toString(), is("/"));

    }

    @Test
    public void test_compare() throws Exception
    {
        assertThat(new DN("/CesRoute/").compareTo(new DN("/CesRoute/156")), not(0));
        assertTrue(new DN("/CesRoute/").compareTo(new DN("/CesRoute/156")) > 0);

        assertTrue(new DN("/Shelf/1/TrafficUnit").compareTo(new DN("/Shelf/2/TrafficUnit")) < 0);

    }

    @Test
    public void test_deeper() throws Exception
    {
        assertFalse(new DN("/*/*/CesService/*").deeperThan(new DN("/Ems/1/Ne/1")));

        assertTrue(new DN("/*/*/Ne/*/CesRoute/*").deeperThan(new DN("/Ems/1/Ne/1")));
    }

    @Test
    public void test_isOffspring() throws Exception
    {
        DN a = new DN("/Ems/1");
//        DN b = new DN("/Ems/1/Ne/1");
//        DN c = new DN("/Ems/112/Ne/3");
//
//        DN d = new DN("/Ems/112");
//
//        assertTrue(b.isOffspringOf(a));
//        assertFalse(c.isOffspringOf(a));
//
//
//        assertTrue(c.isOffspringOf(d));
//        assertFalse(b.isOffspringOf(d));

        DN f = new DN("/");
        assertTrue(a.isOffspringOf(f));
    }
}
