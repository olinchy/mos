package com.zte.mos.util.tools;

import com.zte.mos.util.Scan;
import com.zte.mos.util.scaner.PreLoadScanner;

import java.util.Set;

/**
 * Created by luoqingkai on 14-10-23.
 */
public class Preloader
{

    public final static void load(String path)
    {
        Set<Class<Object>> set = Scan.getClasses(path, Object.class);
        PreLoadScanner scanner = new PreLoadScanner();
        for (Class<Object> claz : set)
        {
            try
            {
                scanner.handle(claz);
            }
            catch (Throwable ignore)
            {

            }
        }
    }
}
