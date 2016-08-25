package com.zte.mos.util.basic;

import org.apache.log4j.PropertyConfigurator;

import java.util.Properties;

/**
 * Created by olinchy on 16-1-13.
 */
public class Log4JRegister
{
    public static Properties init()
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
        PropertyConfigurator.configure(prop);

        return prop;
    }
}
