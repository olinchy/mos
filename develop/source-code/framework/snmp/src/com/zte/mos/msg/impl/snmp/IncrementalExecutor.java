package com.zte.mos.msg.impl.snmp;

//import com.sun.xml.internal.txw2.output.IndentingXMLStreamWriter;

import com.zte.mos.app.trap.TrapMessage;
import com.zte.mos.app.trap.TrapMessageContainer;
import com.zte.mos.domain.ImagedSystem;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.domain.TargetAddress;
import com.zte.mos.domain.or.MapperPool;
import com.zte.mos.domain.or.MoOrMapper;
import com.zte.mos.domain.or.ModelMapperService;
import com.zte.mos.domain.or.ORRow;
import com.zte.mos.msg.framework.operation.IOperation;
import com.zte.mos.msg.framework.operation.OperEnum;
import com.zte.mos.msg.framework.operation.WriteOperation;
import com.zte.mos.msg.impl.snmp.tofile.DBRecord;
import com.zte.mos.msg.impl.snmp.tofile.DBRecords;
import com.zte.mos.msg.impl.snmp.tofile.XmlJdomWriter;
import com.zte.mos.msg.impl.util.ftp.FtpInfo;
import com.zte.mos.msg.impl.util.ftp.FtpManager;
import com.zte.mos.util.basic.Logger;
import com.zte.ums.uep.api.psl.systemsupport.AccountManagerException;
import com.zte.ums.uep.protocol.snmp.beans.SnmpTarget;
import com.zte.ums.uep.protocol.snmp.snmp2.SnmpException;
import com.zte.ums.uep.protocol.snmp.snmp2.SnmpVar;

import javax.naming.NamingException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Queue;

import static com.zte.mos.util.basic.Logger.logger;
import static com.zte.ums.uep.protocol.snmp.snmp2.SnmpAPI.*;
import static com.zte.ums.uep.protocol.snmp.snmp2.SnmpVar.createVariable;

/**
 * Created by luoqingkai on 15-11-27.
 */
public class IncrementalExecutor
{
    private static Logger log = logger(IncrementalExecutor.class);
    private static final String ftpUserName = "mwRM";
    private static String ftpDir;
    private static Integer currentSeq = 0;
    private final SnmpSession session;
    private final TargetAddress address;
    private final Queue<IOperation> transaction;
    private static int TIME_OUT = 180000;
    private final String fileName;
    private FtpInfo ftp;
    private int transId;
    private int localTransId;

    public IncrementalExecutor(SnmpSession session, Queue<IOperation> transaction, int trasnId, int localTransId)
    {
        this.session = session;
        this.address = session.getMyAddress();
        this.transaction = transaction;
        this.transId = trasnId;
        this.localTransId = localTransId;
        this.fileName = this.address.getIpAddress()+"_"+transId + ".xml";
    }


    public int run()
    {
        DBRecords dbRecords = generateDbRecords();

        if(dbRecords.records.size()==0)
        {
            log.warn("increment, generate DB records, records size is 0");
            return -1;

        }

        generateFile(dbRecords);
        int res =  sendToNe();
        backupFile(ftpDir + this.fileName);
        return res;
    }

    private DBRecords generateDbRecords()
    {

        ModelMapperService service = MapperPool.getModelMapperService(address.getModelHead());

        DBRecords dbRecords = new DBRecords();
        ImagedSystem sys = address.getSystem();

        for (IOperation operation : transaction)
        {
            ManagementObject mo = ((WriteOperation) operation).getMo();
            MoOrMapper mapper = service.getMoMapper(mo.getClass().getSimpleName());

            if(mapper == null)
            {
                log.warn("increment ,get mo mapper is null. mo is"+mo.getClass().getSimpleName());
                continue;

            }

            try
            {
                List<ORRow> rows = mapper.toRow(mo, sys, localTransId);
                for (ORRow row : rows){
                    DBRecord record = new DBRecord();
                    String tableName = mapper.getTableName();
                    record.table = tableName;
                    try {
                        record.opType = toOpType(tableName,operation.getOperation());
                    } catch (Exception e) {
                        log.warn("unknown operation:" + operation.toString());
                        continue;
                    }
                    record.init(row);
                    dbRecords.records.add(record);
                }
            }catch(Throwable ex)
            {
                log.error("increment, ormap exception."+mo.getClass().getSimpleName(),ex);

            }


        }


       return dbRecords;


    }

    private int getCurrentSeq()
    {
        synchronized (currentSeq)
        {
            currentSeq++;
            if (currentSeq >= 65535)
            {
                currentSeq = 0;
            }
            return currentSeq;
        }
    }

    private int sendToNe()
    {
        String[] oids = new String[]{
                ".1.3.6.1.4.1.3902.2400.1.1.1.4.1.1",
                ".1.3.6.1.4.1.3902.2400.1.1.1.4.1.2",
                ".1.3.6.1.4.1.3902.2400.1.1.1.4.1.3",
                ".1.3.6.1.4.1.3902.2400.1.1.1.4.1.4",
                ".1.3.6.1.4.1.3902.2400.1.1.1.4.1.5",
                ".1.3.6.1.4.1.3902.2400.1.1.1.4.1.6",
                ".1.3.6.1.4.1.3902.2400.1.1.1.4.1.7"
        };
        int seq = getCurrentSeq();
        SnmpVar[] vars = new SnmpVar[0];
        try
        {
            vars = new SnmpVar[]{
                    createVariable("0", INTEGER),
                    createVariable(ftp.getIpAddress(), IPADDRESS),
                    createVariable(ftp.getFtpUserName(), STRING),
                    createVariable(ftp.getFtpUserPassWord(), STRING),
                    createVariable(ftp.getPort(), INTEGER),
                    createVariable("cm"+File.separator+"increment"+File.separator + this.fileName, STRING),
                    createVariable(String.valueOf(seq), INTEGER)
            };
        }
        catch (SnmpException e)
        {
            e.printStackTrace();
        }
        SnmpTarget target = session.buildTarget();

        TrapMessage trap = new TrapMessage(
                session.getMyAddress().getIpAddress(),
                ".1.3.6.1.4.1.3902.2400.1.1.9.1.14.1.6.1",
                seq);
        TrapMessageContainer.addTrapMessage(trap);

        int result = SnmpMsgServiceFactory.getService().syncSET(target, oids, vars);

        log.info("***increment, snmp return is "+result+" ***");
       // add trapListener first. because receive trap msg maybe earlier than snmp response
        if(result == 0)
        {
            synchronized (trap)
            {
                try
                {
                    trap.wait(TIME_OUT);
                }
                catch (InterruptedException e)
                {
                    log.info("interrupted " + target.getTargetHost());
                }
                String errorcode = trap.getValue(".1.3.6.1.4.1.3902.2400.1.1.9.1.14.1.6.2");
                if (errorcode != null && !errorcode.equals("0"))
                {
                    log.info("*** increment, get trap "+errorcode+" ***");
                    return  -1;
                }
                else
                {
                    log.info("*** increment, get trap "+errorcode+", success ***");
                    return 0;
                }
            }
        }else{
            return result;
        }

    }

    private String toOpType(String tableName,String operation) throws Exception
    {
        //all vlan related operation(add,del,set), return update
        if(tableName.equalsIgnoreCase("R_VlanCfg"))
        {
            return "U";
        }
        if (operation.equals(OperEnum.Create.name()))
        {
            return "I";
        }
        else if (operation.equals(OperEnum.Delete.name()))
        {
            return "D";
        }
        else if (operation.equals(OperEnum.Update.name()))
        {
            return "U";
        }
        else
        {
            throw new Exception();
        }
    }

    private boolean isSFtp()
    {
        return session.isSecure();
    }

    private String generateFilePath() throws NamingException, AccountManagerException, IOException
    {
        ftp = FtpManager.getFtpInfo(ftpUserName, isSFtp());
        if (ftpDir == null)
        {

            String ftpRootDir = ftp.getUseHome();
            if (!ftpRootDir.endsWith(File.separator))
            {
                ftpRootDir = ftpRootDir + File.separator;
            }
            ftpDir = ftpRootDir + "cm" + File.separator+"increment"+File.separator;
            File tempFile = new File(ftpDir);
            if (!tempFile.exists())
            {
                tempFile.mkdir();
            }
        }

        return ftpDir + this.fileName;
    }




//    private DBRecord writeOneDbRecord(IOperation operation, ModelMapperService service)
//            throws XMLStreamException, IllegalAccessException
//    {
//        DBRecord record = new DBRecord();
//        ManagementObject mo = ((WriteOperation) operation).getMo();
//        MoOrMapper mapper = service.getMoMapper(mo.getClass().getSimpleName());
//        if (mapper == null)
//        {
//            return null;
//        }
//        record.table = mapper.getTableName();
//        try
//        {
//            record.opType = toOpType(operation.getOperation());
//        }
//        catch (Exception e)
//        {
//            return null;
//        }
//        record.init(mapper, mo);
//
//        return record;
//    }

    private void generateFile(DBRecords dbRecords)
    {
        try
        {
            File file = new File(generateFilePath());
            if (!file.exists())
            {
                file.createNewFile();
            }


            XmlJdomWriter.writeXMLFiles(file.getAbsolutePath(),dbRecords);
            log.info("***increment add records***");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void backupFile(String fileName)
    {
        File file = new File(fileName);


        if(file.exists())
        {
            log.info("increment: file backup,"+fileName+"file existed,so delete it first");
            File fileOld = new File(fileName+".backup");
            if(fileOld.exists())
            {
               boolean isDelete =  fileOld.delete();

                if(!isDelete)
                {
                    log.info("increment: file backup,delete existed file failed.");
                    return;
                }
            }



        }

            file.renameTo(new File(fileName+".backup"));


    }

}
