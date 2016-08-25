package com.zte.mos.persistent;

import com.zte.mos.persistent.xml.Table;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class TestPersistent
{
    private static Record ne2;
    private static Record ne1;
    private static Table emsTab;
    private static Table neTab;

    @BeforeClass
    public static void start()
    {

        Record ems = new Record("Ems", "DN");
        ems.add("DN", "/Ems/1");

        ne1 = new Record("Ne", "DN");
        ne1.add("DN", "/Ems/1/Ne/1");

        ne2 = new Record("Ne", "DN");
        ne2.add("DN", "/Ems/1/Ne/2");

        emsTab = new Table();
        emsTab.name = "Ems";

        neTab = new Table();
        neTab.name = "Ne";

        emsTab.add(ems);

        neTab.add(ne1);
        neTab.add(ne2);

        System.out.println(new File("./").getAbsolutePath());
    }

    @Test
    public void testAdd()
    {
        assertTrue(emsTab.write());
        assertTrue(neTab.write());
    }

    @Test
    public void testDel()
    {
        Record ne = new Record("Ne", "DN");
        ne.add("DN", "/Ems/1/Ne/2");
        neTab.remove(ne);
        assertThat(String.valueOf(neTab.rows.get(0).toRecord("NE").get("DN")), is("/Ems/1/Ne/1"));
        neTab.write();
    }
}
