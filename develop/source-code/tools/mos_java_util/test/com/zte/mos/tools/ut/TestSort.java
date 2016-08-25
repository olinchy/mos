package com.zte.mos.tools.ut;

import com.zte.mos.util.Sorter;
import com.zte.mos.util.tools.IntegerGreaterThan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by olinchy on 7/25/14 for MO_JAVA.
 */
@RunWith(Parameterized.class)
public class TestSort
{
    private Integer[] toSort;
    private Integer[] expect;

    public TestSort(Integer[] toSort, Integer[] expect)
    {
        this.toSort = toSort;
        this.expect = expect;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data()
    {
        return Arrays.asList(new Object[][]
                {
                        { new Integer[] { 8, 5, 6, 2, 1, 4 }, new Integer[] { 1, 2, 4, 5, 6, 8 } },
                        { new Integer[] { 8, 5, 6, 2, 1, 4,3,9 }, new Integer[] { 1, 2, 3, 4, 5, 6, 8,9 } },
                });

    }

    @Test
    public void test() throws Exception
    {
        Sorter.asc(toSort, new IntegerGreaterThan());
        assertThat(toSort, is(expect));
    }

}
