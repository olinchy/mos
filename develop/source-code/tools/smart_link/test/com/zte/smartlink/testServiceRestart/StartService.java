package com.zte.smartlink.testServiceRestart;

import com.zte.mos.exception.MOSException;
import com.zte.mos.smartlink.addressbook.service.StartSmartlinkServer;
import com.zte.mos.util.basic.Log4JRegister;
import com.zte.mos.util.tools.Prop;
import org.apache.log4j.PropertyConfigurator;

import java.util.Properties;

/**
 * Created by olinchy on 16-1-13.
 */
public class StartService
{
    public static void main(String[] args) throws MOSException
    {
        Properties prop = Log4JRegister.init();
        prop.put("storage_path", "./");

        PropertyConfigurator.configure(prop);
        Prop.set(prop);
        StartSmartlinkServer.start("4000", "//127.0.0.1:4000/addressService");
    }
}
