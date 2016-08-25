package com.zte.mos.app.alarm.stub;

import com.zte.ums.uep.api.pfl.emb.EMBException;
import com.zte.ums.uep.api.pfl.emb.EMessage;
import com.zte.ums.uep.api.pfl.embmml.EMBMmlService;
import com.zte.ums.uep.api.pfl.embmml.ParseMMLUtil;

import java.net.URL;

/**
 * Created by ccy on 8/13/15.
 */
public class EMBMmlServiceStub implements EMBMmlService {
    @Override
    public String convertEMessageToMml(EMessage eMessage, String s) throws EMBException {
        return null;
    }

    @Override
    public EMessage convertMmlToEMessage(String s, String s1) throws EMBException {
        return null;
    }

    @Override
    public String convertEMBExceptionToMml(EMBException e, String s) throws EMBException {
        return null;
    }

    @Override
    public EMBException convertMmlToEMBException(String s, String s1) throws EMBException {
        return null;
    }

    @Override
    public String convertArrayToString(String[] strings) throws EMBException {
        return null;
    }

    @Override
    public String convert2DArrayToString(String[][] strings) throws EMBException {
        return null;
    }

    @Override
    public String[] convertStringToArray(String s) throws EMBException {
        return new String[0];
    }

    @Override
    public String[][] convertStringToArray2D(String s) throws EMBException {
        return new String[0][];
    }

    @Override
    public boolean isReceiveMML(String s) throws EMBException {
        return false;
    }

    @Override
    public ParseMMLUtil newParseMMLUtil() {
        return new ParseMMLUtilStub();
    }

    @Override
    public void addEmbCommandFile(URL url, URL url1) throws EMBException {

    }

    @Override
    public void setMMLReceive(int i) throws EMBException {

    }
}
