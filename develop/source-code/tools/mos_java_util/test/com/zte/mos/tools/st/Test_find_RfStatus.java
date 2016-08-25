package com.zte.mos.tools.st;

import com.zte.mos.domain.DN;
import com.zte.mos.httpservice.MosServiceHttp;
import com.zte.mos.message.Mo;
import com.zte.mos.message.Result;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by olinchy on 16-4-13.
 */
public class Test_find_RfStatus
{
    @Test
    public void test() throws Exception
    {

        MosServiceHttp service = new MosServiceHttp("10.86.95.13");

        Result<Mo> result = service.find(
                "RfStatus.link = 'up'", "RfStatus",
                new DN("/Ems/1/Ne/14/Product/1/Shelf/1/Board/5/AirPort/1/RfStatus/1"));

        assertTrue(result.getMo().size() > 0);
    }
}
