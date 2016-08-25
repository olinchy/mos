package com.zte.mos.app.switchmodel.cases.env;

/**
 * Created by ccy on 5/26/16.
 */
public class CaseEnv
{
    public CaseEnv(String neType, String modelVersionHop, String scence, String oldCfgPath, String newCfgPath)
    {
        this.neType = neType;
        this.modelVersionHop = modelVersionHop;
        this.scenario = scence;
        this.oldPath = oldCfgPath;
        this.newPath = newCfgPath;
    }

    private final String scenario;
    private final String oldPath;
    private final String newPath;
    private final String neType;
    private final String modelVersionHop;
    private String caseOutPut;
    private String berkleyDbOutPut;

    public String getScenario()
    {
        return scenario;
    }

    public String scence()
    {
        return scenario;
    }

    public String getNeType()
    {
        return this.neType;
    }

    public String oldCfgPath()
    {
        return oldPath;
    }
    public String newCfgPath()
    {
        return newPath;
    }

    public String getModelVersionHop()
    {
        return this.modelVersionHop;
    }

    public String getBerkleyDbOutPut()
    {
        return this.berkleyDbOutPut;
    }

    public String getCaseOutPut()
    {
        return this.caseOutPut;
    }

    public void setCaseOutPut(String s)
    {
        this.caseOutPut = s;
    }

    public void setBerkleyDbOutPut(String s)
    {
        this.berkleyDbOutPut = s;
    }

}
