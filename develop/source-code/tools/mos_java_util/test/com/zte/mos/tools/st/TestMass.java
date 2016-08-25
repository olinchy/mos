package com.zte.mos.tools.st;

import com.zte.mos.domain.DN;
import com.zte.mos.exception.MOSException;
import com.zte.mos.httpservice.MosServiceHttp;
import com.zte.mos.message.Mo;
import com.zte.mos.message.Result;
import org.apache.log4j.PropertyConfigurator;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Date;
import java.util.Properties;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by olinchy on 3/23/15 for mosjava.
 */
public class TestMass
{
    private static MosServiceHttp service;

    @BeforeClass
    public static void setUpClass() throws MOSException
    {
        service = new MosServiceHttp();
        Properties prop = new Properties();
        prop.put("log4j.rootCategory", "DEBUG, FILE, CONSOLE");
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
    public void test_find_mass() throws MOSException
    {
        // TODO add for debug, should delete afterward
        logger(this.getClass()).info(String.valueOf(new Date().getTime()));
        long time = new Date().getTime();
        Result<Mo> res = service.find("", "**/CesRoute/*", new DN("/"));
        //        assertThat(res.getRetObjs().size(), is(101));
        logger(this.getClass()).info(String.valueOf(new Date().getTime() - time));
        logger(this.getClass()).info(String.valueOf(res.getMo().size()));
    }

    public void find_all_mo() throws Exception
    {
        Date starting = new Date();

        int groupCounter = 0;
        Result<Mo> result = service.find("", "");
        logger(this.getClass()).info(
                "find all mo finished after " + String.valueOf(new Date().getTime() - starting.getTime()) + " ms");

        logger(this.getClass()).info(result.getMo().size() + " mo in mos-ems");

        for (Mo mo : result.getMo())
        {
            if (mo.getMoClass().equals("Group"))
            {
                groupCounter++;
            }
        }

        logger(this.getClass()).info(groupCounter + " group mo in mos-ems");

        Result<Mo> result_ne = service.find("", "Ne");
        logger(this.getClass()).info(
                "find all ne finished after " + String.valueOf(new Date().getTime() - starting.getTime()) + " ms");

        logger(this.getClass()).info(result_ne.getMo().size() + " ne in mos-ems");
    }

    @Test
    public void timer() throws Exception
    {
        new Thread()
        {
            @Override
            public void run()
            {
                while (true)
                {
                    try
                    {
                        find_all_mo();

                        sleep(60000);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        Thread.sleep(48 * 60 * 60 * 1000);
    }
}
