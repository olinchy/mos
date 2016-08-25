package com.zte.mos.msg.impl;

import com.zte.mos.domain.IMosHead;
import com.zte.mos.domain.ImagedSystem;
import com.zte.mos.domain.ModelHead;
import com.zte.mos.domain.TargetAddress;
import com.zte.mos.msg.MsgMode;
import com.zte.mos.msg.SupportedProtocol;

/**
 * Created by luoqingkai on 15-5-8.
 */
public class UTTargetAddress implements TargetAddress {

    private String ipAddress = "127.0.0.1";
    private String targetID = "/Ems/1/Ne/1/Product/1";

    private IMosHead mosHead;
    private ModelHead modelHead;

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setTargetID(String targetID) {
        this.targetID = targetID;
    }

    public void setMosHead(IMosHead mosHead) {
        this.mosHead = mosHead;
    }

    public void setModelHead(ModelHead modelHead) {
        this.modelHead = modelHead;
    }

    @Override
    public String getTargetID() {
        return this.targetID;
    }

    @Override
    public String getIpAddress() {
        return this.ipAddress;
    }

    @Override
    public IMosHead getMosHead() {
        return this.mosHead;
    }

    @Override
    public ModelHead getModelHead() {
        return this.modelHead;
    }

    @Override
    public ImagedSystem getSystem() {
        return null;
    }
}
