package com.zte.mos.tools.st;

import com.zte.mos.annotation.ValidateException;
import com.zte.mos.domain.DN;
import com.zte.mos.exception.AckException;
import com.zte.mos.httpservice.MosServiceHttp;
import com.zte.mos.inf.Maybe;
import com.zte.mos.message.AckResult;
import com.zte.mos.message.Mo;
import com.zte.mos.util.basic.Log4JRegister;
import com.zte.mos.util.msg.MoAckMsg;
import com.zte.mos.util.tools.JsonUtil;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by olinchy on 16-4-12.
 */
public class TestCommit
{
    @BeforeClass
    public static void setUp() throws Exception
    {
        Log4JRegister.init();
    }

    @Test
    public void test_commit_results() throws Exception
    {
        MosServiceHttp service = new MosServiceHttp();
        service.set(new Mo("IpV4").setDn(new DN("/Ems/1/IpV4/10.0.0.2")).setAttr("ip_addr", "10.0.0.2"));

        System.out.println("");
    }

    @Test
    public void test_to_actResult() throws Exception
    {
        AckResult result = new AckResult(new Maybe<Integer>(1));
        AckResult afterDeserialize = JsonUtil.toObject(JsonUtil.toString(result), AckResult.class);

        assertTrue(afterDeserialize != null);

        assertThat(afterDeserialize.getResult(), is(0l));
        assertThat(afterDeserialize.getMo().size(), is(0));

        assertTrue(afterDeserialize.exception() == null);
    }

    @Test
    public void test_deserialize_ack_result_with_exception() throws Exception
    {
        AckResult result = new AckResult(new Maybe<Integer>()).push(
                new AckException(new ValidateException("testing ack result with exception"), "/Ems/1/Ne/1"));

        AckResult afterDeserialize = JsonUtil.toObject(JsonUtil.toString(result), AckResult.class);

        assertFalse(afterDeserialize.isSuccess());
        assertThat(afterDeserialize.getMo().size(), is(1));

        assertThat(afterDeserialize.getMo().get(0).getException().getMessage(), is("testing ack result with exception"));
    }
}
