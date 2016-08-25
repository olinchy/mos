package com.zte.mos.app.alarm.stub;

import com.zte.ums.uep.api.pfl.emb.EMBException;
import com.zte.ums.uep.api.pfl.emb.EMessageObject;
import com.zte.ums.uep.api.pfl.embmml.ParseMMLUtil;

import java.util.HashMap;

/**
 * Created by ccy on 8/13/15.
 */
public class ParseMMLUtilStub implements ParseMMLUtil {

    private String code = "";
    private HashMap<String, Object> map = new HashMap<String, Object>();
    @Override
    public void setCommandCode(String s)
    {
        this.code = s;
    }

    @Override
    public String getCommandCode() {
        return code;
    }

    @Override
    public int getParaCount() {
        return map.size();
    }

    @Override
    public int getBlockCount() {
        return 0;
    }

    @Override
    public int getBlockIndexByPara(int i) {
        return 0;
    }

    @Override
    public String getParaName(int i, int i1) {
        return null;
    }

    @Override
    public Object getParaValue(int i, int i1) {
        return null;
    }

    @Override
    public String[] getParaArray(int i, int i1) {
        return new String[0];
    }

    @Override
    public Object getParaValue(int i, String s) {
        return map.get(s);
    }

    @Override
    public String[] getParaArray(int i, String s) {
        return (String[]) map.get(s);
    }

    @Override
    public String[][] getParaTwoArray(int i, int i1) {
        return new String[0][];
    }

    @Override
    public String toMML() throws EMBException {
        return null;
    }

    @Override
    public void setMML(String s) throws EMBException {

    }

    @Override
    public void addPara(int i, String s, String s1) {
        map.put(s, s1);

    }

    @Override
    public void addParaArray(int i, String s, String[] strings) {
        map.put(s, strings);
    }

    @Override
    public void addParaArrays(int i, String s, String[][] strings) {

    }

    @Override
    public void addObjectPara(int i, EMessageObject eMessageObject)
    {

    }

    @Override
    public void addObjectParaArray(int i, String s, EMessageObject[] eMessageObjects) {

    }
}
