package com.zte.mos.logging_service;

import com.zte.mos.util.tools.Prop;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.util.Properties;

/**
 * Created by olinchy on 16-7-21.
 */
public class Main
{
    public static void main(
            String[] args) throws IOException, NotBoundException
    {
        Properties pop = new Properties();
        pop.load(new FileInputStream(new File("log4j.properties")));
        PropertyConfigurator.configure(pop);

        LocateRegistry.createRegistry(Integer.parseInt(Prop.get("loggingservice_port")));
        Naming.rebind(Prop.get("loggingservice"), new LoggingServiceImpl());

        new MonitorFrame().setVisible(true);
    }
}
