package com.zte.app.common.util;

import com.zte.mos.domain.DN;
import com.zte.mos.exception.MOSException;
import com.zte.mos.httpservice.MosServiceHttp;
import com.zte.mos.message.ActionRsp;
import com.zte.mos.message.Mo;
import com.zte.mos.message.Result;
import com.zte.mos.type.Pair;
import com.zte.mos.util.basic.Logger;
import com.zte.smartlink.SmartLinkNodeStarter;
import sun.rmi.transport.proxy.RMIDirectSocketFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.server.RMISocketFactory;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Created by pavel on 15-7-2.
 */
public class MosUtil {
    private Logger logger = Logger.logger(MosUtil.class);
    public static final String NE_DN_PATH = "/Ems/1/Ne/";
    public static final String VERSIONINFO_DN_PATH = "/VersionInfo/1/";
    public static final String PRODUCT_DN_PATH = "/Product/1";
    public static final String TimeZone_DN_PATH = "/Product/1/SysPara/1/Time/1/Sntp/1";
    public static final String DST_DN_PATH = "/Product/1/SysPara/1/Time/1/SummerTime/1";
    public static final String COMMUNICATION_DN_PATH = "/Communication/1";
    public static final String IPV4_DN_PATH = "/Ems/1/IpV4/";
    public static final String TASKGROUP_PATH = "/Ems/1/VersionModule/1/TaskGroup/";
    public static final String UNIFIEDVERSIONPACKAGE_PATH = "/Ems/1/VersionModule/1/UnifiedVersionPackage/";
    public static final String SOFTWAREPACKAGE = "/SoftwarePackage/1";
    public static final String FIRMWAREPACKAGE = "/FirmwarePackage/1";
    public static final String VERSIONPACKAGE = "/VersionPackage/";
    public enum Dst_Status {enable, disable}

    public void addMo(Mo... mos)
    {
        MosServiceHttp service = null;
        try
        {
            service = new MosServiceHttp();
            service.startTransaction();
            for (Mo mo:mos) {
                service.add(mo);
            }
            service.commit();
        }
        catch(Throwable e)
        {
//            try {
//                service.rollback();
//            } catch (MOSException e1) {
//                e1.printStackTrace();
//            }
        }
    }

    public boolean addMo(DN dn, Mo mo)
    {
        Result result = null;
        MosServiceHttp service = null;
        try
        {
            mo.setDn(dn);
            service = new MosServiceHttp();
            result = service.add(mo);
            return result.isSuccess();
        }

        catch(Throwable e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delMo(DN dn)
    {
        Result result = null;
        MosServiceHttp service = null;
        try
        {
            service = new MosServiceHttp();
            result = service.del(dn);
            return result.isSuccess();
        }

        catch(Throwable e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public boolean setMo(DN dn, Mo mo)
    {
        Result result = null;
        MosServiceHttp service = null;
        try
        {
            mo.setDn(dn);
            service = new MosServiceHttp();
            result = service.set(mo);
            return result.isSuccess();
        }

        catch(Throwable e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public Mo creatNE(String ip, String neType, String version, String neName){
        return creatNE(ip, neType, version, neName, "480", Dst_Status.disable, "0", "0", "0",neType.toUpperCase(),"v241");
    }

    public Mo createPureNe(String ip, String neType, String version, String neName)
    {
        return _createPureNe(ip, neType, version, neName, "480", Dst_Status.disable, "0", "0", "0", neType.toUpperCase(), "v241");
    }

    public Mo creatNE(String ip, String neType, String version, String neName,String modelName,String modelVersion){
        return creatNE(ip, neType, version, neName, "480", Dst_Status.disable, "0", "0", "0",modelName,modelVersion);
    }

    public Mo creatNE(String ip, String neType, String version, String neName, String TimeZoneId
                ,Dst_Status status, String start, String end, String offset,String modelName,String modelVersion)
    {
        Mo neMo = null;
        Result result = null;
        MosServiceHttp service = null;
        String neID = null;
        try {
            service = new MosServiceHttp();
            Result<ActionRsp> resId = service.act(new DN("/Ems/1"), "allocNeId");
            if (!resId.isSuccess())
            {
                logger.info("IP " + ip + " allocateNeID fail");
                return null;
            }
            neID = String.valueOf(resId.getMo().get(0).get("neid"));
        } catch (MOSException e) {
            logger.error("alloc neid failed. ", result.exception());
            return null;
        }
        try
        {
            String ipv4dn = IPV4_DN_PATH + ip;
            Mo ipv4Mo = getIpV4Mo(ipv4dn, ip);

            String nedn = NE_DN_PATH + neID;
            neMo = getNeMo(nedn, neType, neName,ip,version);

            //String productDn = nedn + PRODUCT_DN_PATH;
            //Mo productMo = getProductMo(productDn, neType, modelVersion);

            String timeZoneDn = nedn + TimeZone_DN_PATH;
            Mo timeZoneMo = getTimeZoneMo(timeZoneDn, TimeZoneId);

            String dstDn = nedn + DST_DN_PATH;
            Mo dstMo = getDSTMo(dstDn, status, start, end, offset);

            //String versionInfoDn = nedn + VERSIONINFO_DN_PATH;
            //Mo versionInfoMo = getVersionInfoMo(versionInfoDn,version);

            //Add Ne Mo
            service.startTransaction();
            result = service.add(ipv4Mo, neMo);
            if (!result.isSuccess()){
                logger.error("Add ne failed. ipv4Mo or neMo or productMo add failed.",result.exception());
                service.rollback();
                return null;
            }
//            Mo productMo = getProductMo(nedn + PRODUCT_DN_PATH,modelName,modelVersion);
//            result = service.set(productMo);
//            if (!result.isSuccess()){
//                logger.error("set productMo failed.",result.exception());
//                service.rollback();
//                return null;
//            }
//            result = service.set(timeZoneMo);
//            if (!result.isSuccess()){
//                logger.error("Add ne failed. timeZoneMo set failed.",result.exception());
//                service.rollback();
//                return null;
//            }
//            result = service.set(dstMo);
//            if (!result.isSuccess()){
//                logger.error("Add ne failed. dstMo set failed.",result.exception());
//                service.rollback();
//                return null;
//            }
//            //if add ne success, set IpV4 Mo data
//            result = service.set(ipv4Mo);
//            if (!result.isSuccess()){
//                logger.error("Add ne failed. ipv4Mo set failed.",result.exception());
//                service.rollback();
//                return false;
//            }
            Pair<String,Object> paras1 = new Pair<String,Object>("modelName",modelName.toLowerCase());
            Pair<String,Object> paras2 = new Pair<String,Object>("modelVersion",modelVersion);
            result = service.act(new DN(nedn+PRODUCT_DN_PATH),"modelChange",paras1,paras2);
            if (!result.isSuccess()){
                logger.error("load Model failed.",result.exception());
                service.rollback();
                return null;
            }
            result = service.set(timeZoneMo);
            if (!result.isSuccess()){
                logger.error("Add ne failed. timeZoneMo set failed.",result.exception());
                service.rollback();
                return null;
            }
            result = service.set(dstMo);
            if (!result.isSuccess()){
                logger.error("Add ne failed. dstMo set failed.",result.exception());
                service.rollback();
                return null;
            }
            service.commit();
        } catch (Throwable throwable) {
            logger.error("Add ne failed. ip=" + ip, throwable);
//            try {
//                service.rollback();
//            } catch (MOSException e) {
//                logger.error("Add ne failed, and rollback failed too.",throwable);
//            }
            return null;
        }
        return neMo;
    }

    public Mo createPureNeSpecifyID(int neID, String ip, String neType, String version, String neName)
    {
        Mo neMo = null;
        Result result = null;
        MosServiceHttp service = null;
        /*String neID = null;
        try {
            service = new MosServiceHttp();
            Result<ActionRsp> resId = service.act(new DN("/Ems/1"), "allocNeId");
            if (!resId.isSuccess())
            {
                logger.info("IP " + ip + " allocateNeID fail");
                return null;
            }
            neID = String.valueOf(resId.getMo().get(0).get("neid"));
        } catch (MOSException e) {
            logger.error("alloc neid failed. ", result.exception());
            return null;
        }*/
        try
        {
            service = new MosServiceHttp();
            String ipv4dn = IPV4_DN_PATH + ip;
            Mo ipv4Mo = getIpV4Mo(ipv4dn, ip);

            String nedn = NE_DN_PATH + neID;
            neMo = getNeMo(nedn, neType, neName,ip,version);

            //Add Ne Mo
            service.startTransaction();
            result = service.add(ipv4Mo, neMo);
            if (!result.isSuccess()){
                logger.error("Add ne failed. ipv4Mo or neMo or productMo add failed.",result.exception());
                service.rollback();
                return null;
            }
            service.commit();
        } catch (Throwable throwable) {
            logger.error("Add ne failed. ip=" + ip, throwable);
            return null;
        }
        return neMo;
    }

    public Mo _createPureNe(String ip, String neType, String version, String neName, String TimeZoneId
            ,Dst_Status status, String start, String end, String offset,String modelName,String modelVersion)
    {
        Mo neMo = null;
        Result result = null;
        MosServiceHttp service = null;
        String neID = null;
        try {
            service = new MosServiceHttp();
            Result<ActionRsp> resId = service.act(new DN("/Ems/1"), "allocNeId");
            if (!resId.isSuccess())
            {
                logger.info("IP " + ip + " allocateNeID fail");
                return null;
            }
            neID = String.valueOf(resId.getMo().get(0).get("neid"));
        } catch (MOSException e) {
            logger.error("alloc neid failed. ", result.exception());
            return null;
        }
        try
        {
            String ipv4dn = IPV4_DN_PATH + ip;
            Mo ipv4Mo = getIpV4Mo(ipv4dn, ip);

            String nedn = NE_DN_PATH + neID;
            neMo = getNeMo(nedn, neType, neName,ip,version);

            //String productDn = nedn + PRODUCT_DN_PATH;
            //Mo productMo = getProductMo(productDn, neType, modelVersion);

            String timeZoneDn = nedn + TimeZone_DN_PATH;
            Mo timeZoneMo = getTimeZoneMo(timeZoneDn, TimeZoneId);

            String dstDn = nedn + DST_DN_PATH;
            Mo dstMo = getDSTMo(dstDn, status, start, end, offset);

            //String versionInfoDn = nedn + VERSIONINFO_DN_PATH;
            //Mo versionInfoMo = getVersionInfoMo(versionInfoDn,version);

            //Add Ne Mo
            service.startTransaction();
            result = service.add(ipv4Mo, neMo);
            if (!result.isSuccess()){
                logger.error("Add ne failed. ipv4Mo or neMo or productMo add failed.",result.exception());
                service.rollback();
                return null;
            }
//            Mo productMo = getProductMo(nedn + PRODUCT_DN_PATH,modelName,modelVersion);
//            result = service.set(productMo);
//            if (!result.isSuccess()){
//                logger.error("set productMo failed.",result.exception());
//                service.rollback();
//                return null;
//            }
//            result = service.set(timeZoneMo);
//            if (!result.isSuccess()){
//                logger.error("Add ne failed. timeZoneMo set failed.",result.exception());
//                service.rollback();
//                return null;
//            }
//            result = service.set(dstMo);
//            if (!result.isSuccess()){
//                logger.error("Add ne failed. dstMo set failed.",result.exception());
//                service.rollback();
//                return null;
//            }
//            //if add ne success, set IpV4 Mo data
//            result = service.set(ipv4Mo);
//            if (!result.isSuccess()){
//                logger.error("Add ne failed. ipv4Mo set failed.",result.exception());
//                service.rollback();
//                return false;
//            }
//            Pair<String,Object> paras1 = new Pair<String,Object>("modelName",modelName.toLowerCase());
//            Pair<String,Object> paras2 = new Pair<String,Object>("modelVersion",modelVersion);
//            result = service.act(new DN(nedn+PRODUCT_DN_PATH),"modelChange",paras1,paras2);
//            if (!result.isSuccess()){
//                logger.error("load Model failed.",result.exception());
//                service.rollback();
//                return null;
//            }
//            result = service.set(timeZoneMo);
//            if (!result.isSuccess()){
//                logger.error("Add ne failed. timeZoneMo set failed.",result.exception());
//                service.rollback();
//                return null;
//            }
//            result = service.set(dstMo);
//            if (!result.isSuccess()){
//                logger.error("Add ne failed. dstMo set failed.",result.exception());
//                service.rollback();
//                return null;
//            }
            service.commit();
        } catch (Throwable throwable) {
            logger.error("Add ne failed. ip=" + ip, throwable);
//            try {
//                service.rollback();
//            } catch (MOSException e) {
//                logger.error("Add ne failed, and rollback failed too.",throwable);
//            }
            return null;
        }
        return neMo;
    }

    public Mo creatNEWithoutModel(String ip, String neType, String version, String neName)
            //, String TimeZoneId, Dst_Status status, String start, String end, String offset)
    {
        Mo neMo = null;
        Result result = null;
        MosServiceHttp service = null;
        String neID = null;
        try {
            service = new MosServiceHttp();
            Result<ActionRsp> resId = service.act(new DN("/Ems/1"), "allocNeId");
            if (!resId.isSuccess())
            {
                logger.info("IP " + ip + " allocateNeID fail");
                return null;
            }
            neID = String.valueOf(resId.getMo().get(0).get("neid"));
        } catch (MOSException e) {
            logger.error("alloc neid failed. ", result.exception());
            return null;
        }
        try
        {
            String ipv4dn = IPV4_DN_PATH + ip;
            Mo ipv4Mo = getIpV4Mo(ipv4dn, ip);

            String nedn = NE_DN_PATH + neID;
            neMo = getNeMo(nedn, neType, neName,ip,version);

            /*String productDn = nedn + PRODUCT_DN_PATH;
            Mo productMo = getProductMo(productDn, neType, modelVersion);

            String timeZoneDn = nedn + TimeZone_DN_PATH;
            Mo timeZoneMo = getTimeZoneMo(timeZoneDn, TimeZoneId);

            String dstDn = nedn + DST_DN_PATH;
            Mo dstMo = getDSTMo(dstDn, status, start, end, offset);*/

            //Add Ne Mo
            service.startTransaction();
            result = service.add(ipv4Mo, neMo);
            if (!result.isSuccess()){
                logger.error("Add ne failed. ipv4Mo or neMo add failed.",result.exception());
                service.rollback();
                return null;
            }
            /*Mo productMo = getProductMo(nedn + PRODUCT_DN_PATH,modelName,modelVersion);
            try {
                service.set(productMo);
            } catch (MOSException e) {
                logger.error("set productMo failed.",e);
                return null;
            }*/
            /*result = service.set(timeZoneMo);
            if (!result.isSuccess()){
                logger.error("Add ne failed. timeZoneMo set failed.",result.exception());
                service.rollback();
                return null;
            }
            result = service.set(dstMo);
            if (!result.isSuccess()){
                logger.error("Add ne failed. dstMo set failed.",result.exception());
                service.rollback();
                return null;
            }*/
            service.commit();
        } catch (Throwable throwable) {
            logger.error("Add ne failed. ip=" + ip, throwable);
//            try {
//                service.rollback();
//            } catch (MOSException e) {
//                logger.error("Add ne failed, and rollback failed too.",throwable);
//            }
            return null;
        }
        return neMo;
    }

    private Mo getNeVersionMo(String neid,String versionType,int pkgIndex,String pkgName,String pkgStatus,int parentPkgIndex,String parentPkgName){
        String neDn = NE_DN_PATH+neid;
        String pkgType = null;
        String vmpDn = neDn + "/Product/1/Vmp/1";
        Mo mo = new Mo(versionType/*"Software"*/);
        DN dn = null;
        if (versionType.equals("Firmware")){
            dn = new DN(vmpDn + "/Firmware/"+pkgIndex);
            pkgType = "firmware";
        }
        else if (versionType.equals("HostedAouSoftware")){
            dn = new DN(vmpDn + "/HostedAouSoftware/"+pkgIndex);
            pkgType = "taousoftware";
        }
        else if (versionType.equals("Software")){
            dn = new DN(vmpDn + "/Software/"+pkgIndex);
            pkgType = "software";
        }
        else if (versionType.equals("Patch")){
            dn = new DN(vmpDn + "/Software/"+parentPkgIndex+"/Patch/"+pkgIndex);
            pkgType = "patch";
        }
        else if (versionType.equals("HotPatch")){
            dn = new DN(vmpDn + "/Software/"+parentPkgIndex+"/HotPatch/"+pkgIndex);
            pkgType = "hotpatch";
        }
        mo.setDn(dn);
        mo.setAttr("pkgName", pkgName /*"NR8000V2.03.03.16"*/);
        mo.setAttr("status", pkgStatus /*"backup"*/);
        mo.setAttr("pkgType", pkgType /*"software"*/);
        if (parentPkgName!=null){
            mo.setAttr("pkgFatherName", parentPkgName);
        }
        return mo;
    }

    public void setNeVersion(String neid,String runVersion,String backupVersion,String firmVersion){
        List<Mo> moList = new ArrayList<Mo>();
        moList.add(getNeVersionMo(neid, "Software", 1, backupVersion/*"NR8000V2.03.03.16"*/, "backup", -1, null));
        moList.add(getNeVersionMo(neid, "HotPatch", 1, backupVersion + "HP1" /*"NR8000V2.03.03.16HP1"*/, "patchActive", 1, backupVersion));
        moList.add(getNeVersionMo(neid, "Patch", 1, backupVersion + "P1"/* "NR8000V2.03.03.16P1"*/, "corrupted", 1, backupVersion));

        moList.add(getNeVersionMo(neid, "Software", 2, runVersion/*"NR8000V2.04.01.10"*/, "run", -1, null));
        moList.add(getNeVersionMo(neid, "Patch", 1, runVersion + "P1" /*"NR8000V2.04.01.10P1"*/, "backup", 2, runVersion));
        moList.add(
                getNeVersionMo(
                        neid, "Patch", 2, runVersion + "P2" /*"NR8000V2.04.01.10P2"*/, "patchActive", 2, runVersion));
        moList.add(
                getNeVersionMo(
                        neid, "HotPatch", 1, runVersion + "HP1" /*"NR8000V2.04.01.10HP1"*/, "backup", 2, runVersion));

        moList.add(getNeVersionMo(neid, "Firmware", 1, firmVersion/*"FWNR8000V2.03.03.16"*/, "taouCopy", -1, null));
        moList.add(
                getNeVersionMo(
                        neid, "HostedAouSoftware", 1, backupVersion /*"NR8000V2.03.03.16"*/, "taouCopy", -1, null));
        try {
            new MosServiceHttp().add(moList.toArray(new Mo[moList.size()]));
        } catch (MOSException e) {
            e.printStackTrace();
        }

    }

    private Mo getNeMo(String nedn, String neType,String neName,String ip,String version)
    {
        Mo neMo = new Mo("Ne");
        neMo.setDn(new DN(nedn));
        //neMo.addAttr("neId", neInfo.getOID());
        neMo.setAttr("netype", neType.toLowerCase());
        Double pox = 0.0;
        Double poy = 0.0;
        neMo.setAttr("longitude", pox.toString());
        neMo.setAttr("latitude", poy.toString());
        neMo.setAttr("location", "");
        neMo.setAttr("neName", neName);
        neMo.setAttr("siteId", "");
        neMo.setAttr("siteName", "");
        neMo.setAttr("ipV4", IPV4_DN_PATH + ip);
        neMo.setAttr("version", version);
        return neMo;
    }
    private static Mo getIpV4Mo(String ipv4dn, String ip)
    {
        Mo ipMo = new Mo("IpV4");
        ipMo.setDn(new DN(ipv4dn));
        ipMo.setAttr("ip_addr", ip);
        ipMo.setAttr("net_mask", "255.255.0.0");
        ipMo.setAttr("gate_way", "193.167.0.1");
        return ipMo;
    }

    private static Mo getTimeZoneMo(String timeZoneDn, String zoneId)
    {
        Mo neMo = new Mo("Sntp");
        neMo.setDn(new DN(timeZoneDn));
        neMo.setAttr("timeZone", zoneId);
        neMo.setAttr("city", "0");
        return neMo;
    }

    private static Mo getDSTMo(String dstDn, Dst_Status status, String start, String end, String offset)
    {
        Mo neMo = new Mo("SummerTime");
        neMo.setDn(new DN(dstDn));
        neMo.setAttr("state", status);
        neMo.setAttr("startDate", start);
        neMo.setAttr("endDate", end);
        neMo.setAttr("step", offset);
        return neMo;
    }

    private static Mo getProductMo(String productDn, String modelName, String modelVersion)
    {
        Mo productMo = new Mo("Product");
        productMo.setDn(new DN(productDn));
        //use default value for now
        productMo.setAttr("modelName", modelName);
        productMo.setAttr("version", modelVersion);
        return productMo;
    }

    private static Mo getVersionInfoMo(String versionInfoDn,String runningVersion)
    {
        Mo mo = new Mo("VersionInfo");
        mo.setDn(new DN(versionInfoDn));
        //use default value for now
        mo.setAttr("running", runningVersion);
        return mo;
    }

    public List<String> findNeID(String ip){
        String ipdn = IPV4_DN_PATH+ip;
        List<String> nelist = new ArrayList<String>();
        MosServiceHttp service = null;
        try {
            service = new MosServiceHttp();

            String exp = "Ne.ipV4='" + ipdn+"'";
            Result res = service.find(exp, "Ne", new DN("/Ems/1/Ne/"));
            if (null != res.getMo() && res.getMo().size() != 0) {
                for (Mo neMo:(List<Mo>)res.getMo()){
                    nelist.add(neMo.getDn().value("Ne"));
                }
            }
        }catch (Throwable throwable){
            logger.error("check existance of ip " + ip + " failed.", throwable);
        }
        return nelist;
    }

    public String getConfigSetAttribut(String neID, String attribute){
        Result result = null;
        MosServiceHttp service = null;
        try {
            service = new MosServiceHttp();
            String cfgsetdn = NE_DN_PATH + neID + PRODUCT_DN_PATH;
            result = service.get(new DN(cfgsetdn));
            if (result.isSuccess()){
                return ((Mo) result.getMo().get(0)).get(attribute).toString();
            }
        } catch (MOSException e) {
            e.printStackTrace();
        }
        return "";
    }

    public Mo getMo(String dn){
        Result result = null;
        MosServiceHttp service = null;
        try {
            service = new MosServiceHttp();
            result = service.get(new DN(dn));
            if (result.isSuccess()){
                return (Mo)result.getMo().get(0);
            }
        } catch (MOSException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getAttribut(String dn, String attribute){
        Result result = null;
        MosServiceHttp service = null;
        try {
            service = new MosServiceHttp();
            result = service.get(new DN(dn));
            if (result.isSuccess()){
                Object attri = ((Mo) result.getMo().get(0)).get(attribute);
                return attri==null?null:attri.toString();
            }
        } catch (MOSException e) {
            e.printStackTrace();
        }
        return "";
    }

    public boolean setAttribut(String dn, String clsName, String attribute, String value){
        Result result = null;
        MosServiceHttp service = null;
        try {
            service = new MosServiceHttp();
            Mo mo = new Mo(clsName);
            mo.setDn(new DN(dn));
            mo.setAttr(attribute, value);
            result = service.set(mo);
            return result.isSuccess();
        } catch (MOSException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<String> getNeList(){
        Result result = null;
        MosServiceHttp service = null;
        try {
            service = new MosServiceHttp();
            result = service.ls(new DN(NE_DN_PATH));
        } catch (MOSException e) {
            return new ArrayList<String>();
        }
        return result.getMo();
    }

    public Mo createVersionPackage(String version,String versionType,String neType) {
        Result result = null;
        MosServiceHttp service = null;
        Mo versionPackageMo = null;
        try {
            service = new MosServiceHttp();
            Mo unifiedVersionPackageMo = new Mo("UnifiedVersionPackage");
            unifiedVersionPackageMo.setDn(new DN(UNIFIEDVERSIONPACKAGE_PATH + version));
            unifiedVersionPackageMo.setAttr("UnifyVersion", version);
            unifiedVersionPackageMo.setAttr("pkgType", versionType);
            unifiedVersionPackageMo.setAttr("neType", "NR8000");
            service.startTransaction();
            result = service.add(unifiedVersionPackageMo);
            if (result.isSuccess()){
                List<Mo> subMos = new ArrayList<Mo>();
                if (versionType.equals("SOFTWARE") || versionType.equals("UNIFY")) {
                    Mo subSoftPackageMo = new Mo("SoftwarePackage");
                    subSoftPackageMo.setDn(new DN(unifiedVersionPackageMo.getDn() + SOFTWAREPACKAGE));
                    subMos.add(subSoftPackageMo);
                }
                if (versionType.equals("FIRMWARE") || versionType.equals("UNIFY")){
                    Mo subFirmPackageMo = new Mo("FirmwarePackage");
                    subFirmPackageMo.setDn(new DN(unifiedVersionPackageMo.getDn() + FIRMWAREPACKAGE));
                    subMos.add(subFirmPackageMo);
                }
                Mo[] subMoArr = new Mo[subMos.size()];
//                subMoArr = subMos.toArray(subMoArr);
                result = service.add(subMos.toArray(subMoArr));
                if (result.isSuccess()){
                    for (Mo mo:subMos) {
                        versionPackageMo = new Mo("VersionPackage");
                        versionPackageMo.setDn(new DN(mo.getDn() + VERSIONPACKAGE + neType));
                        versionPackageMo.setAttr("filePath", "/version/package/path/in/ems");
//                        versionPackageMo.addAttr("versionname", "dpkg.xml");
                        result = service.add(versionPackageMo);
                    }
                }else {
                    service.rollback();
                    return null;
                }
            }else {
                service.rollback();
                return null;
            }
            service.commit();
            return result.isSuccess()?unifiedVersionPackageMo:null;
        } catch (MOSException e) {
//            try {
//                service.rollback();
//            } catch (MOSException e1) {
//                e1.printStackTrace();
//            }
            e.printStackTrace();
            return null;
        }
    }

    public Mo createVMTaskGroup(int taskgroupID){
        return createVMTaskGroup(taskgroupID,new Date());
    }

    public Mo createVMTaskGroup(int taskgroupID,Date createDate){
        Result result = null;
        MosServiceHttp service = null;
        try {
            service = new MosServiceHttp();
            Mo taskGroupMo = new Mo("TaskGroup");
            taskGroupMo.setDn(new DN(TASKGROUP_PATH + taskgroupID));
            String userip = "-";
            try {
                userip = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            String username = "admin";//UepClientService.getFClientService().getUserName();
            taskGroupMo.setAttr("username", username);
            taskGroupMo.setAttr("userip", userip);
            taskGroupMo.setAttr("createtime", createDate);
            taskGroupMo.setAttr("type", "download");
            result = service.add(taskGroupMo);
            return result.isSuccess()==true?taskGroupMo:null;
        } catch (MOSException e) {
            logger.error("create vm taskgroup[" + taskgroupID + "] failed.", e);
            return null;
        }
    }

    public Mo createVMTask(int groupIndex,int taskIndex,String taskType,Mo neMo,String targetVersion,String pkgType, Date createDate){
        Result result = null;
        MosServiceHttp service = null;
        try {
            service = new MosServiceHttp();
            Mo taskMo = new Mo("Task");
            taskMo.setDn(new DN(TASKGROUP_PATH + groupIndex + "/Task/" + taskIndex));
            taskMo.setAttr("type", taskType);
            taskMo.setAttr("neid", neMo.getDn().value("Ne"));
            taskMo.setAttr("netype", neMo.get("netype"));
            taskMo.setAttr("versionNo", targetVersion);
            taskMo.setAttr("timeout", 5);
            taskMo.setAttr("priority", 1);
            taskMo.setAttr("createtime", createDate);
            taskMo.setAttr("packagetype", pkgType);
            result = service.add(taskMo);
            return result.isSuccess()==true?taskMo:null;
        } catch (MOSException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Mo createVMTask(int groupIndex,int taskIndex,String taskType,Mo neMo,String targetVersion){
        return createVMTask(groupIndex,taskIndex,taskType,neMo,targetVersion, "SOFTWARE"/*EnumPkgType.SOFTWARE.toString()*/,new Date());
    }

    public Mo createVMTask(int groupIndex,int taskIndex,String taskType,Mo neMo,String targetVersion,Date createDate){
        return createVMTask(groupIndex,taskIndex,taskType,neMo,targetVersion, "SOFTWARE"/*EnumPkgType.SOFTWARE.toString()*/,createDate);
    }

    public boolean setTaskResult(int groupId,int taskId,String taskResult){
        Result result = null;
        MosServiceHttp service = null;
        try {
            service = new MosServiceHttp();
            Mo mo = new Mo("Task");
            mo.setDn(new DN(TASKGROUP_PATH + groupId + "/Task/" + taskId));
            mo.setAttr("status", "finish");
            mo.setAttr("result", taskResult);
            result = service.set(mo);
            return result.isSuccess();
        } catch (MOSException e) {
            e.printStackTrace();
        }
        return false;
    }


    public void deleteAllMO(String moDN)
    {
        Result result = null;
        MosServiceHttp service = null;
        try {
            service = new MosServiceHttp();
            result = service.ls(new DN(moDN.substring(0, moDN.length() - 1)));
            List<DN> dnList = new ArrayList<DN>();
            if(null != result.getMo()){
                for (String id:(ArrayList<String>)result.getMo()){
                    dnList.add(new DN(moDN + id));
                }
                if (dnList.size()>0) {
                    DN[] dns = new DN[dnList.size()] ;
                    dns = dnList.toArray(dns);
                    result = service.del(dns);
                }
            }
        } catch (MOSException e) {
            e.printStackTrace();
        }
    }

    public List<Mo> find(String exp,String moClass,String dn){
        List<Mo> molist = new ArrayList<Mo>();
        MosServiceHttp service = null;
        try {
            service = new MosServiceHttp();
            Result res = service.find(exp, moClass, new DN(dn));
            if (res.isSuccess()){
                molist = res.getMo();
            }
        }catch (Throwable throwable){
            logger.error("find by exp[" + exp + "] failed.", throwable);
        }
        return molist;
    }

    public List list(String dn){
        MosServiceHttp serviceHttp = null;
        try {
            serviceHttp = new MosServiceHttp();
            Result result = serviceHttp.ls(new DN(dn));
            if (result.isSuccess()){
                return result.getMo();
            }
        } catch (MOSException e) {
            e.printStackTrace();
        }
        return new ArrayList();
    }

    public static void startApp() throws IOException, MOSException {
        System.setProperty("java.rmi.server.hostname", "mos-server");

//        if (RMISocketFactory.getSocketFactory()!=null) {
            RMISocketFactory.setSocketFactory(new RMIDirectSocketFactory() {
                @Override
                public Socket createSocket(String s, int i) throws IOException {
                    if (s.equals("mos-server")) {
                        return new Socket("127.0.0.1", i);
                    }
                    return new Socket(s, i);
                }
            });
//        }
        Properties prop = new Properties();
        prop.setProperty("ports", "55324-55429");
        SmartLinkNodeStarter.start("127.0.0.1", prop);
    }

}
