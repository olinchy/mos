package com.zte.mos.app.alarm.stub;

import com.zte.ums.api.usf.bsf.system.Path;
import com.zte.ums.uep.api.pfl.emb.EMBUrl;
import com.zte.ums.uep.api.pfl.emb.EMessage;
import com.zte.ums.uep.api.pfl.emb.EMessageObject;

import java.util.List;

/**
 * Created by ccy on 8/13/15.
 */
public class EMessageStub implements EMessage {
    @Override
    public int getMessageCode() {
        return 0;
    }

    @Override
    public String getAppVersion() {
        return null;
    }

    @Override
    public void setAppVersion(String s) {

    }

    @Override
    public EMBUrl getDestinationURL() {
        return null;
    }

    @Override
    public EMBUrl getSourceURL() {
        return null;
    }

    @Override
    public String getSequenceID() {
        return null;
    }

    @Override
    public void addMessageObject(EMessageObject eMessageObject) {

    }

    @Override
    public void addMessageObjects(EMessageObject[] eMessageObjects) {

    }

    @Override
    public List getAllMessageObject() {
        return null;
    }

    @Override
    public String getHandler() {
        return null;
    }

    @Override
    public void setHandler(String s) {

    }

    @Override
    public Path getDestinationPath() {
        return null;
    }

    @Override
    public Path getSourcePath() {
        return null;
    }

    @Override
    public void setDestinationPath(Path path) {

    }

    @Override
    public void setSourcePath(Path path) {

    }

    @Override
    public void setMML(String s) {

    }

    @Override
    public String getMML() {
        return null;
    }

    @Override
    public void setMsgVersion(int i) {

    }

    @Override
    public int getMsgVersion() {
        return 0;
    }

    @Override
    public void setMarker(String s) {

    }

    @Override
    public String getMarker() {
        return null;
    }

    @Override
    public boolean isBinaryMsg() {
        return false;
    }

    @Override
    public void setBinaryMsg(boolean b) {

    }

    @Override
    public int getTimeout() {
        return 0;
    }

    @Override
    public void setTimeout(int i) {

    }

    @Override
    public void setLimitSize(int i) {

    }

    @Override
    public int getLimitSize() {
        return 0;
    }

    @Override
    public byte[] getBinMsg() {
        return new byte[0];
    }

    @Override
    public void setMsgHeadProperty(String s, String s1) {

    }

    @Override
    public String getMsgHeadProperty(String s) {
        return null;
    }

    @Override
    public void setAsyncResponseCommand(boolean b) {

    }

    @Override
    public void setReceiveMml(boolean b) {

    }

    @Override
    public void setAsynResponseTimeout(int i) {

    }

    @Override
    public int getAsynResoneTimeout()
    {
        return 0;
    }
}
