package com.zte.mos.domain;

public class MosSettings
{

    public String rootMoName = "";
    private String processName = "";

    private MosSettings()
    {
    }

    public String getProcessName()
    {
        return processName;
    }

    public void setProcessName(String processName)
    {
        this.processName = processName;
    }

    public String rootMoName()
    {
        return rootMoName;
    }

    public void set(String rootMoName)
    {
        this.rootMoName = rootMoName;
    }


}
