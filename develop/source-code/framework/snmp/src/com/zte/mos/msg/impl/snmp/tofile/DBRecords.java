package com.zte.mos.msg.impl.snmp.tofile;

import com.zte.mos.util.XmlJaxbHelper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by olinchy on 16-1-28.
 */
@XmlRootElement(name = "DBRECORDLIST")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class DBRecords
{
    @XmlElement(name = "DBRECORD")
    public ArrayList<DBRecord> records = new ArrayList<DBRecord>();

    public void write(String path)
    {
        try
        {
            OutputStream out = new FileOutputStream(new File(path));
            XmlJaxbHelper.exportXmlJAXB(this, out);
        }
        catch (Exception e)
        {
            logger(this.getClass()).warn("write file " + path + " failed!", e);
        }
    }


}
