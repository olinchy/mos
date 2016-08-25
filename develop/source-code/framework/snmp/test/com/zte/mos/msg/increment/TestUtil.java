package com.zte.mos.msg.increment;

import com.zte.mos.domain.DN;
import com.zte.mos.exception.MOSException;
import com.zte.mos.httpservice.MosService;
import com.zte.mos.httpservice.MosServiceHttp;
import com.zte.mos.message.ConfResult;
import com.zte.mos.message.Mo;
import com.zte.mos.message.Result;
import com.zte.mos.type.Pair;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by qiuhua on 11/5/15.
 */
public class TestUtil
{
    public static void bulidMosEnv(String fileName) throws JDOMException, IOException, MOSException {
        Element root = readFile(getPath(fileName));
        List<Element> objs = root.getChildren();
        for (Element obj : objs) {
            MosServiceHttp service = new MosServiceHttp();
            try {
                service.startTransaction();
                addObj(obj, service);
                service.commit();
            } catch (Throwable e) {
                System.out.println(e);
                service.rollback();
            }

        }
    }

    private static void addObj(Element obj, MosServiceHttp service) throws MOSException {
        List<Element> mos = obj.getChildren("mo");
        for (Element moElm : mos) {
            String moClass = moElm.getAttributeValue("moclass");
            String dn = moElm.getAttributeValue("dn");
            String oper = moElm.getAttributeValue("oper");
            Mo mo = new Mo(moClass);
            mo.setDn(new DN(dn));

            List<Element> attrs = moElm.getChildren("attr");
            for (Element attr : attrs) {
                String attrtype = attr.getAttributeValue("attr-type");
                if ("ArrayList".equals(attrtype)) {
                    String[] vlaue = attr.getAttributeValue("attr-value").split(";");
//                    List<String> vlaues = new ArrayList<String>();
//                    for (String s : vlaue) {
//                        vlaues.add(s);
//                    }
                    mo.setAttr(
                            attr.getAttributeValue("attr-key"),
                            vlaue);
                } else {
                    mo.setAttr(
                            attr.getAttributeValue("attr-key"),
                            attr.getAttributeValue("attr-value"));
                }
            }

            Result res;

            if ("set".equals(oper))
            {
                res = service.set(mo);
            }
            else if ("act".equals(oper))
            {
                String actName = moElm.getAttributeValue("actionName");
                Pair[] pairs = new Pair[mo.getMo().size()];
                int i = 0;
                for (String key : mo.getMo().keySet())
                {
                    pairs[i] = new Pair(key, mo.get(key));
                    i++;
                }
                res = service.act(mo.getDn(), actName, pairs);
            }
            else
            {
                res = service.add(mo);
            }
            if (!res.isSuccess()) {
                System.out.println("Failed in add " + mo + " because: " + ((ConfResult) res.getMo().get(0)).exception().getCause());
            }
        }
    }

    public static void clearEnv() {
        try {
            MosService mosService = new MosServiceHttp();
            Result<String> res = mosService.ls(new DN("/Ems/1/"));
            if (res.isSuccess()) {
                for (String dn : res.getMo()) {
                    String delDN = "/Ems/1/" + dn + "/";
                    Result<String> dn_res = mosService.ls(new DN(delDN));
                    if (dn_res.isSuccess()) {
                        for (String dn_del : dn_res.getMo()) {
                            Result del_res = mosService.del(new DN(delDN + dn_del));
                            if (!del_res.isSuccess()) {
                                System.out.println("Failed in delete " + dn_del + " because: " + ((ConfResult) del_res.getMo().get(0)).exception().getCause());
                            }
                        }
                    }
                }
            }
        } catch (MOSException e) {
            e.printStackTrace();
        }
    }

    public static void clearAllMO(String motype) {
        try {
            Result<String> res = new MosServiceHttp().ls(new DN("/Ems/1/" + motype));
            if (res.isSuccess()) {
                for (String dn : res.getMo()) {
                    new MosServiceHttp().del(new DN("/Ems/1/" + motype + "/" + dn));
                }
            }
        } catch (MOSException e) {
            e.printStackTrace();
        }

    }

    private static Element readFile(String filePath) throws JDOMException, IOException {
        SAXBuilder sb = new SAXBuilder();
        Document doc = sb.build(new File(filePath));
        return doc.getRootElement();
    }

    public static String getPath(String fileName) {
        return Thread.currentThread().getContextClassLoader().getResource(fileName).getPath();
//        return this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
    }
}
