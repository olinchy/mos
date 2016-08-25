package com.zte.mos.process.daemon;

import com.zte.mos.util.tools.Prop;
import com.zte.ums.uep.api.ServiceAccess;

import java.io.*;
import java.util.Properties;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by olinchy on 15-5-13.
 */
public class PropertiesLoader
{
    private static Properties properties = null;

    public static Properties load() throws Exception
    {
        if (properties == null)
        {
            loadProperties();
        }
        return properties;
    }

    private static void loadProperties() throws Exception
    {
        properties = new Properties();
        File[] files = ServiceAccess.getSystemSupportService().getFiles("conf.prop");
        if (files == null || files.length == 0)
        {
            logger(PropertiesLoader.class).error("conf.properties does not exist", new Exception(
                    "no_conf.properties"));
            throw new Exception("conf.properties_error");
        }
        for (int i = 0; i < files.length; i++)
        {
            properties.load(new FileInputStream(files[i]));
            properties.setProperty("confpath", files[i].getParent());
            String storage_path = properties.getProperty("storage_path");
            if(storage_path != null && storage_path.length() != 0) {
                properties.setProperty("storage_path", ServiceAccess.getSystemSupportService().getPPUSBaseDir() + storage_path);
            }
        }
        Prop.set(properties);
    }
}
