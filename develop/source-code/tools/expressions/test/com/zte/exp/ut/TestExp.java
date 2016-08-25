package com.zte.exp.ut;

import com.zte.exp.mos.Evaluate;
import com.zte.exp.mos.MosBaseListener;
import com.zte.exp.mos.Mos_expParser;
import org.antlr.v4.runtime.misc.NotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class TestExp
{

    private final String exp;

    public TestExp(String exp)
    {
        this.exp = exp;
    }

    @Parameters
    public static Collection<?> paras()
    {
        return Arrays.asList(new Object[][] {
                { "55 >> 2 = 13" },
                { "55 << 2 = 220" },
                { "55 | 67 = 119" },
                { "55 & 67 = 3" },
                { "55 + 1 = 56" },
                { "55 - 1 = 54" },
                { "2 * 3 = 6" },
                { "6 div 3 = 2" },
                { "(134283264 >> 26) & 0xF = 2" },
                { "(134283264 >> 26) & 0xf = 2" },
        });
    }

    @Test
    public void test() throws Exception
    {
        Listener lis = new Listener();
        Evaluate.walk("neid='mw.nr8250=14'", lis);
        assertThat(lis.getValue(), is("'mw.nr8250=14'"));
    }

    @Test
    public void test1() throws Exception
    {
        Listener lis = new Listener();

        Evaluate.walk(exp, lis);
        assertThat(lis.getRes(), is(true));
    }

    class Listener extends MosBaseListener
    {
        private String value;

        @Override
        public void exitDbString(@NotNull Mos_expParser.DbStringContext ctx)
        {
            value = ctx.getText();
            stack.push(value);
        }

        @Override
        public void exitDbexp(@NotNull Mos_expParser.DbexpContext ctx)
        {
            stack.push(ctx.name().getText());
        }

        public String getValue()
        {
            return value;
        }
    }
}
