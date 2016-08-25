package com.zte.mos.app.switchmodel.cases.model;

/**
 * Created by ccy on 5/25/16.
 */
public class CaseModel
{
    private String oldModelType;
    private String oldModelVersion;

    private String newModelType;
    private String newModelVersion;

    private String scence;
    private String neType;

    private String oldCfgPath;
    private String newCfgPath;

    public CaseModel()
    {

    }

    public void setOldModelType(String oldModelType)
    {
        this.oldModelType = oldModelType;
    }

    public void setOldModelVersion(String oldModelVersion)
    {
        this.oldModelVersion = oldModelVersion;
    }

    public void setNewModelType(String newModelType)
    {
        this.newModelType = newModelType;
    }

    public void setNewModelVersion(String newModelVersion)
    {
        this.newModelVersion = newModelVersion;
    }

    public void setScence(String scence)
    {
        this.scence = scence.replace("-", "_").replace(".", "_").replace("+", "Plus");
    }

    public void setNeType(String neType)
    {
        this.neType = neType;
    }

    public void setOldCfgPath(String oldCfgPath)
    {
        this.oldCfgPath = oldCfgPath;
    }

    public void setNewCfgPath(String newCfgPath)
    {
        this.newCfgPath = newCfgPath;
    }

    public String getOldModelType()
    {
        return oldModelType;
    }

    public String getOldModelVersion()
    {
        return oldModelVersion;
    }

    public String getNewModelType()
    {
        return newModelType;
    }

    public String getNewModelVersion()
    {
        return newModelVersion;
    }

    public String getScence()
    {
        return scence;
    }

    public String getNeType()
    {
        return neType;
    }

    public String getOldCfgPath()
    {
        return oldCfgPath;
    }

    public String getNewCfgPath()
    {
        return newCfgPath;
    }
}
