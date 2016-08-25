package com.zte.mos.util.tools;

import java.io.*;
import java.util.Properties;

import static com.zte.mos.util.basic.Logger.logger;

public class Prop
{

    private static Properties prop = new Properties();

    static
    {
        try
        {
            prop.load(new FileInputStream(new File("conf.properties")));
        } catch (IOException e)
        {
            logger(Prop.class).info("conf.properties not found");
        }
    }

    public static String get(String key)
    {
        return prop.getProperty(key);
    }

    public static void set(Properties properties)
    {
        prop = properties;
    }

    public static Properties getProp()
    {
        return prop;
    }

}
