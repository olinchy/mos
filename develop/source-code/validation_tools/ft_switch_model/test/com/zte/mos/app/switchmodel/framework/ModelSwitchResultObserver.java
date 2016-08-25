package com.zte.mos.app.switchmodel.framework;

import com.zte.mos.app.switchmodel.service.Observer;
import com.zte.mos.app.switchmodel.util.FileAccess;

import java.io.File;

/**
 * Created by ccy on 5/27/16.
 */
public class ModelSwitchResultObserver implements Observer
{
    ModelSwitchResultObserver(String srcPath, String dstPath)
    {
        this.srcPath = srcPath;
        this.dstPath = dstPath;
    }

    private final String srcPath;
    private final String dstPath;

    @Override
    public void update(String result)
    {
        try
        {
            Thread.sleep(5000);
            FileAccess.copyDir(new File(srcPath), new File(dstPath));
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }
}
