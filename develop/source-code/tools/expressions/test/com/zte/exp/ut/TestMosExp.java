package com.zte.exp.ut;

import com.zte.exp.mos.Evaluate;
import com.zte.exp.mos.MosBaseListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class TestMosExp
{

    private final Boolean res;
    private final String exp;

    public TestMosExp(String exp, boolean res)
    {
        this.exp = exp;
        this.res = res;
    }

    @Parameters
    public static Collection<?> para()
    {
        return Arrays.asList(new Object[][]
                {
                        { "5 = 5 and 4=4 and 6=6", true },
                        { "5 = 6 or 4=5 or 6=6", true },
                        { "5 = 6 or 4=4 and 6=6", true },
                        { "5 = 6 or 4=5 and 6=6", false },
//                        {"/Ems/1/CesService/1) like /*/CesService/*", true},
                });
    }

    @Test
    public void test() throws Exception
    {
        assertThat(Evaluate.evaluate(exp, new MosBaseListener()), is(res));
    }
}
