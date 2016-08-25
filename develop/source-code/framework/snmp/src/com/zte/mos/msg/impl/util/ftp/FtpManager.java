package com.zte.mos.msg.impl.util.ftp;

import com.zte.mos.domain.DN;
import com.zte.mos.exception.MOSException;
import com.zte.mos.exception.NullMoException;
import com.zte.mos.httpservice.MosServiceHttp;
import com.zte.mos.message.Mo;
import com.zte.mos.message.Result;
import com.zte.ums.uep.api.SFtpContext;
import com.zte.ums.uep.api.ServiceAccess;
import com.zte.ums.uep.api.psl.systemsupport.AccountInfo;
import com.zte.ums.uep.api.psl.systemsupport.AccountManagerException;
import com.zte.ums.uep.api.psl.systemsupport.FtpAccountInfo;
import com.zte.ums.uep.api.psl.systemsupport.SystemAccountManager;
import com.zte.ums.uep.protocol.ftpclient.api.FtpContext;
import com.zte.ums.uep.protocol.ftpclient.api.FtpUserContext;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by zhangbin10086509 on 15-6-5.
 */
public class FtpManager
{

    private static final int SFTP_ENABLE = 1;
    private static final String NE= "/Ems/1/Ne/${neId}";
    private static final String SNMPV3 = "/Ems/1/Ne/${neId}/Communication/snmpV3";
    private static final String SFTP = "/Ems/1/Ne/${neId}/Communication/sftp";


    public static FtpInfo getFtpInfo(String ftpName, boolean isSftp) throws AccountManagerException, NamingException {
        ftpName = ftpName.trim();
        FtpInfo[] ftpInfos = null;
        SystemAccountManager sysManager = (SystemAccountManager) ServiceAccess.lookup(SystemAccountManager.ROLE);
        FtpAccountInfo[] ftpAccountInfos = sysManager.getFtpAccounts();
        for(int i = 0; i < ftpAccountInfos.length; i++){
            AccountInfo userAccountInfo = ftpAccountInfos[i].getFtpAccount();
            String ftpUserName = userAccountInfo.getUser();
            if(ftpName.equals(ftpUserName)){
                if(isSftp){
                    SFtpContext context = getSFTPContext(ftpUserName);
                    return new FtpInfo(context.getServerAddress(), ftpUserName, userAccountInfo.getPassword()
                    , ftpAccountInfos[i].getUserHome(), Integer.toString(context.getServerPort()), FtpInfo.Protocol.sftp);
                }
                FtpContext context = getFTPContext(ftpUserName);
                return new FtpInfo(context.getServerAddress(), ftpUserName, userAccountInfo.getPassword()
                        , ftpAccountInfos[i].getUserHome(), Integer.toString(context.getServerPort()), FtpInfo.Protocol.ftp);
            }

        }
        return null;
    }

    public static FtpInfo getFtpInfo(String ftpName, String neId) throws MOSException, AccountManagerException, NamingException
    {
        return getFtpInfo(ftpName, _isSftp(neId));
    }

    public static boolean isSftp(String neId) throws MOSException
    {
        return _isSftp(neId);
    }

    private static FtpContext getFTPContext(String ftpUserName) throws NamingException {
        Context ctx = new InitialContext();
        Object obj = ctx.lookup(FtpUserContext.JNDI_NAME);
        if (obj == null) {
            throw new RuntimeException(FtpUserContext.JNDI_NAME + " context is null");
        }
        FtpUserContext userContext = (FtpUserContext) obj;
        FtpContext[] contexts =  userContext.getAllFtpContext();
        for (FtpContext context : contexts){
            if(ftpUserName.equals(context.getUserName())){
                return context;
            }
        }
        return null;
    }

    private static SFtpContext getSFTPContext(String ftpUserName) throws NamingException {
        Context ctx = new InitialContext();
        Object obj = ctx.lookup(SFtpContext.JNDI_NAME);
        if (obj == null) {
            throw new RuntimeException(SFtpContext.JNDI_NAME + " context is null");
        }
        SFtpContext userContext = (SFtpContext) obj;
        return userContext;
    }


    private static Mo getMo(DN dn) throws MOSException
    {
        Result<Mo> result = new MosServiceHttp().get(dn);

        if (result.isSuccess() && !result.getMo().isEmpty())
        {
            return result.getMo().get(0);
        }
        logger(FtpManager.class).debug(" fail to get mo, dn " + dn);
        throw new NullMoException(" fail to get mo, dn " + dn);

    }

    protected static final boolean _isSupportSftp(String neId) throws MOSException
    {
        Mo mo = getMo(new DN(NE.replace("${neId}", neId)));
        logger(FtpManager.class).debug(neId + " current version :" + mo.get("version"));
        return mo.get("version").toString().trim().compareTo("2.04.01.03") >= 0;
    }


    protected static final boolean _isSftpMode(String neId) throws MOSException
    {
        Mo mo = getMo(new DN(SFTP.replace("${neId}", neId)));
        logger(FtpManager.class).debug(neId + " current sftp enable :" + mo.get("status"));
        return Integer.valueOf(mo.get("status").toString()) == SFTP_ENABLE;
    }

    protected static boolean _isSftp(String neId) throws MOSException
    {
        return _isSftpMode(neId) ? _isSupportSftp(neId) : false;
    }


}
