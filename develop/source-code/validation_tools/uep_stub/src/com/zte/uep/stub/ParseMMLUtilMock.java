package com.zte.uep.stub;

import com.zte.ums.uep.api.pfl.emb.EMBException;
import com.zte.ums.uep.api.pfl.emb.EMessageObject;
import com.zte.ums.uep.api.pfl.embmml.ParseMMLUtil;

/**
 * Created by pavel on 16-1-14.
 */
public class ParseMMLUtilMock implements ParseMMLUtil
{
    private String cmdCode = null;

    @Override
    public void setCommandCode(String s)
    {
        this.cmdCode = s;
    }

    @Override
    public String getCommandCode()
    {
        return cmdCode;
    }

    @Override
    public int getParaCount()
    {
        return 0;
    }

    @Override
    public int getBlockCount()
    {
        return 0;
    }

    @Override
    public int getBlockIndexByPara(int i)
    {
        return 0;
    }

    @Override
    public String getParaName(int i, int i1)
    {
        return null;
    }

    @Override
    public Object getParaValue(int i, int i1)
    {
        return null;
    }

    @Override
    public String[] getParaArray(int i, int i1)
    {
        return new String[0];
    }

    @Override
    public Object getParaValue(int i, String s)
    {
        return null;
    }

    @Override
    public String[] getParaArray(int i, String s)
    {
        return new String[0];
    }

    @Override
    public String[][] getParaTwoArray(int i, int i1)
    {
        return new String[0][];
    }

    @Override
    public String toMML() throws EMBException
    {
        return cmdCode;
    }

    @Override
    public void setMML(String s) throws EMBException
    {

    }

    @Override
    public void addPara(int i, String s, String s1)
    {

    }

    @Override
    public void addParaArray(int i, String s, String[] strings)
    {

    }

    @Override
    public void addParaArrays(int i, String s, String[][] strings)
    {

    }

    @Override
    public void addObjectPara(int i, EMessageObject eMessageObject)
    {

    }

    @Override
    public void addObjectParaArray(int i, String s, EMessageObject[] eMessageObjects)
    {

    }
}
