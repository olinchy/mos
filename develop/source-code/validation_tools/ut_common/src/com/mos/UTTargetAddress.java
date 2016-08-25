package com.mos;

import com.zte.mos.annotation.Imaged;
import com.zte.mos.domain.*;
import com.zte.mos.exception.MOSException;
import com.zte.mos.msg.SupportedProtocol;

/**
 * Created by luoqingkai on 15-5-8.
 */
@Imaged(protocol = SupportedProtocol.RPC)
public class UTTargetAddress implements TargetAddress {

    private String ipAddress = "10.10.10.10";
    private String modelType = "nr8250";
    private String targetID = "/Ems/1/Ne/1/Product/1";
    private String modelVersion = "2.04.01.06";


    public String getIpAddress() {
        return ipAddress;
    }

    @Override
    public IMosHead getMosHead() {
        return null;
    }

    @Override
    public ModelHead getModelHead() {
        return null;
    }

    @Override
    public ImagedSystem getSystem() {
        return null;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Override
    public String getTargetID() {
        return targetID;
    }


    public void setTargetID(String targetID) {
        this.targetID = targetID;
    }

}
