package com.zte.mos.util;

import org.jdom.Element;

public final class Dn2Element
{
    private Dn2Element()
    {

    }

    public static Element build(String dn)
    {
        Element root = new Element("dn");
        Element elem = null;

        int tagStart = dn.indexOf("/");
        int tagEnd = 0;
        int valueEnd = 0;

        while ((tagStart != -1) && (tagStart != dn.length()))
        {
            tagEnd = dn.indexOf("/", tagStart + 1);

            valueEnd = dn.indexOf("/", tagEnd + 1);

            if (valueEnd == -1)
            {
                valueEnd = dn.length();
            }
            elem = new Element(dn.substring(tagStart + 1, tagEnd));
            elem.setText(dn.substring(tagEnd + 1, valueEnd));
            root.addContent(elem);
            tagStart = valueEnd;
        }
        return root;
    }

}
