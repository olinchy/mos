package com.mos.lite.process_starter;

import com.mos.lite.Mos;
import com.mos.lite.http_service.RpcStarter;
import com.mos.lite.snmp_agent.SNMP4JAgent;
import com.zte.mos.annotation.MoEnum;
import com.zte.mos.domain.MetaStringStore;
import com.zte.mos.exception.MOSException;
import com.zte.mos.type.CommonTypeRegister;
import com.zte.mos.util.Scan;
import com.zte.mos.util.basic.Logger;
import com.zte.mos.util.scaner.MoEnumScanner;
import com.zte.mos.util.scaner.PreLoadScanner;
import com.zte.mos.util.tools.Prop;
import org.apache.log4j.PropertyConfigurator;
import org.snmp4j.security.SecurityProtocols;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;

import static com.zte.mos.util.Singleton.getInstance;

/**
 * Created by olinchy on 15-11-19.
 */
public class Main
{
    static Logger log = Logger.logger(Main.class);

    public static void main(String[] args) throws Exception
    {
        initLog();

        if (startSnmp())
            startSNMPAgent();

        loadMeta();

        searchAction();

        CommonTypeRegister.regAll();

        startMos(args[0], args[1], args[2]);
        new RpcStarter();

        log.info("ne simulator started! have some more fun!");
    }

    private static boolean startSnmp()
    {
        return Prop.get("start_snmp") != null ? Boolean.valueOf(Prop.get("start_snmp")) : false;
    }

    private static void startSNMPAgent() throws IOException
    {
        SNMP4JAgent sa = new SNMP4JAgent();
        SecurityProtocols.getInstance().addDefaultProtocols();
        sa.run();
    }

    private static void startMos(String rootClassName, String modelType, String modelVersion) throws Exception
    {
        Mos mos = new Mos(rootClassName, modelType, modelVersion);
        Mos.setInstance(mos);
    }

    private static void searchAction()
    {
        // TODO: 15-11-19
    }

    private static void initLog() throws IOException
    {
        Properties pop = new Properties();
        pop.load(new FileInputStream(new File("log4j.properties")));
        PropertyConfigurator.configure(pop);
    }

    private static void loadMeta() throws MOSException
    {
        Set<Class<Object>> set = new LinkedHashSet<Class<Object>>();
        set.addAll(Scan.getClasses("com.zte.mos.autogen"));
        set.addAll(Scan.getClasses("com.zte.app.smartlink"));

        new MoEnumScanner().scan(Scan.getClasses("com.zte.mos.autogen", MoEnum.class));
        getInstance(MetaStringStore.class);
        new PreLoadScanner().scan(set);

        CommonTypeRegister.regAll();
        log.info("meta related initialized.");
    }
}
