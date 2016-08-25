package com.zte.mos.sim.preload;

import com.zte.mos.util.tools.Preloader;

/**
 * Created by olinchy on 16-1-26.
 */
public class ScanPreloads
{
    public static void load()
    {
        String packageName = ScanPreloads.class.getPackage().getName();

        Preloader.load(packageName);
    }
}
