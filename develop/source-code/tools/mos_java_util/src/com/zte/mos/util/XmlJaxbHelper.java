/**
 *
 */
package com.zte.mos.util;

import org.w3c.dom.Node;

import javax.xml.bind.*;
import java.io.*;
import java.net.URL;
import java.util.Hashtable;

/**
 * @author MaXiaojun
 */
public class XmlJaxbHelper
{

    private static Hashtable<String, JAXBContext> contextbuf = new Hashtable<String, JAXBContext>();

    private static JAXBContext getJAXBContext(Class<?>[] clazzs) throws JAXBException
    {
        if (clazzs != null)
        {
            StringBuilder sb = new StringBuilder();
            for (Class<?> c : clazzs)
            {
                sb.append(c.getName()).append(",");
            }
            String str = sb.toString();
            if (contextbuf.containsKey(str))
            {
                return contextbuf.get(str);
            }
            else
            {
                JAXBContext c = JAXBContext.newInstance(clazzs);
                contextbuf.put(str, c);
                return c;
            }
        }
        else
        {
            throw new JAXBException(new NullPointerException("Class"));
        }

    }

    /**
     * JAXB方式导出XML
     *
     * @param obj
     * @param clazz
     * @param file
     * @throws JAXBException
     * @throws IOException
     */
    public static void exportXmlJAXB(Object obj, Class<?> clazz, File file)
            throws JAXBException, IOException
    {
        exportXmlJAXB(obj, new Class<?>[] { clazz }, file);
    }

    public static void exportXmlJAXB(Object obj, Class<?> clazz, Node node)
            throws JAXBException, IOException
    {
        exportXmlJAXB(obj, new Class<?>[] { clazz }, node);
    }

    public static void exportXmlJAXB(Object obj, Node node) throws JAXBException, IOException
    {
        exportXmlJAXB(obj, new Class<?>[] { obj.getClass() }, node);
    }

    public static void exportXmlJAXB(Object obj, OutputStream out) throws JAXBException, IOException
    {
        if (obj != null)
        {
            exportXmlJAXB(obj, obj.getClass(), out);
        }
    }

    public static void exportXmlJAXB(Object obj, Class<?> clazz, OutputStream out)
            throws JAXBException, IOException
    {
        exportXmlJAXB(obj, new Class<?>[] { clazz }, out);
    }

    /**
     * JAXB方式导出XML
     *
     * @param obj
     * @param clazz
     * @param filename
     * @throws JAXBException
     * @throws IOException
     */
    public static void exportXmlJAXB(Object obj, Class<?> clazz, String filename)
            throws JAXBException, IOException
    {
        exportXmlJAXB(obj, clazz, new File(filename));
    }

    /**
     * @param obj
     * @param clazz
     * @param file
     * @throws JAXBException
     * @throws IOException   ((com.zte.cmre.api.Resource)((com.zte.cmdt.base.cmdset.CmdSetCmd
     *                       )((com.zte.cmdt.base.cmdset.CmdSet)((java.util.ArrayList)((
     *                       com.zte.ums.womcn.cmdt.base.cmdset.CmdSetModel)obj).cmdsets).
     *                       get(0)).getCmds().get(3)).getLabel()).getLabel(0).getBytes()
     */
    public static void exportXmlJAXB(Object obj, Class<?>[] clazz, File file)
            throws JAXBException, IOException
    {
        OutputStream os = new FileOutputStream(file);
        exportXmlJAXB(obj, clazz, os);
        os.close();
    }

    /**
     * @param obj
     * @param clazz
     * @param node
     * @throws JAXBException
     * @throws IOException
     */
    // @SuppressWarnings("unused")
    public static void exportXmlJAXB(Object obj, Class<?>[] clazz, Node node)
            throws JAXBException, IOException
    {
        JAXBContext jc = getJAXBContext(clazz);
        Marshaller m = jc.createMarshaller();
        setMarshallerProperty(m).marshal(obj, node);
    }

    /**
     * @param obj   : 对象
     * @param clazz : 对象对应的类
     * @param out   :
     * @throws JAXBException
     */
    public static void exportXmlJAXB(Object obj, Class<?>[] clazz, OutputStream out)
            throws JAXBException, IOException
    {
        JAXBContext jc = getJAXBContext(clazz);
        Marshaller m = jc.createMarshaller();
        // XMLWriter writer = new XMLWriter(new OutputWriter(out));
        setMarshallerProperty(m).marshal(obj, out);
    }

    /**
     * @param m
     * @param encoding
     * @return
     * @throws PropertyException
     */
    private static Marshaller setMarshallerProperty(Marshaller m) throws PropertyException
    {
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        // set Output charset to project's charset;
        m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        return m;
    }

    /**
     * @param obj
     * @param clazz
     * @param filename
     * @throws JAXBException
     * @throws IOException
     */
    public static void exportXmlJAXB(Object obj, Class<?>[] clazz, String filename)
            throws JAXBException, IOException
    {
        exportXmlJAXB(obj, clazz, new File(filename));
    }

    /**
     * 解析文件
     *
     * @param clazz
     * @param file
     * @return
     * @throws JAXBException
     */
    public static Object importXmlJAXB(Class<?> clazz, File file) throws JAXBException
    {
        return importXmlJAXB(new Class<?>[] { clazz }, file);
    }

    public static Object importXmlJAXB(Class<?> clazz, Node node) throws JAXBException
    {
        return importXmlJAXB(new Class<?>[] { clazz }, node);
    }

    /**
     * JAXB方式导入XML
     *
     * @param clazz
     * @param filename
     * @return
     * @throws JAXBException
     */
    public static Object importXmlJAXB(Class<?> clazz, String filename) throws JAXBException
    {
        return importXmlJAXB(clazz, new File(filename));
    }

    public static Object importXmlJAXB(Class<?>[] clazz, File file) throws JAXBException
    {
        JAXBContext jc = getJAXBContext(clazz);
        Unmarshaller u = jc.createUnmarshaller();
        return u.unmarshal(file);
    }

    public static Object importXmlJAXB(Class<?>[] clazz, Node node) throws JAXBException
    {
        long start = System.currentTimeMillis();
        try
        {
            JAXBContext jc = getJAXBContext(clazz);
            Unmarshaller u = jc.createUnmarshaller();
            return u.unmarshal(node);
        }
        catch (JAXBException ex)
        {
            throw ex;
        }
        finally
        {

        }

    }

    public static Object importXmlJAXB(Class<?>[] clazz, String filename) throws JAXBException
    {
        return importXmlJAXB(clazz, new File(filename));
    }

    public static Object importXmlJAXB(Class<?>[] clazz, URL url) throws JAXBException
    {
        JAXBContext jc = getJAXBContext(clazz);
        Unmarshaller u = jc.createUnmarshaller();
        return u.unmarshal(url);
    }

    public static Object importXmlJAXB(Class<?> clazz, URL url) throws JAXBException
    {
        return importXmlJAXB(new Class<?>[] { clazz }, url);
    }

    public static Object importXmlJAXB(Class<?>[] clazz, InputStream in) throws JAXBException
    {
        JAXBContext jc = getJAXBContext(clazz);
        Unmarshaller u = jc.createUnmarshaller();
        return u.unmarshal(in);
    }

    public static Object importXmlJAXB(Class<?> clazz, InputStream in) throws JAXBException
    {
        return importXmlJAXB(new Class<?>[] { clazz }, in);
    }

    public static Object importXmlJAXB(Class<?>[] clazz, Reader in) throws JAXBException
    {
        JAXBContext jc = getJAXBContext(clazz);
        Unmarshaller u = jc.createUnmarshaller();
        return u.unmarshal(in);
    }

    public static Object importXmlJAXB(Class<?> clazz, Reader in) throws JAXBException
    {
        return importXmlJAXB(new Class<?>[] { clazz }, in);
    }

}
