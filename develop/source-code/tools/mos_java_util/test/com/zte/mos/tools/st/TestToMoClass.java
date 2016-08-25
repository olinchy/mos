package com.zte.mos.tools.st;

import com.zte.mos.domain.DN;
import com.zte.mos.exception.MOSException;
import com.zte.mos.httpservice.MosServiceHttp;
import org.junit.Test;

/**
 * Created by olinchy on 15-5-12.
 */
public class TestToMoClass
{
    @Test
    public void test() throws MOSException
    {
        MosServiceHttp serviceHttp = new MosServiceHttp();

        serviceHttp.get(new DN("/Ems/1/Ne/2"));

    }
}
