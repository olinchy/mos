package com.zte.mos.modb;

import com.zte.exp.mos.Evaluate;
import com.zte.mos.modb.stub.FakeEms;
import com.zte.mos.tools.MOSListener;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TestMoListener
{

    @Test
    public void test() throws Exception
    {
        Evaluate.evaluate("mo.dn like /Ems/1", new MOSListener(new FakeEms(), null));
    }

    @Test
    public void test_contains() throws Exception
    {
        FakeNode fakeNode = new FakeNode();
        fakeNode.nes.add("/Ems/1/Ne/1");
        boolean expect = Evaluate.evaluate("mo.nes contains '/Ems/1/Ne/1'", new MOSListener(fakeNode, null));

        assertTrue(expect);

    }
}
