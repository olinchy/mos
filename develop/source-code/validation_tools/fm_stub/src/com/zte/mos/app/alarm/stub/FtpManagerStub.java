package com.zte.mos.app.alarm.stub;

import com.zte.app.common.uep.ftp.FtpInfo;
import com.zte.app.common.uep.ftp.IFtpManager;
import com.zte.mos.exception.MOSException;
import com.zte.ums.uep.api.psl.systemsupport.AccountManagerException;

import javax.naming.NamingException;

/**
 * Created by ccy on 8/13/15.
 */
public class FtpManagerStub implements IFtpManager
{
    private FtpInfo info = new FtpInfo();

    @Override
    public FtpInfo getFtpInfo(String ftpName, boolean isSftp) throws AccountManagerException, NamingException {
        return info;
    }

    @Override
    public FtpInfo getFtpInfo(String ftpName, String neId) throws MOSException, AccountManagerException, NamingException
    {
        return null;
    }

    @Override
    public boolean isSftp(String neId) throws MOSException
    {
        return false;
    }

    public FtpManagerStub(String ftpHome)
    {
        info.setUseHome(ftpHome);
        info.setFtpUserName("mwFM");
        info.setFtpUserPassWord("1234");
        info.setIpAddress("127.0.0.1");
    }
}
