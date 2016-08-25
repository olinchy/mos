
package com.zte.mos.util;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * </font>
 * </UL>
 *
 * @author liujiangangcc
 */
public class W3cDomHelper
{
    // ���ڸ�ʽ
    public static final SimpleDateFormat DF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * ���� Unix
     */
    private static final String newline = "\n";// ����
    // ����
    // XML ת���ַ�
    private static final String XML_ESC_N = "&#xA;"; // \n
    private static final String XML_ESC_R = "&#xD;"; // \r
    private static final String XML_ESC_Q = "&quot;"; // "
    private static final String XML_ESC_A = "&amp;";// &
    private static final String XML_ESC_L = "&lt;"; // <
    private static final String XML_ESC_G = "&gt;"; // >
    /**
     * ���� - TAB
     */
    private static char[] indents = new char[40];// = Util.fill('\t', 40);//;//
    private static String projectEncoding = "UTF-8";
    private static DocumentBuilder builder;
    private static XMLOutputFactory outputFactory;

    /** ��̬��ʼ�� */
    static
    {
        /** �����ַ�, 40�� */
        Arrays.fill(indents, '\t');
        try
        {
            // com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            outputFactory = XMLOutputFactory.newInstance();
        }
        catch (ParserConfigurationException e)
        {
            System.err.println(e);
        }
    }

    public static void setProjectEncoding(String projectEncoding)
    {
        W3cDomHelper.projectEncoding = projectEncoding;
    }

	/*
     * public static XMLOutputFactory getXMLOutputFactory(){ return
	 * outputFactory; }
	 */

    /**
     * ��һ���ļ�, ȡ�� root
     *
     * @param file �ļ�
     * @return Element: root Element
     * @throws Exception
     */
    public static Element getRootElement(File file) throws Exception
    {
        InputStream is = null;
        try
        {
            is = new FileInputStream(file);
            Element root = getRootElement(is);
            return root;
        }
        catch (FileNotFoundException e)
        {
            throw e;
        }
        finally
        {
            if (is != null)
            {
                try
                {
                    is.close();
                }
                catch (IOException e)
                {
                    // System.err.println("can't close inputstream :" +
                    // e.getLocalizedMessage());
                }
            }
        }
    }

    /**
     * ��ȡһ�� Element �������ӽڵ㵽 Element ����������.
     *
     * @param parent
     * @return
     */
    public static List<Element> elements(Element parent)
    {
        Node child = parent.getFirstChild();
        List<Element> list = null;
        if (child != null)
        {
            list = new ArrayList<Element>();
            while (child != null)
            {
                if (child.getNodeType() == Node.ELEMENT_NODE)
                {
                    list.add((Element) child);
                }
                child = child.getNextSibling();
            }
        }
        return list;
    }

    /**
     * ��ȡһ�� Element �������ӽڵ㵽 Element ����������.
     *
     * @param parent
     * @param name   �ӽڵ����.
     * @return
     */
    public static List<Element> elements(Element parent, String name)
    {
        Node child = parent.getFirstChild();
        List<Element> list = null;
        if (child != null)
        {
            list = new ArrayList<Element>();
            while (child != null)
            {
                if (child.getNodeType() == Node.ELEMENT_NODE && child.getNodeName().equals(name))
                {
                    list.add((Element) child);
                }
                child = child.getNextSibling();
            }
        }
        return list;
    }

    /**
     * ȡ�� element �ĵ�һ���ӽڵ�.
     *
     * @param parent
     * @return
     */
    public static Element firstChildElement(Element parent)
    {
        Node child = parent.getFirstChild();
        while (child != null)
        {
            if (child.getNodeType() == Node.ELEMENT_NODE)
            {
                break;
            }
            // ��һ���ֵܽڵ�.
            child = child.getNextSibling();
        }
        return (Element) child;
    }

    public static Element element(Element parent, String name)
    {
        if (parent.hasChildNodes())
        {
            Node child = parent.getFirstChild();
            while (child != null)
            {
                if (child.getNodeType() == Node.ELEMENT_NODE && child.getNodeName().equals(name))
                {
                    return (Element) child;
                }
                // ��һ���ֵܽڵ�.
                child = child.getNextSibling();
            }
        }
        return null;
    }

    public static String elementTextTrim(Element parent, String elementName)
    {
        String text = null;
        Element e = element(parent, elementName);
        if (e != null)
        {
            text = e.getTextContent();
        }
        return text != null ? text.trim() : text;
    }

    /**
     * ȡ��һ��URL���ĸ��ĵ�
     *
     * @param src
     * @return
     * @throws IOException
     */
    public static Element getRootElement(URL src) throws Exception
    {
        return getRootElement(src.openStream());
    }

    /**
     * ȡ���ļ�����
     *
     * @param fileName
     * @return
     */
    public static String getEncoding(String fileName)
    {
        InputStream is = null;
        try
        {
            Document doc = builder.parse(is = new FileInputStream(fileName));
            return doc.getInputEncoding();
        }
        catch (Exception e)
        {
            return null;
        }
        finally
        {
            if (is != null)
            {
                try
                {
                    is.close();
                }
                catch (IOException e)
                {
                    // e.printStackTrace();
                }
            }
        }
    }

    /**
     * ����������ø�ڵ�(Document �е� ��һ��Element���ͽڵ�)
     *
     * @param is
     * @return
     * @throws Exception
     */
    public static Element getRootElement(InputStream is) throws Exception
    {
        Document doc = getDocument(is);
        return doc != null ? doc.getDocumentElement() : null;
    }

    /**
     * ȡ�� DocumentFragment �ĸ�ڵ�.(����JAXB���).
     *
     * @param doc DocumentFragment
     * @return
     */
    public static Element getRootElement(DocumentFragment doc)
    {
        return (Element) doc.getFirstChild();
    }

    /**
     * �����������һ�� Document
     *
     * @param is
     * @return
     */
    public static Document getDocument(InputStream is) throws Exception
    {
        return builder.parse(is);
    }

    /**
     * �Ե�ǰ����XML����, ���һ�� Document ���ļ�.
     *
     * @param doc  : Document����.
     * @param file : �ļ�
     * @return boolean: �ɹ�/ʧ��
     * @throws IOException
     */
    public static boolean toFile(Document doc, File file) throws Exception
    {
        return toFile(doc, file, projectEncoding);
    }

    /**
     * ���һ�� Document ���ļ�.
     *
     * @param doc      : org.w3c.dom.Document
     * @param file     : �ļ�
     * @param charset: ����
     * @return
     * @throws IOException
     */
    public static boolean toFile(Document doc, File file, String charset) throws Exception
    {
        if (doc == null)
        {
            throw new IllegalArgumentException("δָ������, Document Ϊ��.");
        }
        if (file == null)
        {
            throw new IOException("δָ���ļ�!!");
        }
        if (file.exists() && !file.canWrite())
        {
            FileUtil.makeFileWritable(file);
        }
        FileUtil.ensureDirExists(file);
        FileOutputStream fos = null;
        try
        {
            fos = new FileOutputStream(file);
            return toFile(doc, fos, charset);
        }
        catch (IOException ex)
        {
            throw ex;
        }
        finally
        {
            if (fos != null)
            {
                try
                {
                    fos.close();
                }
                catch (Exception e2)
                {
                    // do Nothing;
                }
            }
        }

    }

    private static void writeProcessInstruction(XMLStreamWriter writer)
    {
        try
        {
            writer.writeProcessingInstruction("cmds", "created=\"" + DF.format(new Date()) + "\"");
        }
        catch (Exception e)
        {
        }
    }

    public static void writeOutputStream(Node node, OutputStream out) throws Exception
    {
        writeOutputStream(node, out, "UTF-8");
    }

    public static ByteArrayOutputStream toOutputStream(Document doc, final String charset)
            throws Exception
    {
        if (doc != null)
        {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            writeOutputStream(doc.getDocumentElement(), bos, charset);
            return bos;
        }
        else
        {
            return null;
        }
    }

    /**
     * ���ڵ�XMLд������
     *
     * @param node
     * @param out
     * @param charset
     * @return
     * @throws Exception
     */
    public static void writeOutputStream(Node node, OutputStream out, final String charset)
            throws Exception
    {
        OutputStreamWriter writer = null;
        try
        {
            // /r = &#X0D;, /n =&#x0A;
            writer = new OutputStreamWriter(out, charset);
            XMLStreamWriter xmlWriter = outputFactory.createXMLStreamWriter(writer);

            // ת���ַ�
            outputFactory.setProperty("escapeCharacters", true);
            // ����/���봦��, ����.
            // setWriterEncoder(xmlWriter);
            // д��XML
            // Start <?xml...
            xmlWriter.writeStartDocument(charset, "1.0");
            xmlWriter.writeCharacters(newline);
            writeProcessInstruction(xmlWriter);
            // ��Ҫ�ݹ�.
            write(xmlWriter, writer, node, 0);
            // End//do Nothing;
            xmlWriter.writeEndDocument();
            writer.flush();
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (writer != null)
            {
                try
                {
                    // writer.close(); don't close!!!
                }
                catch (Exception e2)
                {
                    // DoNothing;
                }
            }
        }
    }

    /**
     * �ڵ�д����
     *
     * @param out
     * @param node
     * @param indentSize
     */
    private static void write(OutputStream out, Node node, int indentSize, final String charset)
            throws Exception
    {
        Transformer t = TransformerFactory.newInstance().newTransformer();
        //
        t.setOutputProperty(OutputKeys.VERSION, "1.0");
        t.setOutputProperty(OutputKeys.ENCODING, charset);
        t.setOutputProperty(OutputKeys.METHOD, "xml");
        t.setOutputProperty(OutputKeys.INDENT, "yes");
        t.transform(new DOMSource(node), new StreamResult(out));
    }

    /**
     * @param writer
     * @param node
     * @param indentSize
     * @throws Exception
     */
    private static void write(XMLStreamWriter writer, OutputStreamWriter streamWriter, Node node,
            int indentSize)
            throws Exception
    {
        if (node == null)
        {
            writer.writeCharacters(newline);
            // writer.writeComment("EMPTY DOCUMENT");
            return;
        }
        String elmName = node.getNodeName();
        int nodeType = node.getNodeType();
        // ������?
        boolean hasAttributes = node.hasAttributes();
        // ���ӽڵ�?
        boolean hasChildren = node.hasChildNodes();
        // �Ƿ�Ϊ�սڵ�.
        boolean emptyElement = !(hasAttributes || hasChildren);
        // ����
        writer.writeCharacters(newline);
        // ����.
        writer.writeCharacters(indents, 0, indentSize);
        if (emptyElement)
        {
            // �սڵ�
            writer.writeEmptyElement(elmName);
        }
        else
        {
            // �ǿսڵ�.
            writer.writeStartElement(elmName);
            // �Ƿ���/> ��ʽ�����ǩ, �ж���׼: 2. û���κ��ӽڵ�.
            boolean simpleEndTag = true;
            // Element.
            if (nodeType == Node.ELEMENT_NODE)
            {
                // д������
                if (hasAttributes)
                {
                    NamedNodeMap atts = node.getAttributes();
                    // ���atts ������.....nnd
                    int len = atts.getLength();
                    if (len > 0)
                    {
                        for (int i = 0; i < len; i++)
                        {
                            Node att = atts.item(i);
                            // ��Ҫ����ת��.
                            if (att.getNodeValue() != null)
                            {
                                writeAttribute(streamWriter, att.getNodeName(), att.getNodeValue());
                                // writer.writeAttribute(att.getNodeName(),
                                // att.getNodeValue());
                            }
                        }
                    }
                }
                // �ӽڵ�:
                if (hasChildren)
                {

                    Node child = node.getFirstChild();
                    int childCnt = 0, childType;
                    while (child != null)
                    {
                        childType = child.getNodeType();
                        // System.out.println(indentSize + "," +
                        // child.getNodeName() + ",type=" + childType);
                        if (childType == Node.ELEMENT_NODE)
                        {
                            write(writer, streamWriter, child, indentSize + 1);
                            childCnt++;
                        }
                        else if (childType == Node.TEXT_NODE
                                || childType == Node.CDATA_SECTION_NODE)
                        {
                            // text/CDATA;
                            String nodeText = child.getNodeValue();
                            if (nodeText != null)
                            {
                                if (childType == Node.CDATA_SECTION_NODE)
                                {
                                    writer.writeCData(nodeText);
                                }
                                else
                                {
                                    writer.writeCharacters(nodeText);
                                }
                            }
                        }
                        // ��һ���ֵ�.
                        child = child.getNextSibling();
                    }
                    if (childCnt > 0)
                    {
                        simpleEndTag = false;
                        writer.writeCharacters(newline);
                        writer.writeCharacters(indents, 0, indentSize);
                    }
                }// endif hasChildren
            } // endif Not Empty

            if (simpleEndTag)
            {
                writer.writeEndElement();
            }
            else
            {
                writer.writeEndElement();
            }
        }
    }

    private static void writeAttribute(OutputStreamWriter writer, String nodeName, String nodeValue)
            throws IOException
    {
        nodeName = " " + nodeName + "=\"";
        StringBuilder sb = new StringBuilder(nodeName);
        String repl = "";
        boolean skip = false;
        for (int index = 0, end = nodeValue.length(); index < end; index++)
        {
            char c = nodeValue.charAt(index);
            switch (c)
            {
            case '\n':
                repl = XML_ESC_N;// "&#xA;";
                break;
            case '\r':
                repl = XML_ESC_R; // \r
                break;
            case 60: // '<'
                repl = XML_ESC_L;// "&lt;";
                break;
            case 62: // '>'
                repl = XML_ESC_G;// "&gt;";
                break;
            case 38: // '&'
                repl = XML_ESC_A; // ;
                break;
            case 34: // '"'
                repl = XML_ESC_Q; // ˫���
                break;
            default:
                sb.append(c);
                continue;
            }
            sb.append(repl);
        }
        sb.append("\"");
        writer.write(sb.toString());
    }

    /**
     * ����Doc�������
     *
     * @param doc      : Xml Doc
     * @param output   : �����
     * @param charset: ����
     * @return �Ƿ�ɹ�
     * @throws Exception �쳣
     */
    public static boolean toFile(Document doc, OutputStream output, String charset) throws Exception
    {
        if (doc == null || output == null)
        {
            return false;
        }
        // ˫����.
        ByteArrayOutputStream bos = null;
        try
        {
            // д�뻺��
            bos = toOutputStream(doc, charset);
            // ��д�����.
            output.write(bos.toByteArray());
            output.flush();
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (bos != null)
            {
                try
                {
                    bos.close();
                }
                catch (Exception e)
                {
                    // impossible, doNothing;
                }
            }
        }
        return true;
    }

    /**
     * �½�һ�� Document
     *
     * @return
     */
    public static Document newDocument()
    {
        return builder.newDocument();
    }

    /**
     * �� parent ���½�һ�� Element, ������֮.
     *
     * @param doc:   Document : parent�ڵ������ĵ�.
     * @param parent : Ҫ�ڸýڵ����½� name �Ľڵ�.
     * @param name   : ��Element���.
     * @return �½��Ľڵ�, ������ parent �ڵ�.
     */
    public static Element addElement(Document doc, Element parent, String name)
    {
        return (Element) parent.appendChild(doc.createElement(name));
    }

    /**
     * �� doc ���½�һ�� Element, ������֮.
     *
     * @param doc: Document : doc �ڵ������ĵ�.
     * @param name : ��Element���.
     * @return �½��Ľڵ�, ������ parent �ڵ�.
     */

    public static Element addElement(Document doc, String name)
    {
        return (Element) doc.appendChild(doc.createElement(name));
    }

    /**
     * �� elm ���½�һ�� Element, ������֮.
     *
     * @param elm: Element : �ڵ�.
     * @param name : ��Element���.
     * @return �½��Ľڵ�, ������ parent �ڵ�.
     */

    public static Element addElement(Element elm, String name)
    {
        return (Element) elm.appendChild(elm.getOwnerDocument().createElement(name));
    }

    /**
     * ����һ�� CDATA �� parent �ڵ���.
     *
     * @param doc    : Document: �ĵ�.
     * @param parent : ���ڵ�(Ҫ�����ı��ڵ�Ľڵ�);
     * @param name   : CDATA �ı��ڵ����.
     * @param text   : CDATA �ı�.
     * @return
     */
    public static Element addCDATASection(Document doc, Element parent, String name, String text)
    {
        Element e = doc.createElement(name);
        if (text != null)
        {
            e.appendChild(doc.createCDATASection(text));
        }
        parent.appendChild(e);
        return e;
    }

    /**
     * ����һ�������ı��ӽڵ�Ľڵ㵽 parent ��. ��: <br>
     * <code>
     * &lt;parent&gt;<br><font color=red>
     * &nbsp;&lt;name&gt;text&lt;/name&gt;<br></font>
     * &lt;parent&gt;
     * </code>
     *
     * @param doc  : Document: �ĵ�.
     * @param name : �ı��ڵ����.
     * @param text : �ı�.
     * @return
     */
    public static Element addTextNode(Document doc, Element parent, String name, String text)
    {
        Element textElement = doc.createElement(name);
        if (text != null)
        {
            textElement.appendChild(doc.createTextNode(text));
        }
        parent.appendChild(textElement);
        return textElement;
    }

    /**
     * �½�һ���ı��ڵ�. ��: <br>
     * <code>
     * <font color=red>
     * &lt;name&gt;text&lt;/name&gt;<br></font>
     * </code>
     *
     * @param doc  : Document: �ĵ�.
     * @param name : �ı��ڵ����.
     * @param text : �ı�.
     * @return
     */
    public static Element newTextNode(Document doc, String name, String text)
    {
        Element e = doc.createElement(name);
        if (text != null)
        {
            e.appendChild(doc.createTextNode(text));
        }
        return e;
    }

    /**
     * ���� Element ���ı��ӽڵ�����. ��: <br>
     * <code>
     * &lt;element&gt;<font color=red>text</font>&lt;element&gt;
     * </code>
     *
     * @param doc     : Document: �ĵ�.
     * @param element : Ҫ�����ı��Ľڵ�.
     * @param text    : �ı�.
     * @return Text �ı��ӽڵ�.
     */
    public static Text setElementText(Document doc, Element element, String text)
    {
        Text node = null;
        if (text != null)
        {
            element.appendChild(node = doc.createTextNode(text));
        }
        return node;
    }

    /**
     * �½�һ��CDATA�ڵ�. ��: <br>
     * <code>
     * <font color=red>
     * &nbsp;&lt;name&gt;&lt;![CDATA[text]]&gt;&lt;/name&gt;</font><br>
     * </code>
     *
     * @param doc  : Document: �ĵ�.
     * @param name : CDATA �ı��ڵ����.
     * @param text : CDATA �ı�.
     * @return
     */
    public static Element newCDATASection(Document doc, String name, String text)
    {
        Element e = doc.createElement(name);
        if (text != null)
        {
            e.appendChild(doc.createCDATASection(text));
        }
        return e;
    }

    /**
     * ���� element �� CDATA ����. �� <br>
     * <code>
     * &lt;element&gt;<font color=red>&lt;![CDATA[text]]&gt;</font>&lt;element&gt;
     * </code>
     *
     * @param doc     : Document: �ĵ�.
     * @param element : Ҫ����CDATA�Ľڵ�;
     * @param text    : CDATA �ı�.
     * @return
     */
    public static CDATASection setElementCDATA(Document doc, Element element, String text)
    {
        CDATASection node = null;
        if (text != null)
        {
            element.appendChild(node = doc.createCDATASection(text));
        }
        return node;
    }

    /**
     * ��ʽ�� Document, �Ե�ǰ�����ַ��趨.
     *
     * @param doc
     * @return
     */
    public static String toString(Document doc)
    {
        return toString(doc, projectEncoding);
    }

    /**
     * ��ʽ��һ�� Document
     *
     * @param doc
     * @param charset : ����ַ�.
     * @return String
     */
    public static String toString(Document doc, String charset)
    {
        ByteArrayOutputStream bos = null;// new ByteOutputStream();
        try
        {
            bos = toOutputStream(doc, charset);
            return new String(bos.toByteArray(), 0, bos.size(), charset);
        }
        catch (Exception e)
        {
            // do Nothing;
        }
        finally
        {
            if (bos != null)
            {
                try
                {
                    bos.close();
                }
                catch (IOException e)
                {
                    // e.printStackTrace();
                }
            }
        }
        return null;
    }

}
