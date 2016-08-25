package com.zte.mos.tools.ut;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by olinchy on 16-5-23.
 */
@RunWith(Parameterized.class)
public class ToChar
{
    private String local;

    public ToChar(String local)
    {
        this.local = local;
    }

    @Test
    public void toChar() throws Exception
    {
        StringBuilder buffer = new StringBuilder();
        for (String s : local.split(","))
        {
            buffer.append((char) Integer.parseInt(s, 16));
        }

        System.out.println(buffer.toString());
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data()
    {
        return Arrays.asList(
                new Object[]{"68,74,74,70,3a,2f,2f,31,30,2e,38,36,2e,39,35,2e,31,32,3a,35,35,33,32,31,2f,31,30,5f,38,36,5f,39,36,5f,33,35,5f,70,72,6f,78,79,2e,70,61,63"},
                new Object[]{"68,74,74,70,3a,2f,2f,31,30,2e,38,36,2e,39,35,2e,31,32,3a,35,35,33,32,31,2f,31,30,5f,38,36,5f,33,38,5f,32,31,5f,70,72,6f,78,79,2e,70,61,63"},
                new Object[]{"25,00,55,00,53,00,45,00,52,00,50,00,52,00,4f,00,46,00,49,00,4c,00,45,00,25,00,5c,00,41,00,70,00,70,00,44,00,61,00,74,00,61,00,5c,00,4c,00,6f,00,63,00,61,00,6c,00,5c,00,4d,00,69,00,63,00,72,00,6f,00,73,00,6f,00,66,00,74,00,5c,00,57,00,69,00,6e,00,64,00,6f,00,77,00,73,00,5c,00,48,00,69,00,73,00,74,00,6f,00,72,00,79,00,5c,00,48,00,69,00,73,00,74,00,6f,00,72,00,79,00,2e,00,49,00,45,00,35,00,5c,00,4d,00,53,00,48,00,69,00,73,00,74,00,30,00,31,00,32,00,30,00,31,00,36,00,30,00,35,00,32,00,33,00,32,00,30,00,31,00,36,00,30,00,35,00,32,00,34,00,00,00"});
    }
}

