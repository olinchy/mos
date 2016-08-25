package com.zte.mos.msg.impl.snmp.tofile;

import com.zte.mos.util.basic.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by ccc on 16-4-10.
 */
public class XmlJdomWriter
{

    private static final Logger log = Logger.logger(XmlJdomWriter.class);

    public static void writeXMLFiles(String path,DBRecords dbRecords)
    {
        if(path==null || dbRecords == null)
        {
        log.error("writeXmlFiles error, path or dbRecords is null", new Throwable());
        }

        Element root = new Element("DBRECORDLIST");

        Document document = new Document(root);


        for(DBRecord record:dbRecords.records)
        {
            root.addContent(genARecordElement(record));

        }

        XMLOutputter out = new XMLOutputter();

        try
        {
            out.output(document, new FileOutputStream(path));
        }
        catch (IOException e)
        {
            log.error("write db records error",e);
            e.printStackTrace();
        }
    }


    private static Element genARecordElement(DBRecord record)
    {


        Element e = new Element("DBRECORD");
        e.setAttribute("table", record.table);
        e.setAttribute("opType",record.opType);

        for(Field field:record.fields)
        {
            Element element = new Element("FIELD");
            element.setAttribute("name",field.name);
            element.setAttribute("value",field.value);
            e.addContent(element);

        }

        return e;
    }
}
