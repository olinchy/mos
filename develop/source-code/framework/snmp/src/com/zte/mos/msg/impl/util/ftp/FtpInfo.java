package com.zte.mos.msg.impl.util.ftp;

/**
 * Created by zhangbin10086509 on 15-6-5.
 */
public class FtpInfo {
    public enum Protocol{
        ftp(0),
        sftp(1);
        int value;
        Protocol(int value){
            this.value = value;
        }
    }

    private String ftpUserName;

    private String ftpUserPassWord;

    private String useHome;

    private String ipAddress;

    private String  port = "21";

    private Protocol mode = Protocol.ftp;

    public FtpInfo(){
    }


    public FtpInfo(String ipAddress, String ftpUserName, String ftpUserPassWord, String useHome, String port){
        this(ipAddress, ftpUserName, ftpUserPassWord, useHome, port, Protocol.ftp);
    }

    public FtpInfo(String ipAddress, String ftpUserName, String ftpUserPassWord, String useHome, String port, Protocol mode){
        this.ipAddress = ipAddress;
        this.ftpUserName = ftpUserName;
        this.ftpUserPassWord = ftpUserPassWord;
        this.useHome = useHome;
        this.port = port;
        this.mode = mode;
    }

    public String getFtpUserName() {
        return ftpUserName;
    }


    public void setFtpUserName(String ftpUserName) {
        this.ftpUserName = ftpUserName;
    }


    public String getFtpUserPassWord() {
        return ftpUserPassWord;
    }


    public void setFtpUserPassWord(String ftpUserPassWord) {
        this.ftpUserPassWord = ftpUserPassWord;
    }


    public String getUseHome() {
        return useHome;
    }


    public void setUseHome(String useHome) {
        this.useHome = useHome;
    }


    public String getIpAddress() {
        return ipAddress;
    }


    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }


    public String getPort() {
        return port;
    }


    public void setPort(String port) {
        this.port = port;
    }

    public Protocol getMode() {
        return mode;
    }

    public void setMode(Protocol mode) {
        this.mode = mode;
    }
}
