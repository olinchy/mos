package com.zte.mos.app.switchmodel.output;

import com.zte.mos.message.Mo;
import com.zte.mos.type.Pair;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created by ccy on 6/1/16.
 */
public class ModelSwitchOutputter implements Outputter
{
    public ModelSwitchOutputter(String caseName, String fileName, Map<String, List<Pair<Mo, Mo>>> map)
    {
        this.caseName = caseName;
        this.fileName = fileName;
        this.map = map;
        this.file = new File(this.fileName);
    }

    private final String caseName;
    private final String fileName;
    private final Map<String, List<Pair<Mo, Mo>>> map;
    private final File file;

    @Override
    public void output()
    {
        try
        {
            createFileIfNotExisted();
            FileOutputStream out = new FileOutputStream(this.file, true);
            out.write(getBuffer().toString().getBytes("utf-8"));
            out.close();
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }

    private void createFileIfNotExisted() throws IOException
    {
        if (!file.exists())
        {
            file.createNewFile();
        }

    }

    private StringBuffer getBuffer()
    {
        StringBuffer buffer = new StringBuffer(1024);
        buffer.append(this.caseName + " upgrade summary\r\n ");

        Iterator iterator = map.entrySet().iterator();
        while(iterator.hasNext())
        {
            Map.Entry entry = (Map.Entry) iterator.next();
            buffer.append("    " + entry.getKey() + " Mo:\r\n");
            List<Pair<Mo, Mo>> list = (List<Pair<Mo, Mo>>)entry.getValue();

            for(Pair<Mo, Mo> pair : list)
            {
                buffer.append("        " + FormatterDefs.format(entry.getKey().toString(), pair));
            }
        }

        return buffer;
    }
}
