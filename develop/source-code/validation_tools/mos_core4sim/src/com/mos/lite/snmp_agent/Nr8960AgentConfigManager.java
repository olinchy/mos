package com.mos.lite.snmp_agent;

import org.snmp4j.MessageDispatcher;
import org.snmp4j.agent.AgentConfigManager;
import org.snmp4j.agent.MOServer;
import org.snmp4j.agent.cfg.EngineBootsProvider;
import org.snmp4j.agent.io.MOInputFactory;
import org.snmp4j.agent.io.MOPersistenceProvider;
import org.snmp4j.agent.security.VACM;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.util.WorkerPool;

/**
 * Created by ruitao on 16-7-20.
 */
public class Nr8960AgentConfigManager extends AgentConfigManager
{
    public Nr8960AgentConfigManager(
            OctetString agentsOwnEngineID, MessageDispatcher messageDispatcher,
            VACM vacm, MOServer[] moServers,
            WorkerPool workerPool, MOInputFactory configurationFactory,
            MOPersistenceProvider persistenceProvider,
            EngineBootsProvider engineBootsProvider)
    {
        super(agentsOwnEngineID, messageDispatcher, vacm, moServers, workerPool, configurationFactory,
              persistenceProvider,
              engineBootsProvider);
        sysDescr = new OctetString("{netype:\"ZXMW NR8960\",nesubtype:\"a\", interfaceversion: \"2.04.02.01\", modelversion: \"v242\", modelname: \"NR8960A\"}");
        sysOID = new OID("1.3.6.1.4.1.3902.2400.1.1.9.1");
    }
}
