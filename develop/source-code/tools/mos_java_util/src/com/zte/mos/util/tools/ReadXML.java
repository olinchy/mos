package com.zte.mos.util.tools;

import com.zte.mos.util.XmlJaxbHelper;

import javax.xml.bind.UnmarshalException;
import java.io.InputStream;

public class ReadXML
{
    public static <T> T parseByJaxb(InputStream is, Class<T> claz) throws Throwable
    {
        T t = (T) XmlJaxbHelper.importXmlJAXB(claz, is);
        return t;
    }

}
