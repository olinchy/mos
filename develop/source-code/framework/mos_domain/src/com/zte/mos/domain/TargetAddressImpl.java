package com.zte.mos.domain;

/**
 * Created by love on 16-1-10.
 */
public class TargetAddressImpl implements TargetAddress {

    private final String targetID;
    private final String ipAddress;
    private final IMosHead mosHeader;
    private final ModelHead modelHead;
    private final ImagedSystem sys;

    public TargetAddressImpl(String targetID, String ipAddress,
                             ModelMETA meta, ImagedSystem sys) {
        this.targetID = targetID;
        this.ipAddress = ipAddress;
        this.mosHeader = meta.mosHead;
        this.modelHead = meta.modelHead;
        this.sys = sys;
    }

    public TargetAddressImpl(String targetID, String ipAddress, ImagedSystem sys){
        this.targetID = targetID;
        this.ipAddress = ipAddress;
        this.mosHeader = sys.getModelMETA().mosHead;
        this.modelHead = sys.getModelMETA().modelHead;
        this.sys = sys;
    }

    @Override
    public String getTargetID() {
        return targetID;
    }

    @Override
    public String getIpAddress() {
        return ipAddress;
    }

    @Override
    public IMosHead getMosHead() {
        return mosHeader;
    }

    @Override
    public ModelHead getModelHead() {
        return modelHead;
    }

    @Override
    public ImagedSystem getSystem() {
        return sys;
    }

    public String toString(){
        return targetID + ", " + ipAddress + ", " + mosHeader.version();
    }
}
