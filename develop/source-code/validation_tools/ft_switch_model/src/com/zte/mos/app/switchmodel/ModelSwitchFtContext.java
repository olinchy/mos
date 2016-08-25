package com.zte.mos.app.switchmodel;

/**
 * Created by ccy on 5/24/16.
 */
public interface ModelSwitchFtContext
{
    String oldModelName();
    String oldModelVersion();
    String newModelName();
    String newModelVersion();
    String neType();
    String oldCfgPath();
    String newCfgPath();
    String oldOutputPath();
    String newOutputPath();
}
