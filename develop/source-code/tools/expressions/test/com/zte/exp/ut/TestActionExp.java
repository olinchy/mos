package com.zte.exp.ut;

import com.zte.exp.action.ActionDesc;
import com.zte.exp.action.ActionEvaluate;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TestActionExp
{

    @Test
    public void testTree() throws Exception
    {
        String exp = "tree(root:/Ems/1, depth:2)";
        ActionDesc act = ActionEvaluate.evaluate(exp);

        assertThat(act.actionName, is("tree"));
        assertThat(act.paras.get(0), is(new String[] { "root", "/ Ems / 1" }));
        assertThat(act.paras.get(1), is(new String[] { "depth", "2" }));
    }

    @Test
    public void testSelect() throws Exception
    {
        String exp = "select(Ne.dn like /Ems/1/Ne/*)";
        ActionDesc act = ActionEvaluate.evaluate(exp);

        assertThat(act.actionName, is("select"));
    }

    @Test
    public void testVersion() throws Exception
    {
        String exp = "version(ne(/Ems/1/Ne/2))";
        ActionDesc act = ActionEvaluate.evaluate(exp);

        assertThat(act.actionName, is("version"));
    }
}
