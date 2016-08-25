package com.zte.mos.util.tools;

import com.zte.mos.util.basic.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipUtil
{
    static Logger logger = Logger.logger(ZipUtil.class);

    public ZipUtil()
    {

    }

    public static void unzip(File zipFile, String descDir) throws Exception
    {
        File pathFile = new File(descDir);
        OutputStream out = null;
        InputStream in = null;
        ZipFile zip = null;

        if (!pathFile.exists())
        {
            pathFile.mkdirs();
        }

        try
        {
            zip = new ZipFile(zipFile);

            for (Enumeration<? extends ZipEntry> entries = zip.entries(); entries
                    .hasMoreElements(); )
            {
                ZipEntry entry = entries.nextElement();
                String zipEntryName = entry.getName();
                in = zip.getInputStream(entry);
                String outPath = (descDir + "/" + zipEntryName).replaceAll(
                        "\\*",
                        "/");
                File file = new File(outPath.substring(0,
                        outPath.lastIndexOf('/')));
                if (!file.exists())
                {
                    file.mkdirs();
                }
                if (new File(outPath).isDirectory())
                {
                    continue;
                }
                logger.info(outPath);
                out = new FileOutputStream(outPath);
                byte[] buf1 = new byte[1024];
                int len;
                while ((len = in.read(buf1)) > 0)
                {
                    out.write(buf1, 0, len);
                }
            }
        }
        finally
        {
            if (in != null)
            {
                in.close();
            }

            if (out != null)
            {
                out.close();
            }

            if (zip != null)
            {
                zip.close();
            }
        }
        logger.info("******************解压完毕********************");
    }

}
