package com.zte.mos.tools.ut;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zte.mos.domain.DN;
import com.zte.mos.exception.MOSException;
import com.zte.mos.exception.NonvalidKeywordException;
import com.zte.mos.httpservice.MosServiceHttp;
import com.zte.mos.inf.Maybe;
import com.zte.mos.message.*;
import com.zte.mos.util.tools.JsonUtil;
import org.apache.log4j.PropertyConfigurator;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Properties;

import static com.zte.mos.util.tools.JsonUtil.toObject;
import static org.junit.Assert.assertTrue;

/**
 * Created by olinchy on 15-9-23.
 */
public class Test_serialize_results
{
    @BeforeClass
    public static void setUp() throws Exception
    {
//        log4j.appender.CONSOLE=org.apache.log4j.ConsoleAppender
//        log4j.appender.CONSOLE.Threshold=debug
//        log4j.appender.CONSOLE.Target=System.out
//        log4j.appender.CONSOLE.layout=org.apache.log4j.PatternLayout
//        log4j.appender.CONSOLE.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss.SSS}[thr:%t] %c{1}: [%p] %m%n

        Properties prop = new Properties();
        prop.put("log4j.rootCategory", "DEBUG, FILE, CONSOLE");
        prop.put("log4j.appender.CONSOLE", "org.apache.log4j.ConsoleAppender");
        prop.put("log4j.appender.CONSOLE.Threshold", "debug");
        prop.put("log4j.appender.CONSOLE.Target", "System.out");
        prop.put("log4j.appender.CONSOLE.layout", "org.apache.log4j.PatternLayout");
        prop.put(
                "log4j.appender.CONSOLE.layout.ConversionPattern",
                "%d{yyyy-MM-dd HH:mm:ss.SSS}[thr:%t] %c{1}: [%p] %m%n");

        PropertyConfigurator.configure(prop);
    }

    @Test
    public void test_single_set_serialize_and_deserialize() throws Exception
    {
        ConfResultSet set = new ConfResultSet();

        ConfResultSet set1 = toObject(
                "{\"mo\":[{\"ex\":{\"message\":\"f_mainSlot value is 0 now,  should between 1-180\",\"errorCode\":{\"errorCode\":1},\"className\":\"com.zte.mos.annotation.ValidateException\",\"suppressed\":[]},\"transId\":1,\"result\":1}],\"transId\":1,\"result\":1}",
                ConfResultSet.class);

        ConfResult res1 = toObject(
                "{\"ex\":{\"message\":\"f_mainSlot value is 0 now,  should between 1-180\",\"errorCode\":{\"errorCode\":1},\"className\":\"com.zte.mos.annotation.ValidateException\",\"suppressed\":[]},\"transId\":1,\"result\":1}",
                ConfResult.class);

        assertTrue(!set1.isSuccess());
    }

    public class AAA
    {
        public AAA()
        {
        }

        @JsonProperty("mo")
        public ArrayList<ConfResult> mo = new ArrayList<ConfResult>();
    }

    @Test
    public void test_1() throws Exception
    {

        ClassA a = new ClassA();
        a.transId = new Maybe<Integer>(null);

        ClassA a1 = JsonUtil.toObject(JsonUtil.toString(a), ClassA.class);

        System.out.println(a1.toString());
    }

    @Test
    public void test() throws Exception
    {
        Successful successful = new Successful(new Maybe<Integer>(null));

        String a;
        System.out.println(a = JsonUtil.toString(successful));

        Successful b = JsonUtil.toObject(a, Successful.class);

        System.out.println(b);

        String a1;
        System.out.println(a1 = JsonUtil.toString(new Maybe<Integer>(null)));

        Maybe<Integer> b1 = JsonUtil.toObject(a1, Maybe.class);

        System.out.println(b1);
    }

    @Test
    public void test_findresult() throws Exception
    {
        FindResult findResult = new FindResult(new Maybe<Integer>(null));

        String a;
        System.out.println(a = JsonUtil.toString(findResult));

        FindResult b = JsonUtil.toObject(a, FindResult.class);

        System.out.println(b);
    }

    @Test
    public void test_ls_result() throws Exception
    {

        LsResult result = new LsResult();
        result.setResult(1);
        result.setEx(new MOSException());

        LsResult result1 = JsonUtil.toObject(JsonUtil.toString(result), LsResult.class);

        assertTrue(!result1.isSuccess());
        System.out.println(result1);
    }

    @Test
    public void test_serialize_maybe() throws Exception
    {
        Maybe<Integer> maybe1 = new Maybe<Integer>(null);

        Maybe maybe2 = JsonUtil.toObject(JsonUtil.toString(maybe1), Maybe.class);

        assertTrue(maybe2.nothing());
    }

    @Test
    public void test_failure() throws Exception
    {
        Result result = new Failure(1, new NonvalidKeywordException(), new Maybe<Integer>(null));

        Result result1 = JsonUtil.toObject(JsonUtil.toNode(result).toString(), Failure.class);
        assertTrue(!result1.isSuccess());
    }

    @Test
    public void test_in_mos() throws Exception
    {
        Result result = new MosServiceHttp().ls(new DN("/Ems/1/MM"));

        assertTrue(!result.isSuccess());
    }
}
