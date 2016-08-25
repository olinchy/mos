package com.zte.mos.tools.st;

import com.zte.mos.domain.DN;
import com.zte.mos.httpservice.Config;
import com.zte.mos.httpservice.MosServiceHttp;
import com.zte.mos.message.ConfResult;
import com.zte.mos.message.ConfResultSet;
import com.zte.mos.message.Mo;
import com.zte.mos.message.Result;
import org.apache.log4j.PropertyConfigurator;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.*;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by olinchy on 15-7-2.
 */
public class TestMass_100000
{
    private static final int CYCLE = 4000;
    private static final int ThreadCount = 5;

    @BeforeClass
    public static void setup()
    {
//        log4j.appender.FILE=com.zte.mos.util.basic.ExtendedRollingFileAppender
//        log4j.appender.FILE.Threshold=DEBUG
//        log4j.appender.FILE.MaxFileSize=4MB
//        log4j.appender.FILE.MaxBackupIndex=10
//        log4j.appender.FILE.File=./log/log.log
//        log4j.appender.FILE.layout=org.apache.log4j.PatternLayout
//        log4j.appender.FILE.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss.SSS}[thr:%t] %c{1}: [%p] %m%n
//        log4j.appender.FILE.Append=true
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
    public void test() throws Exception
    {
        logger(this.getClass()).info(" starting to create " + CYCLE * ThreadCount + " ne");
        final int[] value = {CYCLE * ThreadCount};
        Observer ob = new Observer()
        {
            @Override
            public void update(Observable o, Object arg)
            {
                value[0]--;
            }
        };
        for (int x = 0; x < 1; x++)
        {
            for (int i = 0; i < ThreadCount; i++)
            {
                new CreateNeThread(i, ob).start();
            }
        }

        new Timer().schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                logger(this.getClass()).info(value[0] + " ne to create-----  " + new Date());
            }
        }, 10000, 60000);

        while (true)
        {
            Thread.sleep(1000);
            if (value[0] == 0)
            {
                return;
            }
        }
    }

    private void createNe(int index) throws Exception
    {
        MosServiceHttp service = new MosServiceHttp();
        try
        {
            DN nedn = new DN("/Ems/1/Ne/" + index);
            String ipAddress = new StringBuffer("123.").append(index / 255 / 255).append(".").
                    append(index / 255 % 255).append(".").append(index % 255).toString();
            DN ipdn = new DN(String.format("/Ems/1/IpV4/%s", ipAddress));

            Date date = new Date();
            print(index, date, "start creating " + index);
            service.startTransaction();
            Result res = service.configs(
                    Config.ConfigMethods.add(new Mo("IpV4").setDn(ipdn), new Mo("Ne").setDn(nedn).setAttr(
                            "ipV4", ipdn.toString()).setAttr("netype", "nr8960")));
            if (!res.isSuccess())
            {
                for (ConfResult o : ((ConfResultSet) res).getMo())
                {
                    if (!o.isSuccess())
                    {
                        o.exception().printStackTrace();
                    }
                }
                throw new Exception();
            }
            print(index, date, "finish creating " + index);
            service.commit();
            print(index, date, "finish committing");
        }
        catch (Throwable e)
        {
            service.rollback();
        }
    }

    private void print(int index, Date date, String message)
    {
        if (index % 100 == 1)
        {
            System.out.println("      " + message + " cost " + (new Date().getTime() - date.getTime()));
        }
    }

    @Test
    public void test_add_ne() throws Exception
    {
        createNe(1);
    }

    private class CreateNeThread extends Thread
    {
        private final Observer ob;
        private int startIndex;
        private Date date;

        public CreateNeThread(int i, Observer ob)
        {
            this.startIndex = i * CYCLE;
            this.ob = ob;
        }

        @Override
        public void run()
        {
            System.out.println(
                    "starting " + startIndex + "-" + (startIndex + CYCLE - 1) + " at " + new SimpleDateFormat(
                            "HH:mm:ss").format(date = new Date()));
            for (int j = 0; j < CYCLE; j++)
            {
                Date temp = new Date();
                try
                {
                    createNe(startIndex);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                ob.update(null, null);
                startIndex++;
                if (j % (CYCLE / 10) == 0)
                {
                    System.out.println(
                            "   add the " + startIndex + "th of thousand, cost " + (new Date().getTime() - temp.getTime()));
                }
            }
            System.out.println(
                    "finished " + startIndex + "-" + (startIndex + CYCLE - 1) + ", consume " + String.valueOf(
                            new Date().getTime() - date.getTime()) + " at " +
                            new SimpleDateFormat(
                                    "HH:mm:ss").format(new Date()));
        }
    }
}
