package com.zte.mos.tools.st;

import com.zte.mos.domain.DN;
import com.zte.mos.httpservice.MosServiceHttp;
import com.zte.mos.util.basic.Log4JRegister;
import org.apache.log4j.PropertyConfigurator;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.URL;
import java.util.Properties;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by olinchy on 16-7-4.
 */
public class Test_long_live_httpclient
{
    @BeforeClass
    public static void setUp() throws Exception
    {
        Properties prop = new Properties();
        prop.put("log4j.rootCategory", "DEBUG, FILE, CONSOLE");
        prop.put("log4j.appender.CONSOLE", "org.apache.log4j.ConsoleAppender");
        prop.put("log4j.appender.CONSOLE.Threshold", "debug");
        prop.put("log4j.appender.CONSOLE.Target", "System.out");
        prop.put("log4j.appender.CONSOLE.layout", "org.apache.log4j.PatternLayout");
        prop.put(
                "log4j.appender.CONSOLE.layout.ConversionPattern",
                "%d{yyyy-MM-dd HH:mm:ss.SSS}[thr:%t] %c{1}: [%p] %m%n");
        prop.put("log4j.appender.FILE", "com.zte.mos.util.basic.ExtendedRollingFileAppender");
        prop.put("log4j.appender.FILE.Threshold", "DEBUG");
        prop.put("log4j.appender.FILE.MaxFileSize", "4MB");
        prop.put("log4j.appender.FILE.MaxBackupIndex", "10");
        prop.put("log4j.appender.FILE.File", "./log.log");
        prop.put("log4j.appender.FILE.layout", "org.apache.log4j.PatternLayout");
        prop.put("log4j.appender.FILE.Append", "true");
        prop.put(
                "log4j.appender.FILE.layout.ConversionPattern",
                "%d{yyyy-MM-dd HH:mm:ss.SSS}[thr:%t] %c{1}: [%p] %m%n");
        PropertyConfigurator.configure(prop);
    }

    @Test
    public void test() throws Exception
    {
        MosServiceHttp client = new MosServiceHttp(new URL("http://192.168.137.1:8089/goform/emsMsg/"));

        while (true)
        {
            logger(this.getClass()).info(client.get(new DN("/")).getMo().get(0));
            Thread.sleep(60000);
        }
    }
}
