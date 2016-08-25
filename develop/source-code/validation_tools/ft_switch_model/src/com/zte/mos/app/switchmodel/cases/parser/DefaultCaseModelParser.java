package com.zte.mos.app.switchmodel.cases.parser;

import com.zte.mos.app.switchmodel.cases.env.CaseEnv;
import com.zte.mos.app.switchmodel.cases.model.CaseModel;
import com.zte.mos.domain.or.ORDB;
import com.zte.mos.domain.or.ORRow;
import com.zte.mos.util.tools.ReadXML;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ccy on 5/26/16.
 */

/**
 * Created by ccy on 3/23/16.
 */


public class DefaultCaseModelParser implements CaseModelParser
{
    private static Map<String, String> map = new HashMap<String, String>();

    static
    {
        map.put(toKey("nr8250", "8250V2.04.01.08"), "v241");
        map.put(toKey("nr8000tr", "8250V2.04.01.08"), "v241");
        map.put(toKey("nr8250", "8250V2.04.02.08"), "v242");
        map.put(toKey("nr8000tr", "8250V2.04.02.08"), "v242");
        map.put(toKey("nr8120", "8120V2.04.02.06"), "v242");
        map.put(toKey("nr8250", "8250V2.04.02.08"), "v242");
        map.put(toKey("nr8000tr", "8250V2.04.02.08"), "v242");
        map.put(toKey("nr8150", "8250V2.04.02.08"), "v242");
        map.put(toKey("nr8000tr", "8250V2.04.01.08"), "v241");
        map.put(toKey("nr8250", "8250V2.04.01.08"), "v241");
        map.put(toKey("nr8120", "8120V2.04.01.08"), "v241");
        map.put(toKey("nr8150", "8250V2.04.01.08"), "v241");
        map.put(toKey("nr8950", "8950V2.04.01.08"), "v241");
        map.put(toKey("nr8950", "8950V2.03.03.10"), "v2");
        map.put(toKey("nr8120", "8120V2.03.03.04"), "v2");
        map.put(toKey("nr8120", "8120V2.03.03.07"), "v2");
        map.put(toKey("nr8120", "8120V2.03.03.10"), "v2");
        map.put(toKey("nr8120", "V_2.03.02_8120"), "v2");
        map.put(toKey("nr8120", "V2.03.02"), "v2");
        map.put(toKey("nr8120", "8120V2.03.02.10"), "v2");
        map.put(toKey("nr8120", "8120V2.03.01.09"), "v2");

        map.put(toKey("nr8250", "0"), "v1");
        map.put(toKey("nr8250", "8250V2.03.03.07"), "v2");
        map.put(toKey("nr8250", "V_2.03.02_8250"), "v2");
        map.put(toKey("nr8250", "8250V2.03.03.10"), "v2");




    }

    @Override
    public CaseModel parse(CaseEnv env)
    {
        File oldCfgFile = getCfgFile(new File(env.oldCfgPath()));
        File newCfgFile = getCfgFile(new File(env.newCfgPath()));
        String neType = getNeType(oldCfgFile);
        String configModelType = getConfigModelType(oldCfgFile);
        String oldModelType = getModelType(neType, configModelType);
        String oldModelVersion = getModelVersion(neType, getMibVersion(oldCfgFile));
        String newModelType = getModelType(neType, configModelType);
        String newModelVersion = getModelVersion(neType, getMibVersion(newCfgFile));
        CaseModel model = new CaseModel();
        model.setNeType(neType);
        model.setNewCfgPath(env.newCfgPath());
        model.setOldCfgPath(env.oldCfgPath());
        model.setNewModelType(newModelType);
        model.setNewModelVersion(newModelVersion);
        model.setOldModelType(oldModelType);
        model.setOldModelVersion(oldModelVersion);
        model.setScence(env.scence());
        return model;
    }

    private String getConfigModelType(File cfgFile)
    {
        try
        {
            ORDB db = new ORDB();
            NeConfigLoader loader = new NeConfigLoader();
            loader.load(cfgFile.toString());
            db.merge(loader.getDb());
            db.list2Map();
            List<ORRow> list = db.getRows("R_DEVICE");

            for(ORRow row : list)
            {
                return row.getFieldValue("acRadioModel".toUpperCase()).toString().toLowerCase();
            }

        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }

        return "unknownModelType";
    }


    private String getNeType(File cfgFile)
    {
        if (cfgFile != null)
        {
            return cfgFile.getName().split("=")[0].split("\\.")[1];
        }
        return "unknownNeType";
    }

    private String getMibVersion(File cfgFile)
    {
        try
        {
            ORDB db = new ORDB();
            NeConfigLoader loader = new NeConfigLoader();
            loader.load(cfgFile.toString());
            db.merge(loader.getDb());
            db.list2Map();
            List<ORRow> list = db.getRows("R_DEVICE");

            for(ORRow row : list)
            {
                return row.getFieldValue("acMibVersion".toUpperCase()).toString();
            }

        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }

        return "unkownMibVersion";
    }



    private String getModelVersion(String neType, String mibVersion)
    {
        System.out.println("mib version " + String.valueOf(mibVersion));

        return map.get(toKey(neType, mibVersion));
    }

    private static String toKey(String neType, String mibVersion)
    {
        return neType + "_" + mibVersion;
    }

    private File getCfgFile(File parent)
    {
        File[] files = parent.listFiles();

        for(File file: files)
        {
            if (file.getName().contains("sql.backup"))
            {
                continue;
            }
            if (file.getName().contains("ODU.xml.backup"))
            {
                continue;
            }

            if (file.getName().contains("MODSTATUS"))
            {
                continue;
            }
            if (file.getName().endsWith("xml.backup"))
            {
                return file;
            }
        }
        return null;
    }


    private class NeConfigLoader
    {
        private ORDB db = ORDB.nullDb;

        public void load(String fileName) throws Throwable
        {

            File file = new File(fileName);
            if (file.exists() && file.isFile())
            {
                InputStream is = new FileInputStream(file);
                db = ReadXML.parseByJaxb(is, ORDB.class);
            }
            else
            {
                throw new FileNotFoundException(fileName);
            }
        }

        public ORDB getDb()
        {
            return db;
        }

    }


    protected String getModelType(String neType, String configModelType)
    {
        if (neType.equalsIgnoreCase("nr8000tr"))
        {
            return neType.toLowerCase();
        }
        return configModelType;
    }
}
